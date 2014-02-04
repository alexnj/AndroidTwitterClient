package com.alexnj.twitterclient.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6185387492574160062L;

	@Column(name = "Body")
	private String body;

	@Column(name = "TweetId")
	private long uid;

	@Column(name = "IsFavorited")
	private boolean favorited;

	@Column(name = "IsRetweeted")
	private boolean retweeted;

	@Column(name = "User")
	private User user;

	@Column(name = "Tag", index = true)
	private String tag;

	public User getUser() {
		return user;
	}
	
	public void setSerializationTag(String serializationTag) {
		tag = serializationTag;
	}

	public String getBody() {
		return body;
	}

	public long getTweetId() {
		return uid;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.favorited = jsonObject.getBoolean("favorited");
			tweet.retweeted = jsonObject.getBoolean("retweeted");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}

	@Override
	public String toString() {
		return "(TWEET: Body:"+body+", User:"+user.toString()+")";
	}
}