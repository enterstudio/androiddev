package com.tihonchik.lenonhonor360.ui.user;

import android.content.Context;
import android.os.Bundle;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseActivity;

public class BlogDetailActivity extends BaseActivity {

	@Override
	protected Context getContextforBase() {
		return BlogDetailActivity.this;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_blog_detail;
	}
}