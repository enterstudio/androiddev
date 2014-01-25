package com.tihonchik.lenonhonor360.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tihonchik.lenonhonor360.AppConfig;
import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.services.BlogPullService;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;

public class SplashScreenActivity extends BaseActivity {
	private long splashDelay = AppConfig.SPLASHSCREENDELAY;
	private long startTimestamp;
	private Pattern hrefPattern = Pattern.compile("<a style.*?>");

	class loadContentTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... args) {
			startTimestamp = System.currentTimeMillis();
			try {

				URL url = new URL(AppDefines.LENONHONOR_APP_PHP_WEBSITE_URI);
				URLConnection conn = url.openConnection();
				InputStreamReader streamReader = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(streamReader);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}

				Matcher m = hrefPattern.matcher(sb.toString());
				List<String> list = new ArrayList<String>();
		        while(m.find()) {
		            list.add(m.group(1));
		        }
				
		        List<BlogEntry> entries = new ArrayList<BlogEntry>();
		        
		        
		        int newestBlogId = BlogEntryUtils.getNewestBlogId();
		        if( newestBlogId == -1) {
		        	BlogEntryUtils.insertBlogEntries(entries);
		        }
		        
				//Thread.sleep(3000);
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
		// startService(intent);

		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
				0);
		long trigger = System.currentTimeMillis() + (3000);
		final AlarmManager alarmManager = (AlarmManager) getBaseContext()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);

		new loadContentTask().execute();
	}
}