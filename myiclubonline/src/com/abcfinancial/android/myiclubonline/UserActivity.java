package com.abcfinancial.android.myiclubonline;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.R.layout;
import com.abcfinancial.android.myiclubonline.user.AccountActivity;
import com.abcfinancial.android.myiclubonline.user.BarcodeActivity;
import com.abcfinancial.android.myiclubonline.user.CalendarActivity;
import com.abcfinancial.android.myiclubonline.user.EventsActivity;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;
import com.abcfinancial.android.myiclubonline.usergroups.CalendarGroupActivity;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;
import com.abcfinancial.android.myiclubonline.usergroups.MainGroupActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class UserActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
 
        TabHost tabHost = getTabHost();
 
        tabHost.addTab(tabHost.newTabSpec("Account")
        .setIndicator("Account")
        .setContent(new Intent(this, AccountGroupActivity.class)));
        
        tabHost.addTab(tabHost.newTabSpec("Calendar")
        .setIndicator("Calendar")
        .setContent(new Intent(this, CalendarGroupActivity.class)));
 
        tabHost.addTab(tabHost.newTabSpec("Events")
        .setIndicator("Events")
        .setContent(new Intent(this, EventsGroupActivity.class)));
 
        tabHost.addTab(tabHost.newTabSpec("Barcode")
        .setIndicator("Barcode")
        .setContent(new Intent(this, BarcodeActivity.class)));
    }
}