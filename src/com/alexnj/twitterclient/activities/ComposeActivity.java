package com.alexnj.twitterclient.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.TwitterClientApp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}   
	
	public void onActionTweet(MenuItem mi) {
		TextView tvTweet = (TextView)findViewById(R.id.etTweet);
		
		if(tvTweet.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.error_empty_tweet, Toast.LENGTH_SHORT).show();
			return;
		}
		
		TwitterClientApp.getRestClient().postUpdate( tvTweet.getText().toString(), new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONObject json) {
				Toast.makeText(getApplicationContext(), R.string.error_post_failure, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(int arg0, JSONObject arg1) {
				Intent returnIntent = new Intent();
				setResult(RESULT_OK,returnIntent);     
				finish();
			}
		});
		
	}
	
	public void onActionBack(MenuItem mi) {
		finish();
	}

}
