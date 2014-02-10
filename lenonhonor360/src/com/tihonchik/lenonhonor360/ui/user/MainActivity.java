package com.tihonchik.lenonhonor360.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tihonchik.lenonhonor360.ui.BaseActivity;
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
			Fragment f = new BlogDisplayFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.body, f, "BlogDisplayFragment").commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

/*			Bundle arguments = new Bundle();
			arguments.putSerializable(AppDefines.TAG_BLOG_DISPLAY_DETAIL,
					BlogEntryUtils.getNewestBlog());

			Fragment blogDetailFragment = new BlogDetailFragment();
			blogDetailFragment.setArguments(arguments);

			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.body, blogDetailFragment,
							AppDefines.TAG_BLOG_DETAIL).commit();
*/	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
