package com.tihonchik.lenonhonor360.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LaunchReceiver extends BroadcastReceiver {
	public static final String ACTION_PULSE_ALARM = "com.tihonchik.lenonhonor360.ACTION_PULSE_ALARM";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("LH360 - onReceive()... start service...");
		Intent serviceIntent = new Intent(context, BlogPullService.class);
		context.startService(serviceIntent);
	}
}
