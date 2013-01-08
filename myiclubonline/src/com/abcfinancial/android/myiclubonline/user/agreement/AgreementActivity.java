package com.abcfinancial.android.myiclubonline.user.agreement;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.user.checkin.CheckInHistoryDatePickerActivity;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

public class AgreementActivity extends ListActivity {
	static final String[] ACTIONS = new String[] { "Agreement Info", "Personal Info",
			"Club Info" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.account_agreement, ACTIONS));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Intent intent = new Intent(getApplicationContext(),
							CheckInHistoryDatePickerActivity.class);
					startActivity(intent);
				}
				if (position == 1) {
					Intent intent = new Intent(getApplicationContext(),
							CheckInHistoryDatePickerActivity.class);
					startActivity(intent);
				}
				if (position == 2) {
					Intent intent = new Intent(getParent(), ClubInfoActivity.class);
					AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
					parentActivity.startChildActivity(ACTIONS[2], intent);
				}
			}
		});
	}
}