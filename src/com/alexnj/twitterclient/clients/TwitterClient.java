package com.alexnj.twitterclient.clients;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.alexnj.twitterclient.R;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CALLBACK_URL = "oauth://TwitterClient"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, TwitterApi.class, REST_URL, 
        		context.getString(R.string.consumer_key), context.getString(R.string.consumer_secret), 
        		REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, String maxId) {
    	RequestParams params = new RequestParams();
    	if (!maxId.isEmpty()) {
    		params.put("max_id", maxId);
    	}
		client.get(getApiUrl("statuses/home_timeline.json"), maxId.isEmpty() ? null : params, handler);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, String maxId) {
    	RequestParams params = new RequestParams();
    	if (!maxId.isEmpty()) {
    		params.put("max_id", maxId);
    	}
        client.get(getApiUrl("statuses/mentions_timeline.json"), maxId.isEmpty() ? null : params, handler);
    }
    
    public void getUserInfo(AsyncHttpResponseHandler handler, String screenName) {
    	RequestParams params = new RequestParams();
    	if (!screenName.isEmpty()) {
    		params.put("screen_name", screenName);
    		client.get(getApiUrl("users/show.json"), params, handler);
    	}
    	else {
    		client.get(getApiUrl("account/verify_credentials.json"), null, handler);
    	}
        
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, String screenName, String maxId) {
    	RequestParams params = new RequestParams();
    	params.put("screen_name",screenName);
    	if (!maxId.isEmpty()) {
    		params.put("max_id", maxId);
    	}
        client.get(getApiUrl("statuses/user_timeline.json"), params, handler);
    }
    
    public Boolean postUpdate(String update, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
    	params.put("status", update);
    	client.post(getApiUrl("statuses/update.json"), params, handler);
    	return true;
    }
   
}