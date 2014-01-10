package com.tihonchik.lenonhonor360.ui;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment.BaseCallbacks;
import com.tihonchik.lenonhonor360.util.AppUtils;

import android.app.Dialog;
import android.content.Context;
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
	}

	@Override
	protected void onDestroy() {
		Log.v("LH360", "A onDestroy " + this.getClass().getName());
		super.onDestroy();
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
		/*
		 * LayoutInflater inflater = (LayoutInflater)
		 * getSystemService(Context.LAYOUT_INFLATER_SERVICE); View dialogView =
		 * inflater.inflate(R.layout.popup_single_error, null);
		 * dialogView.setLayoutParams(new ViewGroup.LayoutParams(
		 * ViewGroup.LayoutParams.WRAP_CONTENT,
		 * ViewGroup.LayoutParams.WRAP_CONTENT));
		 */

		final Dialog dialog = new Dialog(mContext, R.style.LH360DialogTheme);
		// dialog.setContentView(dialogView);
		dialog.setCancelable(false);

		/*
		 * // Grab the message and buttons from the Content Package String
		 * errorMessage = AppContent.getInstance() .getErrorString(errorCode);
		 * 
		 * List<String> buttons; boolean isMissingError =
		 * errorMessage.isEmpty();
		 * 
		 * if (isMissingError) { // Handle missing error message errorMessage =
		 * getResources().getString( R.string.missing_error_message); buttons =
		 * new ArrayList<String>(); buttons.add("ok"); } else { // Add the
		 * dynamic buttons buttons =
		 * AppContent.getInstance().getErrorButtons(errorCode); }
		 * 
		 * // Grab the error code for debugging purposes if (BuildConfig.DEBUG)
		 * { errorMessage += "\n\n" + errorCode; }
		 * 
		 * TextView tv = (TextView) dialogView.findViewById(R.id.tv_error_msg);
		 * // tv.setTypeface(FontUtils.getEffraLight());
		 * tv.setText(errorMessage);
		 * 
		 * ViewGroup buttonList = (ViewGroup) dialogView
		 * .findViewById(R.id.buttonLayout); ViewGroup scrollButtonList =
		 * (ViewGroup) dialogView .findViewById(R.id.ll_scrollView); for (String
		 * buttonType : buttons) { Button button; if
		 * (buttonType.equals(buttons.get(buttons.size() - 1))) { button =
		 * (Button) inflater.inflate(R.layout.inc_primary_button, null); } else
		 * { button = (Button) inflater.inflate( R.layout.inc_secondary_button,
		 * null); } // button.setTypeface(FontUtils.getEffraMedium()); // Going
		 * to use this to test if the button is configured properly
		 * button.setText("");
		 * 
		 * if ("ok".equalsIgnoreCase(buttonType)) { button.setText(R.string.ok);
		 * button.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { dialog.dismiss(); } }); }
		 * else if ("cancel".equalsIgnoreCase(buttonType)) {
		 * button.setText(R.string.cancel); button.setOnClickListener(new
		 * View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { dialog.cancel(); } }); } else
		 * if ("visitfullsite".equalsIgnoreCase(buttonType)) {
		 * button.setText(R.string.visit_full_website);
		 * button.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // Bring up the web-site in
		 * the external browser Intent intent = new Intent(Intent.ACTION_VIEW);
		 * intent.setData(Uri .parse(AppDefines.LENONHONOR_WEBSITE_URI));
		 * startActivity(intent); } }); } // Make sure the button is configured
		 * if (!button.getText().equals("")) { buttonList.addView(button); } }
		 */

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = AppUtils.getPopupWidth();
		lp.height = AppUtils.getPopupHeight();
		dialog.getWindow().setAttributes(lp);

		return dialog;
	}
}