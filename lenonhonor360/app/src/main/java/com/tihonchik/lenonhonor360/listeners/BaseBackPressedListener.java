package com.tihonchik.lenonhonor360.listeners;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.user.BlogDisplayFragment;

import android.support.v4.app.FragmentActivity;

public class BaseBackPressedListener implements OnBackPressedListener {
	private final FragmentActivity activity;

	public BaseBackPressedListener(FragmentActivity activity) {
		this.activity = activity;
	}

	@Override
	public void goBack() {
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.body, new BlogDisplayFragment(),
						"BlogDisplayFragment").commit();
	}
}
