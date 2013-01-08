package com.abcfinancial.android.myiclubonline.user;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.user.classes.PickLocationActivity;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EventsActivity extends ListActivity {
	static final String[] ACTIONS = new String[] { "Book Appointment", "Enroll in Class" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.tab_activity_events, ACTIONS));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 1) {
					Intent intent = new Intent(getParent(), PickLocationActivity.class);
					EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
					parentActivity.startChildActivity(ACTIONS[1], intent);
				}
			}
		});
	}
}