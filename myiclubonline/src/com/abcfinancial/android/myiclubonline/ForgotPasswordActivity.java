package com.abcfinancial.android.myiclubonline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ForgotPasswordActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_forgotpassword);

		TextView loginScreen = (TextView) findViewById(R.id.btnCancelForgot);
		loginScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});		
	}
}
