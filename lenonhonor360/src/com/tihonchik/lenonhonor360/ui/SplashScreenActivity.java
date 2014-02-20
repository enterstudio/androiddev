package com.tihonchik.lenonhonor360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.services.BlogPullService;

public class SplashScreenActivity extends BaseActivity {

	@Override
	protected Context getContextforBase() {
		return this;
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.splash_screen;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);

		Intent intent = new Intent(getApplicationContext(),
				BlogPullService.class);
		final PendingIntent pendingIntent = PendingIntent.getService(this,
				AppDefines.BROADCAST_REQUEST_CODE, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(),
				AppDefines.SERVICE_NOTIFICATION_INTERVAL, pendingIntent);
	}
}