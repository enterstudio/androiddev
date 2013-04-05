package com.abcfinancial.android.myiclubonline.user.billing;

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
import com.abcfinancial.android.myiclubonline.common.BillingInfo;
import com.abcfinancial.android.myiclubonline.common.Enums;
import com.abcfinancial.android.myiclubonline.common.Enums.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.Enums.SearchTypes;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

public class BillingActivity extends ListActivity {
	static final String[] ACTIONS = new String[] { "Payment History",
			"Purchase History", "Make Payment", "Update Payment" };
	private String member, club, paymentAction;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_simple, ACTIONS));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String mobileLoginMember = preferences.getString("mobileLoginMember", "");
			JSONObject loginMember = new JSONObject(mobileLoginMember);
			club = loginMember.getString("homeClub");
			member = loginMember.getString("memberNumber");

			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (position == 0) {
						Intent intent = new Intent(getParent(), HistoryDatePickerActivity.class);
						intent.putExtra("SEARCH_TYPE", SearchTypes.PAYMENT.getId());
						AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
						parentActivity.startChildActivity("PaymentHistory", intent);
					}
					if (position == 1) {
						Intent intent = new Intent(getParent(), HistoryDatePickerActivity.class);
						intent.putExtra("SEARCH_TYPE", SearchTypes.PURCHASE.getId());
						AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
						parentActivity.startChildActivity("PurchaseHistory", intent);
					}
					if (position == 2 ) {
						paymentAction = Enums.PaymentActions.MAKE_PAYMENT.name();
						new WebServiceTask().execute("billinginfo", member, club);
					}
					if (position == 3 ) {
						paymentAction = Enums.PaymentActions.UPDATE_PAYMENT.name();
						new WebServiceTask().execute("billinginfo", member, club);
					}					
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}

	private class WebServiceTask extends AsyncTask<String, String, String> {
//		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
//			progressDialog = ProgressDialog.show(BillingActivity.this, "", "Loading billing info...");
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
/*			if (progressDialog != null) {
				progressDialog.dismiss();
			}
*/			String intentName = "";
			Intent intent = null;
			switch (Enums.PaymentActions.valueOf(paymentAction)) {
				case MAKE_PAYMENT:
					intent = new Intent(getParent(), PaymentInfoActivity.class);
					intentName = "MakePaymentInfoActivity";
					break;
				case UPDATE_PAYMENT:
					intent = new Intent(getParent(), PaymentInfoActivity.class);
					intentName = "UpdatePaymentInfoActivity";
					break;
				default:
					// TODO: throw error dialog
					break;
			}
			intent.putExtra("BILLING_INFO", processBillingInfo(response));
			intent.putExtra("PAYMENT_ACTION", paymentAction);
			AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
			parentActivity.startChildActivity(intentName, intent);			
		}
		
		private String processBillingInfo(String response) {
			BillingInfo billingInfo = new BillingInfo();
			try {
				JSONObject json = new JSONObject(response);
				billingInfo.setFirstName(json.getString("firstName"));
				billingInfo.setLastName(json.getString("lastName"));
				billingInfo.setPaymentMethod(Enums.PaymentMethods.fromName(json.getString("paymentMethod")));
				billingInfo.setBankAccountNumber(json.getString("bankAccountNumber"));
				billingInfo.setBankRoutingNumber(json.getString("bankRoutingNumber"));
				billingInfo.setBankAccountType(Enums.BankAccountTypes.fromName(json.getString("bankAccountType")));
				billingInfo.setCreditCardNumber(json.getString("creditCardNumber"));
				billingInfo.setCreditCardType(Enums.CreditCardTypes.fromName(json.getString("creditCardType")));
				billingInfo.setCreditCardExpMonth(json.getString("creditCardExpMonth"));
				billingInfo.setCreditCardExpYear(json.getString("creditCardExpYear"));
				billingInfo.setNextDueDate(json.getString("nextDueDate"));
				billingInfo.setNextPaymentAmount(json.getString("nextPaymentAmount"));
				billingInfo.setLateFeeAmount(json.getString("lateFeeAmount"));
				billingInfo.setLatePaymentAmount(json.getString("latePaymentAmount"));
				billingInfo.setTotalPastDue(json.getString("totalPastDue"));
			} catch (JSONException exception) {
				// TODO: throw error dialog
			}
			return billingInfo.toString();
		}
	}
}
