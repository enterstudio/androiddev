package com.abcfinancial.android.myiclubonline.user.appointments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.adapters.SectionedAdapter;
import com.abcfinancial.android.myiclubonline.common.Utils;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

public class PickAppointmentActivity extends ListActivity {
	
	List<Integer> sectionSizeCount;
	List<JSONObject> orderedAppointmentList;	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			List<String> locations = new ArrayList<String>();
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String appointments = preferences.getString("mobileLoginBookableAppointments", "");
			JSONArray json = new JSONArray(appointments);
			String clubName, available;
			for( int i=0; i<json.length(); i++) {
				JSONObject appointment = json.getJSONObject(i);
				clubName = appointment.getString("clubName");
				if( !locations.contains(clubName)) {
					locations.add(clubName);
				}
			}
			
		    SectionedAdapter adapter = new SectionedAdapter(this);  
			List<Map<String,?>> apptInfo;
			sectionSizeCount = new ArrayList<Integer>();
			orderedAppointmentList = new ArrayList<JSONObject>();
			int elementCount = 0;
			for( String name : locations) {
				apptInfo = new LinkedList<Map<String,?>>();
				for( int i=0; i<json.length(); i++) {
					JSONObject appointment = json.getJSONObject(i);
					available = appointment.getString("available");
					if( "Free".equals(available) ) {
						available = "Sessions are free";
					} else {
						available += " available to use";
					}
			        apptInfo.add(Utils.createItem(appointment.getString("eventName") + " (Level: " + appointment.getString("levelName") + ")", available));
					orderedAppointmentList.add(appointment);
					elementCount++;
				}
				sectionSizeCount.add(elementCount);
				adapter.addSection(name, new SimpleAdapter(this, apptInfo, R.layout.list_complex, new String[] { Utils.ITEM_TITLE, Utils.ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));  
			}
	        ListView list = new ListView(this);  
	        list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					int totalCount = 0;
					JSONObject selectedAppointment = null;
					for(int i=0; i<sectionSizeCount.size(); i++) {
						if(position <= (i+1+sectionSizeCount.get(i)+totalCount)) {
							selectedAppointment = orderedAppointmentList.get(position-(i+1));
							break;
						}
						totalCount += sectionSizeCount.get(i);
					}
					try {
						JSONArray employees = selectedAppointment.getJSONArray("employees");
						if( employees.length() > 1 ) {
							Intent intent = new Intent(getParent(), PickTrainerActivity.class);
							intent.putExtra("SELECTED_APPT", selectedAppointment.toString());
							EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
							parentActivity.startChildActivity("PickTrainerActivity", intent);								
						} else {
							JSONObject employee = employees.getJSONObject(0);
							Intent intent = new Intent(getParent(), PickAppointmentDateActivity.class);
							intent.putExtra("SELECTED_APPT", selectedAppointment.toString());
							intent.putExtra("SELECTED_EMPLOYEE_ID", employee.getString("employeeId"));
							EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
							parentActivity.startChildActivity("PickAppointmentDateActivity", intent);								
						}
					} catch (JSONException exception) {
						// TODO: throw error dialog
					}
				}
			});
	        this.setContentView(list); 
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}