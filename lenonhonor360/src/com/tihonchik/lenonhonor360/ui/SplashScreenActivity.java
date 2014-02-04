package com.tihonchik.lenonhonor360.ui;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.parser.HtmlParser;
import com.tihonchik.lenonhonor360.services.BlogPullService;
import com.tihonchik.lenonhonor360.services.LaunchReceiver;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;

public class SplashScreenActivity extends BaseActivity {

	class HtmlParserTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... args) {
			try {
				HtmlParser.parseBlog();
			} catch (MalformedURLException exception) {
				Log.d("LH360", " > MalformedURLException: " + exception);
			} catch (IOException exception) {
				Log.d("LH360", " > URISyntaxException: " + exception);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
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

		Intent intent = new Intent(getApplicationContext(),
				BlogPullService.class);
		final PendingIntent pendingIntent = PendingIntent.getService(this,
				LaunchReceiver.REQUEST_CODE, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(),
				AppDefines.SERVICE_NOTIFICATION_INTERVAL, pendingIntent);

		new HtmlParserTask().execute();
	}
}