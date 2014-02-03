package com.alexnj.twitterclient.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexnj.twitterclient.R;
import com.alexnj.twitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets );
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if( view == null ) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_tweet, null);
		}
		
		Tweet tweet = getItem(position);
		
		ImageView ivImage = (ImageView)view.findViewById(R.id.ivUser);
		TextView  tvName  = (TextView) view.findViewById(R.id.tvName);
		TextView  tvBody  = (TextView) view.findViewById(R.id.tvBody);
		
		ImageLoader.getInstance().displayImage( tweet.getUser().getProfileImageUrl(), ivImage );
		
		tvName.setText(Html.fromHtml(tweet.getUser().getName()));
		tvBody.setText(Html.fromHtml(tweet.getBody()));
		
		return view;		
	}
	
	

}
