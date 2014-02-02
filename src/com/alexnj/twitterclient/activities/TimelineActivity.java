package com.alexnj.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.SupportFragmentTabListener;
import com.alexnj.twitterclient.TwitterClientApp;
import com.alexnj.twitterclient.adapters.TweetsAdapter;
import com.alexnj.twitterclient.fragments.HomeTweetListFragment;
import com.alexnj.twitterclient.fragments.MentionsTweetListFragment;
import com.alexnj.twitterclient.fragments.TweetListFragment;
import com.alexnj.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends ActionBarActivity {
	
	public static final int COMPOSE_INTENT = 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		setupTabs();
//		refreshTimeline();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onClickCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i,COMPOSE_INTENT);
	}
	
	public void onClickRefresh(MenuItem mi) {
//		refreshTimeline();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case COMPOSE_INTENT:
				if(resultCode==RESULT_OK) {
//					refreshTimeline(); 
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    private void setupTabs() {
    	ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText("First")
            .setIcon(R.drawable.ic_action_compose)
            .setTag("HomeTimelineFragment")
            .setTabListener(new SupportFragmentTabListener<HomeTweetListFragment>(R.id.flContainer, this,
                        "first", HomeTweetListFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
            .newTab()
            .setText("Second")
            .setIcon(R.drawable.ic_action_refresh)
            .setTag("MentionsTimelineFragment")
            .setTabListener(new SupportFragmentTabListener<MentionsTweetListFragment>(R.id.flContainer, this,
                        "second", MentionsTweetListFragment.class));
        actionBar.addTab(tab2);
    }

}
