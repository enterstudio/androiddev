package com.abcfinancial.android.myiclubonline.user.billing;

import java.util.Calendar;

import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.BillingInfo;
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
	private ProgressDialog progressDialog;
	private Spinner bankAccountTypes;
	private String loadMessage = "", club, memberNumber, methodName = "";
	
	public EditText bankFirstName;
	public EditText bankLastName;
	public EditText bankAccountNumber;
	public EditText bankAccountRouting;
	public EditText paymentAmount;	
	public EditText processingDate;
	
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
			String billing = extras.getString("BILLING_INFO");
			String paymentAction = extras.getString("PAYMENT_ACTION");
			boolean usePaymentOnFile = extras.getBoolean("USE_ON_FILE");
			BillingInfo billingInfo = new BillingInfo(billing);

			bankFirstName = (EditText) findViewById(R.id.bankFirstName);
			bankLastName = (EditText) findViewById(R.id.bankLastName);
			bankAccountNumber = (EditText) findViewById(R.id.bankAccountNumber);
			bankAccountRouting = (EditText) findViewById(R.id.bankRoutingNumber);
			bankAccountTypes = (Spinner) findViewById(R.id.bankAccountType);

			TextView paymentAmountLabel = (TextView) findViewById(R.id.paymentAmountLabel);
			paymentAmount = (EditText) findViewById(R.id.paymentAmount);
			String buttonText = "", processingDateText = "";
			
			switch (Enums.PaymentActions.valueOf(paymentAction)) {
				case MAKE_PAYMENT:
					buttonText = "Make Payment";
					processingDateText = "Draft Date";
					loadMessage = "Making payment...";
					methodName = "makepayment";
					if( usePaymentOnFile ) {
						bankFirstName.setText(billingInfo.getFirstName());
						bankLastName.setText(billingInfo.getLastName());
						bankAccountNumber.setText("xxxx-" + billingInfo.getBankAccountNumber());
						bankAccountRouting.setText(billingInfo.getBankRoutingNumber());
						bankAccountTypes.setSelection(billingInfo.getBankAccountType().getOrder());
					} 
					break;
				case UPDATE_PAYMENT:
					buttonText = "Update Payment Method";
					processingDateText = "Effective Date";
					loadMessage = "Updating payment method...";
					methodName = "updatepaymentmethod";
					paymentAmount.setVisibility(View.GONE);
					paymentAmountLabel.setVisibility(View.GONE);
					
					bankFirstName.setText(billingInfo.getFirstName());
					bankLastName.setText(billingInfo.getLastName());
					bankAccountNumber.setText("xxxx-" + billingInfo.getBankAccountNumber());
					bankAccountRouting.setText(billingInfo.getBankRoutingNumber());
					bankAccountTypes.setSelection(billingInfo.getBankAccountType().getOrder());
					break;
				default:
					// TODO: throw error dialog
					break;
			}
			TextView processingDateLabel = (TextView) findViewById(R.id.processDateLabel);
			processingDateLabel.setText(processingDateText);
			processingDate = (EditText) findViewById(R.id.processDate);
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH);			
			processingDate.setText(month+ "/" + day + "/" + year);
			Button paymentActionButton = (Button) findViewById(R.id.paymentActionButton);
			paymentActionButton.setText(buttonText);
			paymentActionButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					new WebServiceTask(loadMessage).execute(methodName, club, memberNumber, bankFirstName.getText().toString(),
							bankLastName.getText().toString(), bankAccountNumber.getText().toString(), String.valueOf(bankAccountTypes.getSelectedItemPosition()),
							bankAccountRouting.getText().toString(), processingDate.getText().toString());
				}
			});			
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}
	
	public void requestFinished(String response) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
//		super.onBackPressed();
	}
	
	private class WebServiceTask extends AsyncTask<String, String, String> {
		private String loadMessage;
		public WebServiceTask(String message) {
			super();
			this.loadMessage = message;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(BankAccountInfoActivity.this, "", loadMessage);
			super.onPreExecute();
		}		
		
		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			client.addParameter("club", uri[1]);
			client.addParameter("memberNumber", uri[2]);
			client.addParameter("firstName", uri[3]);
			client.addParameter("lastName", uri[4]);
			client.addParameter("bankAccountNumber", uri[5]);
			client.addParameter("bankAccountType", uri[6]);
			client.addParameter("bankRoutingNumber", uri[7]);
			client.addParameter("creditCardNumber", "");
			client.addParameter("creditCardType", "");
			client.addParameter("creditCardExpMonth", "");
			client.addParameter("creditCardExpYear", "");
			client.addParameter("paymentMethod", "EFT");
			client.addParameter("effectiveDate", uri[8]);			
			try {
				client.execute(RequestMethod.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			BankAccountInfoActivity.this.requestFinished(response);
		}
	}
}
