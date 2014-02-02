package com.alexnj.twitterclient.fragments;

import com.alexnj.twitterclient.TwitterClientApp;

public class MentionsTweetListFragment extends TweetListFragment {

	@Override
	public void loadMore(int count) {
		TwitterClientApp.getRestClient().getMentionsTimeline( this.refreshHandler, "" );
	}

}
