package com.alexnj.twitterclient.fragments;


import com.alexnj.twitterclient.TwitterClientApp;

public class HomeTweetListFragment extends TweetListFragment {

	@Override
	public void loadMore(int count) {
		String lastId = "";
		if (tweets.size() > 0 && count > 0) {
			lastId = String.valueOf( tweets.get(count-1).getTweetId()-1 );
		}
		TwitterClientApp.getRestClient().getHomeTimeline( this.refreshHandler, lastId );
	}

}
