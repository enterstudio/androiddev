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

public class CreditCardInfoActivity extends Activity {
	private ProgressDialog progressDialog;
	private Spinner creditCardTypes;
	private String loadMessage = "", club, memberNumber, methodName = "";
	
	public EditText creditFirstName;
	public EditText creditLastName;
	public EditText creditCardNumber;
	public EditText creditCardExpiration;
	public EditText paymentAmount;	
	public EditText processingDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_credit_account_info);
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

			creditFirstName = (EditText) findViewById(R.id.creditFirstName);
			creditLastName = (EditText) findViewById(R.id.creditLastName);
			creditCardNumber = (EditText) findViewById(R.id.creditCardNumber);
			creditCardExpiration = (EditText) findViewById(R.id.expirationDate);
			creditCardTypes = (Spinner) findViewById(R.id.creditCardType);

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
						creditFirstName.setText(billingInfo.getFirstName());
						creditLastName.setText(billingInfo.getLastName());
						creditCardNumber.setText("xxxx-" + billingInfo.getCreditCardNumber());
						creditCardExpiration.setText(billingInfo.getCreditCardExpirationForDisplay());
						creditCardTypes.setSelection(billingInfo.getCreditCardType().getOrder());
					} 
					break;
				case UPDATE_PAYMENT:
					buttonText = "Update Payment Method";
					processingDateText = "Effective Date";
					loadMessage = "Updating payment method...";
					methodName = "updatepaymentmethod";
					paymentAmount.setVisibility(View.GONE);
					paymentAmountLabel.setVisibility(View.GONE);
					
					creditFirstName.setText(billingInfo.getFirstName());
					creditLastName.setText(billingInfo.getLastName());
					creditCardNumber.setText("xxxx-" + billingInfo.getBankAccountNumber());
					creditCardExpiration.setText(billingInfo.getCreditCardExpirationForDisplay());
					creditCardTypes.setSelection(billingInfo.getCreditCardType().getOrder());
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
					new WebServiceTask(loadMessage).execute(methodName, club, memberNumber, creditFirstName.getText().toString(),
							creditLastName.getText().toString(), creditCardNumber.getText().toString(), String.valueOf(creditCardTypes.getSelectedItemPosition()),
							creditCardExpiration.getText().toString(), processingDate.getText().toString());
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
			progressDialog = ProgressDialog.show(CreditCardInfoActivity.this, "", loadMessage);
			super.onPreExecute();
		}		
		
		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			client.addParameter("club", uri[1]);
			client.addParameter("memberNumber", uri[2]);
			client.addParameter("firstName", uri[3]);
			client.addParameter("lastName", uri[4]);
			client.addParameter("bankAccountNumber", "");
			client.addParameter("bankAccountType", "");
			client.addParameter("bankRoutingNumber", uri[7]);
			client.addParameter("creditCardNumber", uri[5]);
			client.addParameter("creditCardType", uri[6]);
			
			// TODO: split credit card expiration date
			client.addParameter("creditCardExpMonth", "12");
			client.addParameter("creditCardExpYear", "18");
			client.addParameter("paymentMethod", "CreditCard");
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
			CreditCardInfoActivity.this.requestFinished(response);
		}
	}	
}
