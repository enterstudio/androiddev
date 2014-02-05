package com.tihonchik.lenonhonor360.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tihonchik.lenonhonor360.ui.BaseActivity;
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
			Fragment f = new BlogDisplayFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.body, f, "BlogDisplayFragment").commit();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
