package com.abcfinancial.android.myiclubonline.user.appointments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.adapters.SectionedAdapter;
import com.abcfinancial.android.myiclubonline.common.Utils;

public class PickAppointmentActivity extends ListActivity {
	
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
				}
				adapter.addSection(name, new SimpleAdapter(this, apptInfo, R.layout.list_complex, new String[] { Utils.ITEM_TITLE, Utils.ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));  
			}
	        ListView list = new ListView(this);  
	        list.setAdapter(adapter);  
	        this.setContentView(list); 
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
	
	
}