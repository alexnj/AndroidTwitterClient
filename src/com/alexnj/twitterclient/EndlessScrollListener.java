package com.alexnj.twitterclient;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	private int visibleThreshold = 5;
	private int lastRequested = 0;

    public EndlessScrollListener() {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("DEBUG", "onScroll" + firstVisibleItem + " and firstVisibleItem " + visibleItemCount+ " and total " + totalItemCount);
        if( firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreshold ) {
            if(lastRequested < totalItemCount) {
                lastRequested = totalItemCount;
                Log.d("DEBUG", "onScroll Request more");
                onLoadMore(totalItemCount);
            }
        }
    }

    public abstract void onLoadMore(int start);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}