package com.abcfinancial.android.myiclubonline.user.account.clubinfo;

import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ClubInfoActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_clubinfo);

		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String mobileHomeClub = preferences.getString("mobileHomeClub", "");
			JSONObject json = new JSONObject(mobileHomeClub);
			String clubNameValue = json.getString("name");
			String address1Value = json.getString("address1");
			String address2Value = json.getString("address2");
			String cityValue = json.getString("city");
			String stateValue = json.getString("state");
			String zipValue = json.getString("zip");
			String phoneValue = json.getString("phoneNumber");
			String phoneExtValue = json.getString("phoneNumberExt");
			TextView clubName = (TextView) findViewById(R.id.clubInfoName);
			TextView address1 = (TextView) findViewById(R.id.address1);
			TextView address2 = (TextView) findViewById(R.id.address2);
			TextView city = (TextView) findViewById(R.id.city);
			TextView state = (TextView) findViewById(R.id.state);
			TextView zip = (TextView) findViewById(R.id.zip);
			TextView clubPhone = (TextView) findViewById(R.id.clubPhone);
			TextView clubPhoneExt = (TextView) findViewById(R.id.clubPhoneExt);
			clubName.setText(clubNameValue);
			address1.setText(address1Value);
			address2.setText(address2Value);
			city.setText(cityValue);
			state.setText(stateValue);
			zip.setText(zipValue);
			clubPhone.setText(phoneValue);
			clubPhoneExt.setText(phoneExtValue);
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}
