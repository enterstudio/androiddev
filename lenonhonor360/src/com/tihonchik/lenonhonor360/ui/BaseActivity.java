package com.tihonchik.lenonhonor360.ui;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.services.LaunchReceiver;
import com.tihonchik.lenonhonor360.ui.BaseFragment.BaseCallbacks;
import com.tihonchik.lenonhonor360.util.AppUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;

public abstract class BaseActivity extends FragmentActivity implements
		BaseCallbacks {

	public static final int RESULT_LOGOUT = 1001;
	public static final int RESULT_HOME = 1002;

	protected Dialog mDialog = null;
	private Context mContext = null;

	private LaunchReceiver receiver;

	public BaseActivity() {
	}

	protected abstract Context getContextforBase();

	protected int getContentLayoutId() {
		return R.layout.base_panel_layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("LH360", "A onCreate " + this.getClass().getName());
		super.onCreate(savedInstanceState);

		setContentView(getContentLayoutId());
		mContext = getContextforBase();

		if (receiver == null) {
			IntentFilter filter = new IntentFilter(
					LaunchReceiver.ACTION_PULSE_ALARM);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new LaunchReceiver();
			registerReceiver(receiver, filter);
		}
	}

	@Override
	protected void onDestroy() {
		Log.v("LH360", "A onDestroy " + this.getClass().getName());
		super.onDestroy();
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}

	@Override
	protected void onPause() {
		Log.v("LH360", "A onPause " + this.getClass().getName());
		super.onPause();
		CookieSyncManager.getInstance().sync();
	}

	@Override
	protected void onResume() {
		Log.v("LH360", "A onResume " + this.getClass().getName());
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
			fm.beginTransaction().replace(R.id.body, f, "").commit();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	protected void showSingleErrorDialog(String errorCode) {
		getSingleErrorDialog(errorCode).show();
	}

	protected Dialog getSingleErrorDialog(String errorCode) {

		final Dialog dialog = new Dialog(mContext, R.style.LH360DialogTheme);
		// dialog.setContentView(dialogView);
		dialog.setCancelable(false);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = AppUtils.getPopupWidth();
		lp.height = AppUtils.getPopupHeight();
		dialog.getWindow().setAttributes(lp);

		return dialog;
	}
}