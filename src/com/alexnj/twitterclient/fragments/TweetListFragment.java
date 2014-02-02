package com.alexnj.twitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.query.From;
import com.alexnj.twitterclient.EndlessScrollListener;
import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.TwitterClientApp;
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		tweets = new ArrayList<Tweet>();
		adapter = new TweetsAdapter(getActivity(), tweets);
		refreshHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
				
				if(newTweets.size()>0 ) {
					if (tweets.size()>0) {
						if (newTweets.get(newTweets.size()-1).getId()!=tweets.get(tweets.size()-1).getId()) {
							tweets.addAll(newTweets);
							adapter.notifyDataSetChanged();						
						}
					}
					else {
						tweets.addAll(newTweets);
						adapter.notifyDataSetChanged();						
					}
				}
				
				Log.d("DEBUG", jsonTweets.toString());
			}
		};
		
	}

	abstract public void loadMore(int count);
    
}
