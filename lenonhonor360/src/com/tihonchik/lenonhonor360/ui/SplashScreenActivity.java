package com.tihonchik.lenonhonor360.ui;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.AppConfig;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;
import com.tihonchik.lenonhonor360.util.AppUtils;

public class SplashScreenActivity extends BaseActivity {
	private long splashDelay = AppConfig.SPLASHSCREENDELAY;
	private long startTimestamp;

	class getContentTask extends AsyncTask<String, String, Boolean> {
		TextView progressText;

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
		protected void onProgressUpdate(final String... values) {
			if (progressText == null) {
				progressText = (TextView) findViewById(R.id.tv_loading_screen);
			}
			progressText.setText(values[0]);
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
		
		ImageView splashImage = (ImageView) findViewById(R.id.splash_image);
		try {
			int sdk = android.os.Build.VERSION.SDK_INT;
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				splashImage.setBackgroundDrawable(AppUtils.getImageFromURL("http://placehold.it/320x400"));
			} else {
				splashImage.setBackground(AppUtils.getImageFromURL("http://placehold.it/320x400"));
			}
		} catch (IOException exception) {
			// do nothing
		}
		
		new getContentTask().execute();
	}
}