package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
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

public class PickClassActivity extends ListActivity {
	
	ArrayAdapter<String> listViewAdapter;
	List<String> classList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		classList = new ArrayList<String>();
		Bundle extras = getIntent().getExtras();
	    String clubNumber = extras.getString("CLUB_NUMBER");
	    System.out.println(clubNumber);
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String memberId = preferences.getString("memberId", "");
		    System.out.println(memberId);
			listViewAdapter = new ArrayAdapter<String>(this, R.layout.events_class_pickclass, classList); 
			setListAdapter(listViewAdapter);
			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getParent(), PickClassTimeActivity.class);
					intent.putExtra("CLASS_LIST_POSITION", position);
					EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
					parentActivity.startChildActivity(Integer.toString(position), intent);
				}
			});
			new WebServiceTask().execute("clubclasslist", memberId, clubNumber);
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
	
	public void requestFinished(String response) {
		try {
			JSONObject json = new JSONObject(response);
			System.out.println(json);
		} catch (JSONException e1) {
			e1.printStackTrace();
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
	    	Intent intent = new Intent(PickClassActivity.this,PickClassTimeActivity.class);
	    	PickClassActivity.this.startActivity(intent);
	    	PickClassActivity.this.requestFinished(response);
		}
	}	
}