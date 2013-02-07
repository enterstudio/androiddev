package com.abcfinancial.android.myiclubonline.user.appointments;

import android.app.Activity;
import android.os.Bundle;

public class PickAppointmentDateActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String selectedAppt = extras.getString("SELECTED_APPT");
		String selectedEmployeeId = extras.getString("SELECTED_EMPLOYEE_ID");

	}
}
