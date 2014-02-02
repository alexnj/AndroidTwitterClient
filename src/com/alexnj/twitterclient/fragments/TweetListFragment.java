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
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// Defines the xml file for the fragment
    	View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
    	lvTimeline = (ListView)view.findViewById(R.id.lvTimeline);
    	lvTimeline.setAdapter(adapter);
    	refreshList();
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
				tweets.addAll( Tweet.fromJson(jsonTweets) );
				adapter.notifyDataSetChanged();
				if( tweets.size() > 0 ) {
					Log.d( "DEBUG", String.valueOf( tweets.get(tweets.size()-1).getId() ) );
				}

				Log.d("DEBUG", jsonTweets.toString());
			}
		};
	}

	abstract public void refreshList();
    
}
