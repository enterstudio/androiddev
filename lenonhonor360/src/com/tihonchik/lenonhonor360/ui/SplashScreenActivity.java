package com.tihonchik.lenonhonor360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tihonchik.lenonhonor360.AppConfig;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.services.BlogPullService;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;

public class SplashScreenActivity extends BaseActivity {
	private long splashDelay = AppConfig.SPLASHSCREENDELAY;
	private long startTimestamp;

	class loadContentTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... args) {
			startTimestamp = System.currentTimeMillis();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException exception) {
				Log.d("LH360", " > Exception: " + exception);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			long timeAlreadySpent = System.currentTimeMillis() - startTimestamp;
			splashDelay = splashDelay - timeAlreadySpent;
			if (splashDelay < 0) {
				splashDelay = 0;
			}
			if (splashDelay > AppConfig.SPLASHSCREENDELAY) {
				splashDelay = AppConfig.SPLASHSCREENDELAY;
			}

			if (result) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent i = new Intent(SplashScreenActivity.this,
								MainActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
					}
				}, splashDelay);
			} else {
				showSingleErrorDialog("appUnavailable");
			}
		}
	}

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

		Intent intent = new Intent(this, BlogPullService.class);
        intent.putExtra("com.tihonchik.lenonhonor360.triggerTime", 3000l);
        intent.putExtra(BlogPullService.PARAM_IN_MSG, "");
//        startService(intent);

        PendingIntent pendingIntent = PendingIntent.getService(this,  0,  intent, 0);
        long trigger = System.currentTimeMillis() + (3000);
        final AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);

        new loadContentTask().execute();
	}
}