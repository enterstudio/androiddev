package com.abcfinancial.android.myiclubonline.user.agreement;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.abcfinancial.android.myiclubonline.R;

public class AgreementInfoActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_agreementinfo);

		try {
			Bundle extras = getIntent().getExtras();
			String agreement = extras.getString("AGREEMENT");
			JSONObject agreementInfo = new JSONObject(agreement);
			JSONObject agreementData = agreementInfo.getJSONObject("member");
			
			String agreementNumberValue = agreementData.getString("agreementNumber");
			String effectiveDateValue = agreementData.getString("effectiveDate");
			String downPaymentValue = agreementData.getString("downPayment");
			String payTermsValue = agreementData.getString("payTerms");
			String renewalTypeValue = agreementData.getString("renewalType");
			String contractDateValue = agreementData.getString("contractDate");
			String abcStatusValue = agreementData.getString("abcStatus");
			String membershipTypeValue = agreementData.getString("membershipType");

			TextView agreementNumber = (TextView) findViewById(R.id.agreementNumber);
			TextView effectiveDate = (TextView) findViewById(R.id.agreementBegan);
			TextView downPayment = (TextView) findViewById(R.id.downPayment);
			TextView payTerms = (TextView) findViewById(R.id.agreementType);
			TextView renewalType = (TextView) findViewById(R.id.autoRenew);
			TextView contractDate = (TextView) findViewById(R.id.memberSince);
			TextView abcStatus = (TextView) findViewById(R.id.membershipStatus);
			TextView membershipType = (TextView) findViewById(R.id.membershipType);

			agreementNumber.setText(agreementNumberValue);
			effectiveDate.setText(effectiveDateValue);
			downPayment.setText(downPaymentValue);
			payTerms.setText(payTermsValue);
			renewalType.setText(renewalTypeValue);
			contractDate.setText(contractDateValue);
			abcStatus.setText(abcStatusValue);
			membershipType.setText(membershipTypeValue);
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}
}
