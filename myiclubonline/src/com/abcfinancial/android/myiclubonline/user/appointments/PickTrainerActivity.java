package com.abcfinancial.android.myiclubonline.user.appointments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

public class PickTrainerActivity extends ListActivity {

	List<String> trainerIds;
	JSONObject appointment;
	String selectedAppt;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Bundle extras = getIntent().getExtras();
			selectedAppt = extras.getString("SELECTED_APPT");
			appointment = new JSONObject(selectedAppt);
			JSONArray employees = appointment.getJSONArray("employees");
			
			List<String> trainers = new ArrayList<String>();
			trainerIds = new ArrayList<String>();
			for( int i=0; i<employees.length(); i++) {
				JSONObject employee = employees.getJSONObject(i);
				trainers.add(employee.getString("employeeName"));
				trainerIds.add(employee.getString("employeeId"));
			}
			setListAdapter(new ArrayAdapter<String>(this, R.layout.events_class_picklocation, trainers));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getParent(), PickAppointmentDateActivity.class);
					intent.putExtra("SELECTED_APPT", selectedAppt);
					intent.putExtra("SELECTED_EMPLOYEE_ID", trainerIds.get(position));
					EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
					parentActivity.startChildActivity("PickAppointmentDateActivity", intent);	
				}
			});
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}
