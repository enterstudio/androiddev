package com.tihonchik.lenonhonor360.ui.user;

import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.tihonchik.lenonhonor360.ui.BaseActivity;
import com.tihonchik.lenonhonor360.util.AppUtils;
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

		ImageView newBlogImage = (ImageView) findViewById(R.id.new_blog_image);
		try {
			int sdk = android.os.Build.VERSION.SDK_INT;
			if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				newBlogImage.setBackgroundDrawable(AppUtils
						.getImageFromURL("http://placehold.it/320x240"));
			} else {
				newBlogImage.setBackground(AppUtils
						.getImageFromURL("http://placehold.it/320x240"));
			}
		} catch (IOException exception) {
			// do nothing
		}

		// tf = FontUtils.getEffraMedium();
		Button newBlog = (Button) findViewById(R.id.btn_new_blog);
		// b.setTypeface(tf);
		newBlog.setOnClickListener(mLoginListener);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_main;
	}

	OnClickListener mLoginListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};
}
