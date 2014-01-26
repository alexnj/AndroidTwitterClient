package com.alexnj.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.alexnj.twitterclient.adapters.TweetsAdapter;
import com.alexnj.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.R;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		TwitterClientApp.getRestClient().getHomeTimeline( new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				Log.d( "DEBUG", jsonTweets.toString());
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
				ListView lvTimeline = (ListView)findViewById(R.id.lvTimeline);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTimeline.setAdapter(adapter);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
