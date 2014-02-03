package com.alexnj.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;

import com.alexnj.twitterclient.TwitterClientApp;
import com.alexnj.twitterclient.models.User;

public class UserTweetListFragment extends TweetListFragment {
	protected User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = (User)getArguments().getSerializable("User");
	}

	@Override
	public void loadMore(int count) {
		String lastId = "";
		if (tweets.size() > 0 && count > 0) {
			lastId = String.valueOf( tweets.get(count-1).getTweetId()-1 );
		}

		Log.d("DEBUG", "Usernnn" + user.getScreenName().toString());
		
		TwitterClientApp.getRestClient().getUserTimeline( this.refreshHandler, user.getScreenName(), lastId );
	}
}
