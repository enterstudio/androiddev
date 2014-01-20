package com.tihonchik.lenonhonor360.notifications;

import com.tihonchik.lenonhonor360.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

public class NotifyService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;

	NotifyServiceReceiver notifyServiceReceiver;

	private static final int LH360_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification notification;
	private final String myBlog = "http://android-er.blogspot.com/";

	@Override
	public void onCreate() {
		notifyServiceReceiver = new NotifyServiceReceiver();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		registerReceiver(notifyServiceReceiver, intentFilter);

		// Send Notification
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.notificationicon, "Notification!",
				System.currentTimeMillis());
		Context context = getApplicationContext();
		String notificationTitle = "Exercise of Notification!";
		String notificationText = "http://android-er.blogspot.com/";
		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
		PendingIntent pendingIntent = PendingIntent.getActivity(
				getBaseContext(), 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, notificationTitle,
				notificationText, pendingIntent);
		notificationManager.notify(LH360_NOTIFICATION_ID, notification);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		this.unregisterReceiver(notifyServiceReceiver);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public class NotifyServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			int rqs = arg1.getIntExtra("RQS", 0);
			if (rqs == RQS_STOP_SERVICE) {
				stopSelf();
			}
		}
	}
}