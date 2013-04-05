package com.abcfinancial.android.myiclubonline.user.billing;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.BillingInfo;
import com.abcfinancial.android.myiclubonline.common.Enums;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PaymentInfoActivity extends Activity {
	private String billing = null, paymentAction = null;
	private BillingInfo billingInfo = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_info);
		Bundle extras = getIntent().getExtras();
		billing = extras.getString("BILLING_INFO");
		paymentAction = extras.getString("PAYMENT_ACTION");
		billingInfo = new BillingInfo(billing);
		TextView updatePaymentInfoLabel = (TextView) findViewById(R.id.updatePaymentInfoLabel);
		TextView pastDue = (TextView) findViewById(R.id.pastDue);
		TextView nextPayment = (TextView) findViewById(R.id.nextPayment);
		TextView nextPaymentDue = (TextView) findViewById(R.id.nextPaymentDue);
		Button onFileButton = (Button) findViewById(R.id.pickOnFile);

		switch (Enums.PaymentActions.valueOf(paymentAction)) {
			case MAKE_PAYMENT:
				TextView storedPaymentMethodLabel = (TextView) findViewById(R.id.storedPaymentLabel);
				storedPaymentMethodLabel.setVisibility(View.GONE);
				updatePaymentInfoLabel.setVisibility(View.GONE);
				pastDue.setText(billingInfo.getTotalPastDue());
				nextPayment.setText(billingInfo.getNextPaymentAmount());
				String nextDueDateText = billingInfo.getNextDueDate();
				if (nextDueDateText == null || nextDueDateText.length() == 0) {
					nextDueDateText = "None";
				}
				nextPaymentDue.setText(nextDueDateText);
				if( "0.00".equals(billingInfo.getTotalPastDue()) ) {
					String buttonText = "";
					if( Enums.PaymentMethods.CC.equals(billingInfo.getPaymentMethod()) ) {
						buttonText = "Card ending in " + billingInfo.getCreditCardNumber();
					} else {
						buttonText = "Bank account ending in " + billingInfo.getBankAccountNumber();
					}
					onFileButton.setText(buttonText);
					onFileButton.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent(getParent(), BankAccountInfoActivity.class);
							intent.putExtra("BILLING_INFO", billingInfo.toString());
							intent.putExtra("PAYMENT_ACTION", paymentAction);
							intent.putExtra("USE_ON_FILE", true);
							AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
							parentActivity.startChildActivity("BankAccountInfo", intent);	
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
				if( Enums.PaymentMethods.CC.equals(billingInfo.getPaymentMethod()) ) {
					labelText = "You have a " + billingInfo.getCreditCardType() + " ending in " + billingInfo.getCreditCardNumber() + 
							" with expiration date of " + billingInfo.getCreditCardExpMonth() + "/"+ billingInfo.getCreditCardExpYear() + " on file.";
				} else {
					labelText = "You have a " + billingInfo.getBankName() + " " + billingInfo.getBankAccountType() + " account edning in " +
							billingInfo.getBankAccountNumber() + " on file.";
				}
				updatePaymentInfoLabel.setText(labelText);
				break;
			default:
				// TODO: throw error dialog
				break;
		}

		Button creditCardButton = (Button) findViewById(R.id.pickCreditCard);
		Button bankAccountButton = (Button) findViewById(R.id.pickBankAccount);
		creditCardButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getParent(), CreditCardInfoActivity.class);
				intent.putExtra("BILLING_INFO", billingInfo.toString());
				intent.putExtra("PAYMENT_ACTION", paymentAction);
				intent.putExtra("USE_ON_FILE", false);
				AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
				parentActivity.startChildActivity("CreditCardInfo", intent);
			}
		});
		bankAccountButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getParent(), BankAccountInfoActivity.class);
				intent.putExtra("BILLING_INFO", billingInfo.toString());
				intent.putExtra("PAYMENT_ACTION", paymentAction);
				intent.putExtra("USE_ON_FILE", false);
				AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
				parentActivity.startChildActivity("BankAccountInfo", intent);
			}
		});
	}
}
