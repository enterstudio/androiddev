package com.abcfinancial.android.myiclubonline.usergroups;

import android.content.Intent;
import android.os.Bundle;

import com.abcfinancial.android.myiclubonline.user.EventsActivity;

public class EventsGroupActivity extends MainGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("Events", new Intent(this, EventsActivity.class));
	}
}