package com.abcfinancial.android.myiclubonline.user.billing;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.user.agreement.ClubInfoActivity;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

public class BillingActivity extends ListActivity {
	static final String[] ACTIONS = new String[] { "Payment History",
			"Purchase History", "Make Payment", "Update Payment" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.account_billing, ACTIONS));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					Intent intent = new Intent(getParent(), HistoryDatePickerActivity.class);
					AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
					parentActivity.startChildActivity("PaymentHistory", intent);
				}
				if (position == 1) {
					Intent intent = new Intent(getParent(), HistoryDatePickerActivity.class);
					AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
					parentActivity.startChildActivity("PurchaseHistory", intent);
				}
				if (position == 2) {
					Intent intent = new Intent(getParent(), ClubInfoActivity.class);
					AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
					parentActivity.startChildActivity("MakePayment", intent);
				}
				if (position == 3) {
					Intent intent = new Intent(getParent(), ClubInfoActivity.class);
					AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
					parentActivity.startChildActivity("UpdatePayment", intent);
				}				
			}
		});
	}
}
