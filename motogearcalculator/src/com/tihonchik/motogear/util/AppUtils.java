package com.tihonchik.motogear.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.tihonchik.motogear.app.MotoGearCalculatorApp;

public class AppUtils {
	private static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
	private static int mScreenWidth;
	private static int mScreenHeight;

	@SuppressWarnings("deprecation")
	public static void initializeMotoGearCalculatorApp(Context context) {
		Display display = ((WindowManager) MotoGearCalculatorApp
				.getAppContext().getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		if (SDK_VERSION < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mScreenWidth = display.getWidth();
			mScreenHeight = display.getHeight();
		} else {
			Point size = new Point();
			display.getSize(size);
			mScreenWidth = size.x;
			mScreenHeight = size.y;
		}
	}

	public static int getPopupHeight() {
		return (int) (mScreenHeight * 0.7);
	}

	public static int getScreenHeight() {
		return mScreenHeight;
	}

	public static int getPopupWidth() {
		return mScreenWidth - 40;
	}

	public static Drawable getImageFromURL(String url)
			throws MalformedURLException, IOException {
		return Drawable.createFromStream(
				((InputStream) new URL(url).getContent()), "image");
	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getFormattedLink(String link) {
		return "<a href='fakehttp://" + link + "/'>" + link + "</a>";
	}

	public static float dipToPixels(Context context, int dipValue) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				metrics);
	}

	public static boolean safeEmpty(String value) {
		return (value == null || "".equals(value));
	}
}
