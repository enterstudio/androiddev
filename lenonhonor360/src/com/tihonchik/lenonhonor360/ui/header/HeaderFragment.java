package com.tihonchik.lenonhonor360.ui.header;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.ui.user.AboutFragment;

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

		ImageView about = (ImageView) header.findViewById(R.id.iv_info_logo);
		about.setOnClickListener(mAboutListener);

		return header;
	}

	OnClickListener mAboutListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Fragment f = new AboutFragment();
			getFragmentManager().beginTransaction()
					.replace(R.id.body, f, "AboutFragment").commit();
		}
	};
}
