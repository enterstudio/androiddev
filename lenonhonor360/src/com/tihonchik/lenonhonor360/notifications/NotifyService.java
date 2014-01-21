package com.tihonchik.lenonhonor360.notifications;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotifyService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;

	NotifyServiceReceiver notifyServiceReceiver;

	private static final int LH360_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification notification;

	@Override
	public void onCreate() {
		notifyServiceReceiver = new NotifyServiceReceiver();
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		registerReceiver(notifyServiceReceiver, intentFilter);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class),
				Intent.FLAG_ACTIVITY_NEW_TASK);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		notification = builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.notificationicon)
				.setTicker("Notification!").setWhen(System.currentTimeMillis())
				.setAutoCancel(true).setContentTitle("Notification!")
				.setContentText("Exercise of Notification!").build();

		notificationManager.notify(LH360_NOTIFICATION_ID, notification);
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
			int rqs = arg1.getIntExtra("RQS", 0);
			if (rqs == RQS_STOP_SERVICE) {
				stopSelf();
			}
		}
	}
}