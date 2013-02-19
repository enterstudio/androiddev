package com.abcfinancial.android.myiclubonline.user.billing;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.common.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.Utils;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.usergroups.AccountGroupActivity;

public class HistoryDatePickerActivity extends Activity {
	private static final int DATE_DIALOG_START = 0;
	private static final int DATE_DIALOG_END = 1;
	private int startYear, endYear, startMonth, endMonth, startDay, endDay, searchType;
	private EditText startDate, endDate;
	private String member, club, memberId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_history_datepicker);
		try {
			Bundle extras = getIntent().getExtras();
			searchType = extras.getInt("SEARCH_TYPE");
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String mobileLoginMember = preferences.getString("mobileLoginMember", "");
			JSONObject loginMember = new JSONObject(mobileLoginMember);
			club = loginMember.getString("homeClub");
			memberId = loginMember.getString("memberId");
			member = club + loginMember.getString("memberNumber");
			TextView searchButton = (TextView) findViewById(R.id.btnSearch);
			startDate = (EditText) findViewById(R.id.checkInStartDate);
			startDate.setInputType(InputType.TYPE_NULL);
			endDate = (EditText) findViewById(R.id.checkInEndDate);
			endDate.setInputType(InputType.TYPE_NULL);
			setCurrentDates();
			startDate.setText(startMonth + "/" + startDay + "/" + startYear);
			endDate.setText(endMonth + "/" + endDay + "/" + endYear);
			searchButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String webServiceMethod;
					switch (searchType) {
					case 0:
						webServiceMethod = "paymenthistory";
						break;
					case 1:
						webServiceMethod = "purchasehistory";
						member = memberId;
						break;
					case 2:
						webServiceMethod = "checkinhistorylookup";
						member = memberId;
						break;
					default:
						webServiceMethod = "";
						break;
					}
					new WebServiceTask().execute(webServiceMethod, member, club, startDate.getText().toString(), endDate
									.getText().toString());
				}
			});
			startDate.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					showDialog(DATE_DIALOG_START);
					return false;
				}
			});
			endDate.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					showDialog(DATE_DIALOG_END);
					return false;
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			// TODO: throw error dialog
		}
	}

	private void setCurrentDates() {
		final Calendar c = Calendar.getInstance();
		endYear = c.get(Calendar.YEAR);
		endMonth = c.get(Calendar.MONTH) + 1;
		endDay = c.get(Calendar.DAY_OF_MONTH);
		String endDate = endMonth + "-" + endDay + "-" + endYear;
		String date = Utils.addMonthsToDate(endDate, -2);
		startYear = Integer.valueOf(date.substring(6));
		startMonth = Integer.valueOf(date.substring(0, 2));
		startDay = Integer.valueOf(date.substring(3, 5));
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_START:
			return new DatePickerDialog(getParent(), datePickerListenerStart, startYear, startMonth, startDay);
		case DATE_DIALOG_END:
			return new DatePickerDialog(getParent(), datePickerListenerEnd, endYear, endMonth, endDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListenerStart = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			startDate.setText((selectedMonth + 1) + "-" + selectedDay + "-" + selectedYear);
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListenerEnd = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			endDate.setText((selectedMonth + 1) + "-" + selectedDay + "-" + selectedYear);
		}
	};

	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			try {
				String fieldName;
				switch (searchType) {
				case 0:
					fieldName = "memberNumber";
					break;
				case 1:
				case 2:
					fieldName = "memberId";
					break;
				default:
					fieldName = "";
					break;
				}
				client.addParameter(fieldName, uri[1]);
				client.addParameter("club", uri[2]);
				client.addParameter("lowDate", uri[3]);
				client.addParameter("highDate", uri[4]);
				client.execute(RequestMethod.POST);
			} catch (Exception exception) {
				// TODO: throw error dialog
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			Intent intent = new Intent(getParent(), HistoryDisplayActivity.class);
			intent.putExtra("SEARCH_TYPE", searchType);
			intent.putExtra("SEARCH_RESULT", response);
			AccountGroupActivity parentActivity = (AccountGroupActivity) getParent();
			parentActivity.startChildActivity("HistoryDisplayActivity", intent);
		}
	}
}
