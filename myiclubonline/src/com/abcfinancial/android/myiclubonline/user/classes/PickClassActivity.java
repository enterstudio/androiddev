package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PickClassActivity extends ListActivity {

	public JSONArray mobileClubClasses;
	public String clubNumber;
	public String memberId;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String clubClasses = extras.getString("CLUB_CLASSES");
		clubNumber = extras.getString("CLUB_NUMBER");
		memberId = extras.getString("MEMBER_ID");
		List<String> classes = new ArrayList<String>();
		try {
			JSONObject data = new JSONObject(clubClasses);
			mobileClubClasses = data.getJSONArray("mobileClubClasses");
			JSONObject clubClass;
			for( int i=0; i<mobileClubClasses.length(); i++) {
				clubClass = mobileClubClasses.getJSONObject(i);
				classes.add(clubClass.getString("eventTypeName") + " (Level: " + clubClass.getString("levelName") + ")");
			}
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_simple, classes));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						new WebServiceTask().execute("classschedule", memberId, clubNumber, mobileClubClasses.get(position).toString());
					} catch (JSONException exception) {
						// TODO: throw error dialog
					}
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
			try {
				client.addParameter("memberId", uri[1]);
				client.addParameter("club", uri[2]);
				JSONObject classInfo = new JSONObject(uri[3]);
				client.addParameter("bookingWindowHours", classInfo.getString("bookingHourStart"));
				client.addParameter("eventTypeId", classInfo.getString("eventTypeId"));
				client.addHeader("Authorization", "Basic cWE6dGVzdA==");
				client.execute(RequestMethod.POST);
			} catch (JSONException exception) {
				exception.printStackTrace();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			Intent intent = new Intent(getParent(), PickClassTimeActivity.class);
			intent.putExtra("CLASS_SCHEDULE", response);
			EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
			parentActivity.startChildActivity("PickClassTimeActivity", intent);
		}
	}	
}