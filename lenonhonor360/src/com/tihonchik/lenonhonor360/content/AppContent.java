package com.tihonchik.lenonhonor360.content;

import java.util.ArrayList;
import java.util.List;

import com.tihonchik.lenonhonor360.app.LenonHonor360App;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppContent {
	static private AppContent mInstance;
	private SharedPreferences mPrefs = null;
	private Context mContext;
	private ArrayList<String> mValidButtons = new ArrayList<String>();
	private static final String[] KNOWN_BUTTONS = { "ok", "cancel",
			"visitfullsite" };

	private AppContent() {
		this.mContext = LenonHonor360App.getAppContext();
		// initialize();
		for (String button : KNOWN_BUTTONS) {
			mValidButtons.add(button);
		}
	}

	synchronized public static AppContent getInstance() {
		if (mInstance == null) {
			mInstance = new AppContent();
		}
		return mInstance;
	}

	/*
	 * private void initialize() { mPrefs =
	 * mContext.getSharedPreferences(CONTENT_FILE, Context.MODE_PRIVATE);
	 * mEditor = mPrefs.edit(); }
	 */

	public String getErrorString(final String errorCode) {
		return mPrefs.getString(String.format("EN_%s", errorCode), "");
	}

	public List<String> getErrorButtons(final String errorCode) {
		List<String> buttons = new ArrayList<String>();
		String buttonString = mPrefs.getString(
				String.format("EN_%s.button", errorCode), "").trim();

		if (!buttonString.trim().isEmpty()) {
			for (String button : buttonString.trim().split(",")) {
				// Validate button against known buttons list
				if (mValidButtons.contains(button)) {
					buttons.add(button);
				} else {
					Log.e("LH360",
							"An unknown button type was returned by the content api. Type: "
									+ button);
				}
			}
		}

		// Make sure we always have AT LEAST an 'OK' button
		if (buttons.isEmpty()) {
			buttons.add("ok");
		}
		return buttons;
	}
}
