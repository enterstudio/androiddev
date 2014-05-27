package com.tihonchik.lenonhonor360.ui.user;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseActivity;
import com.tihonchik.lenonhonor360.util.AppUtils;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends BaseActivity {

	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		String url = AppDefines.LENONHONOR_360_WEBSITE_URI;

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			String mode = extras.getString(AppDefines.WEBVIEW_MODE);
			if(!AppUtils.safeEmpty(mode) && !AppDefines.YES.equals(mode)) {
				url = getIntent().getDataString().replace("fakehttp://", "http://");
			}
		}

		webView = (WebView) findViewById(R.id.webViewActivity);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
	}

	@Override
	protected Context getContextforBase() {
		return WebViewActivity.this;
	}
}