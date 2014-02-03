package com.alexnj.twitterclient.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.alexnj.twitterclient.EndlessScrollListener;
import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.adapters.TweetsAdapter;
import com.alexnj.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

abstract public class TweetListFragment extends Fragment {
	protected JsonHttpResponseHandler refreshHandler = null;

	private ListView lvTimeline = null;
	protected TweetsAdapter adapter = null;
	protected ArrayList<Tweet> tweets = null; 
	protected EndlessScrollListener scrollListener;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	            refreshTimeline();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void clearList() {
		scrollListener.reset();
		tweets.clear();
		adapter.notifyDataSetChanged();
	}
	
	public void refreshTimeline() {
		clearList();
		loadMore(0);
	}
	
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tweet_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {

    	// Defines the xml file for the fragment
    	View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
    	lvTimeline = (ListView)view.findViewById(R.id.lvTimeline);
    	lvTimeline.setAdapter(adapter);

    	this.scrollListener = new EndlessScrollListener( ) {
			@Override
		    public void onLoadMore( int start ) {
				loadMore(lvTimeline.getCount()); 
		    }
        };		
        
		lvTimeline.setOnScrollListener( this.scrollListener );

    	loadMore(0);

		return view;
    }
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
		
		tweets = new ArrayList<Tweet>();
		adapter = new TweetsAdapter(getActivity(), tweets);
//		
//		List<Tweet> offlineTweets = new Select().from(Tweet.class).execute();
//		Log.d("DEBUG", "loaded: "+offlineTweets.toString());
//		for (Tweet tweet : offlineTweets) {
//			tweets.add(tweet);
//			Log.d( "DEBUG", tweet.toString());
//		}
//
//		adapter.notifyDataSetChanged();
		
		refreshHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
				
				if (newTweets.isEmpty()) {
					return;
				}
				
				if (tweets.size()>0) {
					if (newTweets.get(newTweets.size()-1).getTweetId()==tweets.get(tweets.size()-1).getTweetId()) {
						return;
					}
				}
				
				ActiveAndroid.beginTransaction();
				try {
					for (Tweet tweet : newTweets) {
						tweets.add(tweet);
						tweet.save();
					}
					ActiveAndroid.setTransactionSuccessful();
				} finally {
                    ActiveAndroid.endTransaction();
                }

				
				adapter.notifyDataSetChanged();
			
				Log.d("DEBUG", jsonTweets.toString());
			}
		};
		
	}

	abstract public void loadMore(int count);
    
}
