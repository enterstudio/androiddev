package com.abcfinancial.android.myiclubonline.user.appointments;

import java.util.Calendar;

import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.Enums.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.Utils;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class PickAppointmentDateActivity extends Activity {
	private static final int DATE_DIALOG_ID = 0;
	private String selectedEmployeeId;
	private String selectedAppt;
	public String selectedLowDate;
	public String selectedHighDate;
	private TextView dateDisplay;
	private int year;
	private int month;
	private int day;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		selectedAppt = extras.getString("SELECTED_APPT");
		selectedEmployeeId = extras.getString("SELECTED_EMPLOYEE_ID");
		setCurrentDate();
		setContentView(R.layout.pick_single_date);
		dateDisplay = (TextView) findViewById(R.id.selectedDate);
		Button selectDate = (Button) findViewById(R.id.pickDate);
		selectDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		Button searchButton = (Button) findViewById(R.id.searchByDate);
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new WebServiceTask().execute("employeeschedule", selectedLowDate, selectedHighDate, selectedEmployeeId, selectedAppt);
			}
		});
	}

	private void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(getParent(), datePickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			selectedLowDate = (month + 1) + "/" + day + "/" + year;
			selectedHighDate = Utils.addDaysToDate(selectedLowDate.replace("/", "-"), 7).replace("-", "/");
			dateDisplay.setText(selectedLowDate + " to " + selectedHighDate);
		}
	};
	
	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			try {
				JSONObject appointment = new JSONObject(uri[4]);
				client.addParameter("club", appointment.getString("clubNumber"));
				client.addParameter("lowDate", uri[1]);
				client.addParameter("highDate", uri[2]);
				client.addParameter("employeeId", uri[3]);
				client.addParameter("duration", appointment.getString("duration"));
				client.addParameter("bookingHourStart", appointment.getString("bookingHourStart"));
				client.execute(RequestMethod.POST);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			System.out.println("RESPONSE: " + response);
//			Intent intent = new Intent(getParent(), PickClassTimeActivity.class);
//			intent.putExtra("EMPLOYEE_SCHEDULE", response);
//			EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
//			parentActivity.startChildActivity("PickClassTimeActivity", intent);
		}
	}
}