package com.tihonchik.lenonhonor360.ui.header;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment;

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
		// TODO: write onResume method to load correct elements
		Activity a = getActivity();
		if (a == null) {
			return;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Don't re-create this fragment on configuration changes
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View header = inflater.inflate(R.layout.header_fragment,
				container, true);
		return header;
	}
}
