package com.alexnj.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.SupportFragmentTabListener;
import com.alexnj.twitterclient.fragments.HomeTweetListFragment;
import com.alexnj.twitterclient.fragments.MentionsTweetListFragment;
import com.alexnj.twitterclient.fragments.TweetListFragment;
import com.alexnj.twitterclient.models.Tweet;

public class TimelineActivity extends ActionBarActivity implements TweetListFragment.OnTweetClickedListener {
	
	public static final int COMPOSE_INTENT = 1;
	public static final int USER_PROFILE_INTENT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onClickCompose() {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i,COMPOSE_INTENT);
	}
	
	public void onClickUserProfile() {
		Intent i = new Intent(this, UserActivity.class);
		startActivityForResult(i,USER_PROFILE_INTENT);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_compose:
	        	onClickCompose();
	            return true;
	        case R.id.action_user:
	        	onClickUserProfile();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
	
	public void refreshTimeline() {
		TweetListFragment f = (TweetListFragment) getSupportFragmentManager().findFragmentByTag(getSupportActionBar().getSelectedTab().getTag().toString());
        f.refreshTimeline();
	}
	
    private void setupTabs() {
    	ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText(R.string.timeline_home)
            .setIcon(R.drawable.ic_action_compose)
            .setTag("HomeTimelineFragment")
            .setTabListener(new SupportFragmentTabListener<HomeTweetListFragment>(R.id.flContainer, this,
                        "first", HomeTweetListFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
            .newTab()
            .setText(R.string.timeline_mentions)
            .setIcon(R.drawable.ic_action_refresh)
            .setTag("MentionsTimelineFragment")
            .setTabListener(new SupportFragmentTabListener<MentionsTweetListFragment>(R.id.flContainer, this,
                        "second", MentionsTweetListFragment.class));
        actionBar.addTab(tab2);
    }

	@Override
	public void onTweetClicked(Tweet tweet) {
		Intent i = new Intent(this, UserActivity.class);
		i.putExtra("User", tweet.getUser());
		startActivity(i);		
	}

}
