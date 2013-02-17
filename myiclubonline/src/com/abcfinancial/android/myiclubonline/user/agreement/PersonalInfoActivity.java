package com.abcfinancial.android.myiclubonline.user.agreement;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.abcfinancial.android.myiclubonline.R;

public class PersonalInfoActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_personalinfo);

		try {
			Bundle extras = getIntent().getExtras();
			String personal = extras.getString("PERSONAL");
			JSONObject personalInfo = new JSONObject(personal);
			JSONObject personalData = personalInfo.getJSONObject("personal");
			
			String fullName = personalData.getString("firstName") + personalData.getString("lastName");
			String addressValue = "", address = personalData.getString("address");
			String cityValue = "", city = personalData.getString("city");
			String stateValue = "", state = personalData.getString("state");
			String zipValue = "", zip = personalData.getString("zip");
			if( address != null && !"".equals(address)) {
				addressValue = address;
			}
			if( city != null && !"".equals(city)) {
				cityValue = city;
			}
			if( state != null && !"".equals(state)) {
				stateValue = ", " + state;
			}
			if( zip != null && !"".equals(zip)) {
				zipValue = " " + zip;
			}
			String cityStateZip = cityValue + stateValue + zipValue;

			String homePhoneValue = "", mobilePhoneValue = "", homePhone = personalData.getString("homePhone"), mobilePhone = personalData.getString("cellPhone");
			if( homePhone!= null && !"".equals(homePhone) && !"0000000000".equals(homePhone) ) {
				homePhoneValue = homePhone;
			}
			if( mobilePhone!= null && !"".equals(homePhone) && !"0000000000".equals(mobilePhone) ) {
				mobilePhoneValue = mobilePhone;
			}
			String emailValue = "", email = personalData.getString("email");
			if( email != null && !"".equals(email)) {
				emailValue = email;
			}

			TextView fullNameLine = (TextView) findViewById(R.id.fullName);
			TextView addressLine = (TextView) findViewById(R.id.address);
			TextView cityLine = (TextView) findViewById(R.id.cityStateZip);
			TextView homePhoneLine = (TextView) findViewById(R.id.homePhone);
			TextView mobilePhoneLine = (TextView) findViewById(R.id.mobilePhone);
			TextView emailLine = (TextView) findViewById(R.id.email);

			fullNameLine.setText(fullName);
			addressLine.setText(addressValue);
			cityLine.setText(cityStateZip);
			homePhoneLine.setText(homePhoneValue);
			mobilePhoneLine.setText(mobilePhoneValue);
			emailLine.setText(emailValue);
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}
}