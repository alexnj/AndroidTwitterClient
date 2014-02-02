package com.alexnj.twitterclient.fragments;

import com.alexnj.twitterclient.TwitterClientApp;

public class MentionsTweetListFragment extends TweetListFragment {

	@Override
	public void loadMore(int count) {
		String lastId = "";
		if (count==0) {
			clearList();
		}
		else if (tweets.size() > 0) {
			lastId = String.valueOf( tweets.get(count-1).getId() );
		}
		TwitterClientApp.getRestClient().getMentionsTimeline( this.refreshHandler, lastId );
	}
}
