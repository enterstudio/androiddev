package com.abcfinancial.android.myiclubonline.user.billing;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.DatePickerCustomDialog;
import com.abcfinancial.android.myiclubonline.common.DatePickerCustomDialog.OnDateSelectedListener;

public class HistoryDatePickerActivity extends FragmentActivity implements
		OnDateSelectedListener {
	public final static int START_DATE = 0;
	public final static int END_DATE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_checkin_datepicker);

		TextView searchButton = (TextView) findViewById(R.id.btnSearch);
		EditText startDate = (EditText) findViewById(R.id.checkInStartDate);
		startDate.setInputType(InputType.TYPE_NULL);
		EditText endDate = (EditText) findViewById(R.id.checkInEndDate);
		endDate.setInputType(InputType.TYPE_NULL);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});
		startDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDatePickerDialog(v, START_DATE);
			}
		});
		endDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDatePickerDialog(v, END_DATE);
			}
		});
	}

	public void showDatePickerDialog(View v, int whichDate) {
		DialogFragment dialogFragment = new DatePickerCustomDialog(whichDate);
		dialogFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void onDateSelected(String selectedDate, int whichDate) {
		switch (whichDate) {
		case START_DATE:
			EditText startDate = (EditText) findViewById(R.id.checkInStartDate);
			startDate.setText(selectedDate);
			break;
		case END_DATE:
			EditText endDate = (EditText) findViewById(R.id.checkInEndDate);
			endDate.setText(selectedDate);
			break;
		default:
			new Exception("Exception: Unknown date field selected");
		}
	}
}
