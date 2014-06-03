package com.tihonchik.motogear.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class BaseFragment extends Fragment {

	public interface BaseCallbacks {
		void switchToFragment(Class<?> targetFragment);
		void switchToFragment(Class<?> targetFragment, Bundle b);
	}

	protected Context mContext = null;
	protected ProgressDialog progressDialog = null;
	protected BaseCallbacks mBaseCallbacks = null;

	public BaseFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;

		try {
			mBaseCallbacks = (BaseCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BaseCallbacks");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("LH360", "F onCreate " + this.getClass().getName());
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onResume() {
		Log.v("LH360", " F onResume() " + this.getClass().getName());
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.v("LH360", " F onPause() " + this.getClass().getName());
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// Make sure we don't hold on to any references to activities
		mBaseCallbacks = null;
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}