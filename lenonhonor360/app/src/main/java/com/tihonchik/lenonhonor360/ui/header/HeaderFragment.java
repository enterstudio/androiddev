package com.tihonchik.lenonhonor360.ui.header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.ui.user.AboutFragment;
import com.tihonchik.lenonhonor360.ui.user.BlogDisplayFragment;
import com.tihonchik.lenonhonor360.ui.user.WebViewActivity;

public class HeaderFragment extends BaseFragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Activity a = getActivity();
		if (a == null) {
			return;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View header = inflater.inflate(R.layout.header_fragment,
				container, true);

		ImageView home = (ImageView) header.findViewById(R.id.iv_nav_logo);
		home.setOnClickListener(mHomeListener);

		ImageView about = (ImageView) header.findViewById(R.id.iv_info_logo);
		about.setOnClickListener(mAboutListener);

		ImageView web = (ImageView) header.findViewById(R.id.iv_web_logo);
		web.setOnClickListener(mWebListener);

		return header;
	}

	OnClickListener mHomeListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Fragment f = new BlogDisplayFragment();
			getFragmentManager().beginTransaction()
					.replace(R.id.body, f, "BlogDisplayFragment").commit();
		}
	};

	OnClickListener mAboutListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Fragment f = new AboutFragment();
			getFragmentManager().beginTransaction()
					.replace(R.id.body, f, "AboutFragment").commit();
		}
	};

	OnClickListener mWebListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), WebViewActivity.class);
			intent.putExtra(AppDefines.WEBVIEW_MODE, AppDefines.YES);
			startActivity(intent);
		}
	};
}
