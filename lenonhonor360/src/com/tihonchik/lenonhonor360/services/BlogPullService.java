package com.tihonchik.lenonhonor360.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BlogPullService extends IntentService {

    public BlogPullService() {
        super("BlogPullService");
     }
	
    
    @Override
    protected void onHandleIntent(Intent intent) {
       //TODO: call to HTML, send notification
		Log.d("LH360", " > BlogPullService onHandleIntent...");
    }
}