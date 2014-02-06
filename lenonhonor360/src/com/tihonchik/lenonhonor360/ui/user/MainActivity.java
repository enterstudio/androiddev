package com.tihonchik.lenonhonor360.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tihonchik.lenonhonor360.ui.BaseActivity;
import com.tihonchik.lenonhonor360.util.AppUtils;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;
import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;

public class MainActivity extends BaseActivity {

	@Override
	protected Context getContextforBase() {
		return MainActivity.this;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				args.putSerializable(AppDefines.TAG_BLOG_DISPLAY_DETAIL, blog);
				Fragment blogDisplayFragment = new BlogDetailFragment();
				blogDisplayFragment.setArguments(args);
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.body, blogDisplayFragment,
								AppDefines.TAG_BLOG_DETAIL).commit();
			} else {
				Fragment f = new BlogDisplayFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.body, f, "BlogDisplayFragment").commit();
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
