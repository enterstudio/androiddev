package com.tihonchik.lenonhonor360.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tihonchik.lenonhonor360.listeners.OnBackPressedListener;
import com.tihonchik.lenonhonor360.ui.BaseActivity;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;
import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;

public class MainActivity extends BaseActivity {

	protected OnBackPressedListener onBackPressedListener;

	@Override
	protected Context getContextforBase() {
		return MainActivity.this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);

		if (savedInstanceState == null) {
			Fragment f = new BlogDisplayFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.body, f, "BlogDisplayFragment").commit();
		}
	}

	@Override
	public void onNewIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras != null) {
			int blogId = Integer.valueOf(extras
					.getString(AppDefines.BLOG_ID_KEY));

			Log.d("LH360", "BLOG ID: " + blogId);

			if (blogId > 0) {
				Bundle arguments = new Bundle();
				arguments.putSerializable(AppDefines.TAG_BLOG_DISPLAY_DETAIL,
						BlogEntryUtils.getBLogById(blogId));

				Fragment blogDetailFragment = new BlogDetailFragment();
				blogDetailFragment.setArguments(arguments);

				/*
				 * Looks like maybe Activity goes to background, and we try to perform a transaction - Mike T., 4/7/2014 
				 * Post with proposed solution: 
				 * http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa/10261438#10261438
				 */
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.body, blogDetailFragment,
								AppDefines.TAG_BLOG_DETAIL).commitAllowingStateLoss();
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (onBackPressedListener != null) {
			onBackPressedListener.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void setOnBackPressedListener(
			OnBackPressedListener onBackPressedListener) {
		this.onBackPressedListener = onBackPressedListener;
	}
}
