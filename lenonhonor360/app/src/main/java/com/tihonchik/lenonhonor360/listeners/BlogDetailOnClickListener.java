package com.tihonchik.lenonhonor360.listeners;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.ui.user.BlogDetailFragment;

public class BlogDetailOnClickListener implements OnClickListener {
	private BlogEntry blog = null;
	private final FragmentActivity activity;

	public BlogDetailOnClickListener(FragmentActivity activity, BlogEntry blog) {
		this.activity = activity;
		this.blog = blog;
	}

	@Override
	public void onClick(View v) {
		Bundle args = new Bundle();
		args.putSerializable(AppDefines.TAG_BLOG_DISPLAY_DETAIL, blog);
		Fragment blogDisplayFragment = new BlogDetailFragment();
		blogDisplayFragment.setArguments(args);
		activity.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.body, blogDisplayFragment,
						AppDefines.TAG_BLOG_DETAIL)
				.addToBackStack(AppDefines.TAG_BLOG_DETAIL).commit();
	}
}