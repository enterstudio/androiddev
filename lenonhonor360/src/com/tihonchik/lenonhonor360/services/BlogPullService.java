package com.tihonchik.lenonhonor360.services;

import java.io.IOException;
import java.net.MalformedURLException;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.parser.HtmlParser;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BlogPullService extends IntentService {

	NotificationManager notificationManager;
	Notification lh360notification;

	public BlogPullService() {
		super("BlogPullService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("LH360", " > BlogPullService onHandleIntent...");
		try {
			if (AppDefines.ISSUE_NOTIFICAION.equals(HtmlParser.parseBlog())) {
				sendNotification();
			}
		} catch (MalformedURLException exception) {
			Log.d("LH360", " > MalformedURLException: " + exception);
		} catch (IOException exception) {
			Log.d("LH360", " > URISyntaxException: " + exception);
		}
	}

	private void sendNotification() {
		lh360notification = new NotificationCompat.Builder(
				getApplicationContext())
				.setSmallIcon(R.drawable.notificationicon)
				.setTicker("Notification!").setWhen(System.currentTimeMillis())
				.setAutoCancel(true).setContentTitle("Notification!")
				.setDefaults(Notification.DEFAULT_ALL)
				.setContentText("LenonHonor360 Notification!").build();

		notificationManager.notify(AppDefines.LH360_NOTIFICATION_ID,
				lh360notification);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}
}