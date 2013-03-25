package com.abcfinancial.android.myiclubonline.user.billing;

import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.Enums;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PaymentInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_info);
		try {
			Bundle extras = getIntent().getExtras();
			String billingInfo = extras.getString("BILLING_INFO");
			String paymentAction = extras.getString("PAYMENT_ACTION");
			JSONObject json = new JSONObject(billingInfo);
			TextView updatePaymentInfoLabel = (TextView) findViewById(R.id.updatePaymentInfoLabel);
			TextView pastDue = (TextView) findViewById(R.id.pastDue);
			TextView nextPayment = (TextView) findViewById(R.id.nextPayment);
			TextView nextPaymentDue = (TextView) findViewById(R.id.nextPaymentDue);
			String paymentMethod = json.getString("paymentMethod");
			Button onFileButton = (Button) findViewById(R.id.pickOnFile);

			switch (Enums.PaymentActions.valueOf(paymentAction)) {
				case MAKE_PAYMENT:
					TextView storedPaymentMethodLabel = (TextView) findViewById(R.id.storedPaymentLabel);
					storedPaymentMethodLabel.setVisibility(View.GONE);
					updatePaymentInfoLabel.setVisibility(View.GONE);
					String totalPastDue = json.getString("totalPastDue");
					pastDue.setText(totalPastDue);
					nextPayment.setText(json.getString("nextPaymentAmount"));
					String nextDueDateText = json.getString("nextDueDate");
					if (nextDueDateText == null || nextDueDateText.length() == 0) {
						nextDueDateText = "None";
					}
					nextPaymentDue.setText(nextDueDateText);
					if( "0.00".equals(totalPastDue) ) {
						String buttonText = "";
						if( "CreditCard".equals(paymentMethod) ) {
							buttonText = "Card ending in " + json.getString("creditCardNumber");
						} else {
							buttonText = "Bank account ending in " + json.getString("bankAccountNumber");
						}
						onFileButton.setText(buttonText);
						onFileButton.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
	
							}
						});
					} else {
						onFileButton.setVisibility(View.GONE);
					}
					break;
				case UPDATE_PAYMENT:
					TextView pastDueLabel = (TextView) findViewById(R.id.pastDueLabel);
					TextView nextPaymentLabel = (TextView) findViewById(R.id.nextPaymentLabel);
					TextView nextPaymentDueLabel = (TextView) findViewById(R.id.nextPaymentDueLabel);
					TextView selectPaymentMethodLabel = (TextView) findViewById(R.id.selectPaymentMethodoLabel);
					pastDue.setVisibility(View.GONE);
					nextPayment.setVisibility(View.GONE);
					nextPaymentDue.setVisibility(View.GONE);
					pastDueLabel.setVisibility(View.GONE);
					nextPaymentLabel.setVisibility(View.GONE);
					nextPaymentDueLabel.setVisibility(View.GONE);
					selectPaymentMethodLabel.setVisibility(View.GONE);
					onFileButton.setVisibility(View.GONE);
					String labelText = "We do not have a payment method on file.";
					if( "CreditCard".equals(paymentMethod)) {
						labelText = "You have a " + json.getString("creditCardType") + " ending in " + json.getString("creditCardNumber") + 
								" with expiration date of " + json.getString("creditCardExpMonth") + "/"+ json.getString("creditCardExpYear") + " on file.";
					} else {
						labelText = "You have a " + json.getString("bankName") + " " + json.getString("bankAccountType") + " account edning in " +
								json.getString("bankAccountNumber") + " on file.";
					}
					updatePaymentInfoLabel.setText(labelText);
					break;
				default:
					break;
			}

			Button creditCardButton = (Button) findViewById(R.id.pickCreditCard);
			Button bankAccountButton = (Button) findViewById(R.id.pickBankAccount);
			creditCardButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

				}
			});
			bankAccountButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}		
	}
}
