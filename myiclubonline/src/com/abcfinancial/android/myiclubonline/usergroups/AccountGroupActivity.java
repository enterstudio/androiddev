package com.abcfinancial.android.myiclubonline.usergroups;

import com.abcfinancial.android.myiclubonline.user.AccountActivity;

import android.content.Intent;
import android.os.Bundle;

public class AccountGroupActivity extends MainGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("Account", new Intent(this,
				AccountActivity.class));
	}
}
