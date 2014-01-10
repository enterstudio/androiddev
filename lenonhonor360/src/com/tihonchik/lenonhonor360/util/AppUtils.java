package com.tihonchik.lenonhonor360.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

import com.tihonchik.lenonhonor360.app.LenonHonor360App;

public class AppUtils {

	private static int mScreenWidth;
	private static int mScreenHeight;

	public static void initializeLenonHonorApp(Context context) {
		Display display = ((WindowManager) LenonHonor360App.getAppContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mScreenWidth = display.getWidth();
		mScreenHeight = display.getHeight();
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
}
