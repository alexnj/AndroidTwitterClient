package com.alexnj.twitterclient.fragments;

import android.util.Log;

import com.alexnj.twitterclient.TwitterClientApp;

public class HomeTweetListFragment extends TweetListFragment {

	@Override
	public void refreshList() {
		String lastId = "";
		if (tweets.size() > 0) {
			lastId = String.valueOf( tweets.get(tweets.size()-1).getId() );
		}
		TwitterClientApp.getRestClient().getHomeTimeline( this.refreshHandler, lastId );
	}

}
