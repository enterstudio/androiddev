package com.tihonchik.motogear.ui;

import com.tihonchik.motogear.R;
import com.tihonchik.motogear.ui.BaseFragment.BaseCallbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.webkit.CookieSyncManager;

public abstract class BaseActivity extends FragmentActivity implements
		BaseCallbacks {

	private Context mContext = null;

	public BaseActivity() {
	}

	protected abstract Context getContextforBase();

	protected int getContentLayoutId() {
		return R.layout.base_panel_layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("MGC", "A onCreate " + this.getClass().getName());
		super.onCreate(savedInstanceState);

		setContentView(getContentLayoutId());
		mContext = getContextforBase();
	}

	@Override
	protected void onDestroy() {
		Log.v("MGC", "A onDestroy " + this.getClass().getName());
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.v("MGC", "A onPause " + this.getClass().getName());
		super.onPause();
		CookieSyncManager.getInstance().sync();
	}

	@Override
	protected void onResume() {
		Log.v("MGC", "A onResume " + this.getClass().getName());
		super.onResume();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	public void switchToFragment(Class<?> targetFragment) {
		switchToFragment(targetFragment, null);
	}

	@Override
	public void switchToFragment(Class<?> targetFragment, Bundle b) {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

		try {
			Fragment f = (Fragment) targetFragment.newInstance();
			if (b != null)
				f.setArguments(b);
			fm.beginTransaction().replace(R.id.content_frame, f, "").commit();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}