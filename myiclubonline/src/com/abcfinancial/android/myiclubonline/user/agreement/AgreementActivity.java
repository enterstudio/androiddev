package com.abcfinancial.android.myiclubonline.user.agreement;

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
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

public class AgreementActivity extends ListActivity {
	static final String[] ACTIONS = new String[] { "Agreement Info", "Personal Info", "Club Info" };
	private String memberId = "", memberNumber = "", club;
	private int selectedPosition;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_simple, ACTIONS));

		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String mobileLoginMember = preferences.getString("mobileLoginMember", "");
			memberId = preferences.getString("memberId", "");
			JSONObject loginMember = new JSONObject(mobileLoginMember);
			club = loginMember.getString("homeClub");
			memberNumber = club + loginMember.getString("memberNumber");
			
			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					selectedPosition = position;
					if (position == 0) {
						new WebServiceTask().execute("membershiplookup", memberId, memberNumber, club);
					}
					if (position == 1) {
						new WebServiceTask().execute("personallookup", memberId, memberNumber, club);
					}
					if (position == 2) {
						Intent intent = new Intent(getParent(), ClubInfoActivity.class);
						AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
						parentActivity.startChildActivity("ClubInfoActivity", intent);
					}
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}		
	}
	
	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			try {
				if("".equals(uri[1])){
					client.addParameter("memberNumber", uri[2]);
				} else {
					client.addParameter("memberId", uri[1]);
				}
				client.addParameter("club", uri[3]);
				client.execute(RequestMethod.POST);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			Intent intent = null;
			if( selectedPosition == 0 ) {
				intent = new Intent(getParent(), AgreementInfoActivity.class);
				intent.putExtra("AGREEMENT", response);
			} else if (selectedPosition == 1) {
				intent = new Intent(getParent(), PersonalInfoActivity.class);
				intent.putExtra("PERSONAL", response);
			}
			AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
			parentActivity.startChildActivity("PersonalInfoActivity", intent);
		}
	}	
}