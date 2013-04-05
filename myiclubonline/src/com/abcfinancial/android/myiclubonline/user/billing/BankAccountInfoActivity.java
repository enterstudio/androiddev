package com.abcfinancial.android.myiclubonline.user.billing;

import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.Enums;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.common.Enums.RequestMethod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BankAccountInfoActivity extends Activity {
	private Spinner bankAccountTypes;
	private String loadMessage = "", club, memberNumber, firstName, lastName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_bank_account_info);
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String mobileLoginMember = preferences.getString("mobileLoginMember", "");
			JSONObject loginMember = new JSONObject(mobileLoginMember);
			club = loginMember.getString("homeClub");
			memberNumber = loginMember.getString("memberNumber");
			
			Bundle extras = getIntent().getExtras();
			String billingInfo = extras.getString("BILLING_INFO");
			String paymentAction = extras.getString("PAYMENT_ACTION");
			boolean usePaymentOnFile = extras.getBoolean("USE_ON_FILE");
			JSONObject json = new JSONObject(billingInfo);
			firstName = json.getString("firstName");
			lastName = json.getString("lastName");
			
			EditText bankFirstName = (EditText) findViewById(R.id.bankFirstName);
			EditText bankLastName = (EditText) findViewById(R.id.bankLastName);
			EditText bankAccountNumber = (EditText) findViewById(R.id.bankAccountNumber);
			EditText bankAccountRouting = (EditText) findViewById(R.id.bankRoutingNumber);
			bankAccountTypes = (Spinner) findViewById(R.id.bankAccountType);

			TextView paymentAmountLabel = (TextView) findViewById(R.id.paymentAmountLabel);
			EditText paymentAmount = (EditText) findViewById(R.id.paymentAmount);
			String buttonText = "", processingDateText = "";
			//int selectedBankAccountType = BankAccountTypes.valueOf(json.getString("bankAccountType")).equals(BankAccountTypes.CHECKING) ? 0 : 1;
			int selectedBankAccountType = 1;
			
			switch (Enums.PaymentActions.valueOf(paymentAction)) {
				case MAKE_PAYMENT:
					buttonText = "Make Payment";
					processingDateText = "Draft Date";
					loadMessage = "Making payment...";
					if( usePaymentOnFile ) {
						bankFirstName.setText(firstName);
						bankLastName.setText(lastName);
						bankAccountNumber.setText(json.getString("bankAccountNumber"));
						bankAccountRouting.setText(json.getString("bankRoutingNumber"));
						bankAccountTypes.setSelection(selectedBankAccountType);
					} 
					break;
				case UPDATE_PAYMENT:
					buttonText = "Update Payment Method";
					processingDateText = "Effective Date";
					loadMessage = "Updating payment method...";
					paymentAmount.setVisibility(View.GONE);
					paymentAmountLabel.setVisibility(View.GONE);
					bankFirstName.setText(firstName);
					bankLastName.setText(lastName);
					bankAccountNumber.setText(json.getString("bankAccountNumber"));
					bankAccountRouting.setText(json.getString("bankRoutingNumber"));
					bankAccountTypes.setSelection(selectedBankAccountType);
					break;
				default:
					// TODO: throw error dialog
					break;
			}
			TextView processingDateLabel = (TextView) findViewById(R.id.processDateLabel);
			processingDateLabel.setText(processingDateText);
			EditText processingDate = (EditText) findViewById(R.id.processDate);
			processingDate.setText("01/01/1900");
			Button paymentActionButton = (Button) findViewById(R.id.paymentActionButton);
			paymentActionButton.setText(buttonText);
			paymentActionButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					new WebServiceTask().execute("makepayment", club, memberNumber);
				}
			});			
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}
	
	private class WebServiceTask extends AsyncTask<String, String, String> {
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(BankAccountInfoActivity.this, "", loadMessage);
			super.onPreExecute();
		}		
		
		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			try {
				client.addParameter("memberNumber", uri[1]);
				client.addParameter("club", uri[2]);
				client.execute(RequestMethod.POST);
			} catch (Exception exception) {
				// TODO: throw error dialog
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}	
}
