package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.AdapterView.OnItemClickListener;

public class PickClassTimeActivity extends ExpandableListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Bundle extras = getIntent().getExtras();
//	    int classListPosition = extras.getInt("CLASS_LIST_POSITION");
	    
		List<String> locations = new ArrayList<String>();
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String clubs = preferences.getString("mobileLoginClubs", "");
			JSONArray json = new JSONArray(clubs);
			for( int i=0; i<json.length(); i++) {
				JSONObject club = json.getJSONObject(i);
				locations.add(club.getString("name"));
			}

		    ExpandableListView expandbleList = getExpandableListView();
		    expandbleList.setDividerHeight(2);
//		    expandbleList.setGroupIndicator(null);
//		    expandbleList.setClickable(true);

		    ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
		    ArrayList<String> child1 = new ArrayList<String>();
		    ArrayList<String> child2 = new ArrayList<String>();
		    child1.add("1");
		    child1.add("2");
		    groups.add(child1);
		    child2.add("3");
		    child2.add("4");
		    groups.add(child2);
		    
/*		    ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
	        listView.setAdapter(adapter);

		    
			expandbleList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				}
			});	*/		
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}