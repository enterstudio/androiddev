package com.tihonchik.motogear.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tihonchik.motogear.R;
import com.tihonchik.motogear.ui.BaseFragment;

public class CustomSetupFragment extends BaseFragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.custom_setup, container, false);

		return rootView;
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
}

