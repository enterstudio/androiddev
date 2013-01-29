package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

public class PickLocationActivity extends ListActivity {
	public List<String> clubNumbers;
	
	public void onCreate(Bundle savedInstanceState) {
		List<String> locations = new ArrayList<String>();
		clubNumbers = new ArrayList<String>();
		super.onCreate(savedInstanceState);
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String clubs = preferences.getString("mobileLoginClubs", "");
			JSONArray json = new JSONArray(clubs);
			for( int i=0; i<json.length(); i++) {
				JSONObject club = json.getJSONObject(i);
				if( club.getBoolean("onlineClasses")) {
					locations.add(club.getString("name"));
					clubNumbers.add(club.getString("number"));
				}
			}
			setListAdapter(new ArrayAdapter<String>(this, R.layout.events_class_picklocation, locations));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getParent(), PickClassActivity.class);
					intent.putExtra("CLUB_NUMBER", clubNumbers.get(position));
					EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
					parentActivity.startChildActivity(Integer.toString(position), intent);
				}
			});
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}