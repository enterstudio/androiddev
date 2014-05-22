package com.tihonchik.motogear.ui.user;

import com.tihonchik.motogear.ui.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override
	protected Context getContextforBase() {
		return MainActivity.this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);
	}

	@Override
	public void onNewIntent(Intent intent) {
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}

