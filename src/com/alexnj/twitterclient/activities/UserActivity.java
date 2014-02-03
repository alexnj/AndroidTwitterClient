package com.alexnj.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.TwitterClientApp;
import com.alexnj.twitterclient.fragments.TweetListFragment;
import com.alexnj.twitterclient.fragments.UserTweetListFragment;
import com.alexnj.twitterclient.models.Tweet;
import com.alexnj.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserActivity extends FragmentActivity implements TweetListFragment.OnTweetClickedListener {
	private User user;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user);
		
		user = (User) getIntent().getSerializableExtra("User");
		if (user==null) {
			user = new User();
		}
		
		
		Bundle args = new Bundle();
        args.putSerializable("User", user);

        UserTweetListFragment fragment = new UserTweetListFragment();
        fragment.setArguments(args);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flContainer, fragment);
		ft.commit();
		
		
		JsonHttpResponseHandler userInfoHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				user = User.fromJson(jsonUser);
				
				Log.d("DEBUG", "jsonUser: " + jsonUser.toString());
				
				TextView tvMeta = (TextView) findViewById(R.id.tvMeta);
				setTitle("@"+user.getScreenName());
				
				TextView tvName = (TextView) findViewById(R.id.tvName);
				tvName.setText(user.getName());
				
				tvMeta.setText(user.getFollowersCount() + " followers. " + user.getFriendsCount() + " friends.");
				
				ImageView ivImage = (ImageView)findViewById(R.id.ivUser);
				
				TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
				tvDescription.setText(user.getDescription());
				
				ImageLoader.getInstance().displayImage( user.getProfileImageUrl(), ivImage );
			}
		};
		
		TwitterClientApp.getRestClient().getUserInfo(userInfoHandler, user.getScreenName());
	}

	@Override
	public void onTweetClicked(Tweet tweet) {
		// TODO Auto-generated method stub
		
	}
}