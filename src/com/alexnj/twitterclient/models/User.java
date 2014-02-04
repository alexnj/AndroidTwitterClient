package com.alexnj.twitterclient.models;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Users")
public class User extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7701926656570381811L;

	@Column(name="Name")
	private String name;
	
	@Column(name="UserId")
	private long uid;
	
	@Column(name="ScreenName")
	private String screenName;
	
	@Column(name="ProfileImageUrl")
	private String profileImageUrl;
	
	@Column(name="TweetCount")
	private int numTweets;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="FollowerCount")
	private int followersCount;
	
	@Column(name="FriendCount")
	private int friendsCount;
	
    public String getName() {
        return name;
    }

    public long getUserId() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }
    
    public String getDescription() {
        return description;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }
    
    public User() {
    	screenName = "";
    	name ="";
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
        	u.name = json.getString("name");
        	u.uid = json.getLong("id");
        	u.screenName = json.getString("screen_name");
        	u.profileImageUrl = json.getString("profile_image_url");
        	u.numTweets = json.getInt("statuses_count");
        	u.description = json.getString("description");
        	u.followersCount = json.getInt("followers_count");
        	u.friendsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    
    @Override
	public String toString() {
		return "(USER: Name:"+name+")";
	}
    
    public List<Tweet> tweets() {
        return getMany(Tweet.class, "User");
    }    

}