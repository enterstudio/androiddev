package com.abcfinancial.android.myiclubonline.usergroups;

import android.content.Intent;
import android.os.Bundle;

import com.abcfinancial.android.myiclubonline.user.BarcodeActivity;

public class BarcodeGroupActivity extends MainGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("Events", new Intent(this, BarcodeActivity.class));
	}
}