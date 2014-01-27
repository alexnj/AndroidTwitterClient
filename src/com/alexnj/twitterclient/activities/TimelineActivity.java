package com.alexnj.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.alexnj.twitterclient.TwitterClientApp;
import com.alexnj.twitterclient.adapters.TweetsAdapter;
import com.alexnj.twitterclient.models.Tweet;
import com.alexnj.twitterclient.R;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private JsonHttpResponseHandler refreshHandler = null;
	
	public static final int COMPOSE_INTENT = 1;
	
	
	public void refreshTimeline() {
		if( refreshHandler == null ) {
			refreshHandler = new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					Log.d( "DEBUG", jsonTweets.toString());
					ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
					
					ListView lvTimeline = (ListView)findViewById(R.id.lvTimeline);
					TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
					lvTimeline.setAdapter(adapter);
				}
			};
		}
		
		TwitterClientApp.getRestClient().getHomeTimeline( refreshHandler );
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		refreshTimeline();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onClickCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i,COMPOSE_INTENT);
	}
	
	public void onClickRefresh(MenuItem mi) {
		refreshTimeline();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case COMPOSE_INTENT:
				if(resultCode==RESULT_OK) {
					refreshTimeline(); 
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
