package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

public class PickLocationActivity extends ListActivity {
	public List<String> clubNumbers;
	public String memberId;
	public String clubNumber;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> locations = new ArrayList<String>();
		clubNumbers = new ArrayList<String>();
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			memberId = preferences.getString("memberId", "");
			String clubs = preferences.getString("mobileLoginClubs", "");
			JSONArray json = new JSONArray(clubs);
			for( int i=0; i<json.length(); i++) {
				JSONObject club = json.getJSONObject(i);
				if( club.getBoolean("onlineClasses")) {
					locations.add(club.getString("name"));
					clubNumbers.add(club.getString("number"));
				}
			}
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_simple, locations));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					clubNumber = clubNumbers.get(position); 
					new WebServiceTask().execute("clubclasslist", memberId, clubNumber);
				}
			});
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
	
	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			client.addParameter("memberId", uri[1]);
			client.addParameter("club", uri[2]);
			client.addHeader("Authorization", "Basic cWE6dGVzdA==");
			try {
				client.execute(RequestMethod.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			Intent intent = new Intent(getParent(), PickClassActivity.class);
			intent.putExtra("CLUB_CLASSES", response);
			intent.putExtra("CLUB_NUMBER", clubNumber);
			intent.putExtra("MEMBER_ID", memberId);
			EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
			parentActivity.startChildActivity("PickClassActivity", intent);		
		}
	}	
}