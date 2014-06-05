package com.tihonchik.motogear;

import java.text.DecimalFormat;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class MotoGearCalculatorApp extends Application {

	private static final String TAG = "MGC-MotoGearCalculatorApp";
	private static Context mContext;
	public static MotoGearCalculatorApp defaultInstance;

	private String mVersionNumber = "UNKNOWN";
	private String mPackageName = "UNKNOWN";
	private int mVersionCode = 0;
	private String mUserAgent = "UNKNOWN_UA";

	@Override
	public void onCreate() {
		Log.i("MotoGearCalculator",
				"Welcome to the MotoGear Calculator Android Application.");

		if (BuildConfig.DEBUG) {
			// Enable strict mode - No I/O on main thread
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyLog()
					// .penaltyDialog()
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyLog().build());
		}

		MotoGearCalculatorApp.mContext = getApplicationContext();
		MotoGearCalculatorApp.defaultInstance = this;
		CookieSyncManager.createInstance(mContext);
		CookieManager.getInstance().setAcceptCookie(true);

		// Gather up the version numbers
		try {
			mPackageName = getPackageName();
			PackageInfo info = getPackageManager().getPackageInfo(mPackageName,
					0);
			mVersionNumber = info.versionName;
			if (BuildConfig.DEBUG) {
				mVersionNumber += ".debug";
			}
			mVersionCode = info.versionCode;
			mUserAgent = String.format("%s/%s", mPackageName, mVersionNumber);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

		if (BuildConfig.DEBUG) {
			Log.d(TAG, " > " + mVersionNumber + " [" + mVersionCode + "]");
			Log.d(TAG, " > User-Agent: " + mUserAgent);
			Log.d(TAG,
					" > Max Heap: "
							+ new DecimalFormat().format(Runtime.getRuntime()
									.maxMemory() / 1048576.0));
		}

/*
		Configuration configuration = new Configuration.Builder(this)
				.withAPIKey("6a72a2d7131b3efa183d12ec774d34bb1f49d763").build();

		Apphance.startNewSession(MotoGearCalculatorApp.this, configuration);
*/
	}

	public static MotoGearCalculatorApp getInstance() {
		return MotoGearCalculatorApp.defaultInstance;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	// Context for routines not explicitly in an Activity.
	public static Context getAppContext() {
		return mContext;
	}

	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) MotoGearCalculatorApp.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public String getVersion() {
		return mVersionNumber;
	}

	public int getVersionCode() {
		return mVersionCode;
	}

	public String getUserAgent() {
		return mUserAgent;
	}
}