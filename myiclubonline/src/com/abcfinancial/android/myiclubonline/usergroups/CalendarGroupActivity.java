package com.abcfinancial.android.myiclubonline.usergroups;

import android.content.Intent;
import android.os.Bundle;

import com.abcfinancial.android.myiclubonline.user.CalendarActivity;

public class CalendarGroupActivity extends MainGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("Calendar", new Intent(this, CalendarActivity.class));
	}
}
