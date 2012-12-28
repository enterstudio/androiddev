package com.abcfinancial.android.myiclubonline;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.R.layout;
import com.abcfinancial.android.myiclubonline.user.AccountActivity;
import com.abcfinancial.android.myiclubonline.user.BarcodeActivity;
import com.abcfinancial.android.myiclubonline.user.CalendarActivity;
import com.abcfinancial.android.myiclubonline.user.EventsActivity;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;
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
        
//        TabSpec accountSpec = tabHost.newTabSpec("Account");
        // setting Title and Icon for the Tab
//        accountSpec.setIndicator("Account");
//        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
//        Intent accountIntent = new Intent(this, AccountActivity.class);
//        accountSpec.setContent(accountIntent);
 
        TabSpec calendarSpec = tabHost.newTabSpec("Calendar");
        calendarSpec.setIndicator("Calendar");
        Intent calendarIntent = new Intent(this, CalendarActivity.class);
        calendarSpec.setContent(calendarIntent);
 
        TabSpec eventsSpec = tabHost.newTabSpec("Events");
        eventsSpec.setIndicator("Events");
        Intent eventsIntent = new Intent(this, EventsActivity.class);
        eventsSpec.setContent(eventsIntent);
 
        TabSpec barcodeSpec = tabHost.newTabSpec("Barcode");
        barcodeSpec.setIndicator("Barcode");
        Intent barcodeIntent = new Intent(this, BarcodeActivity.class);
        barcodeSpec.setContent(barcodeIntent);
        
        // Adding all TabSpec to TabHost
//        tabHost.addTab(accountSpec);
        tabHost.addTab(calendarSpec);
        tabHost.addTab(eventsSpec);
        tabHost.addTab(barcodeSpec);
    }
}