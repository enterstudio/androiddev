package com.tihonchik.lenonhonor360.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.parser.HtmlParser;
import com.tihonchik.lenonhonor360.util.AppUtils;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;

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
		Map<String, String> info = BlogEntryUtils.getNewestBlogInfo();
		String title = info.get("title");
		String text = info.get("blog");
		if (AppUtils.safeEmpty(title) && AppUtils.safeEmpty(text)) {
			return;
		} else {
			if (AppUtils.safeEmpty(title)) {
				title = "New Blog!";
			}
			if (AppUtils.safeEmpty(text)) {
				text = "";
			} else if (text.length() > 150) {
				text = text.substring(0, 147) + "...";
			}
		}
		lh360notification = new NotificationCompat.Builder(
				getApplicationContext())
				.setSmallIcon(R.drawable.notificationicon).setTicker(title)
				.setWhen(System.currentTimeMillis()).setAutoCancel(true)
				.setContentTitle(title).setDefaults(Notification.DEFAULT_ALL)
				.setContentText(text).build();

		notificationManager.notify(AppDefines.LH360_NOTIFICATION_ID,
				lh360notification);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}
}