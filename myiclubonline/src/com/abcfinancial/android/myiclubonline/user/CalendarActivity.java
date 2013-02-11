package com.abcfinancial.android.myiclubonline.user;

import com.abcfinancial.android.myiclubonline.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;

public class CalendarActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity_calendar);

		CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {

			}
		});
		
		ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new String[]{"One","Two"}){

        });
	}
}