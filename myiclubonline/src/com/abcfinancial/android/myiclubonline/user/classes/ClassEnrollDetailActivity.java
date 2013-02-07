package com.abcfinancial.android.myiclubonline.user.classes;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.abcfinancial.android.myiclubonline.R;

public class ClassEnrollDetailActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_class_enrolldetail);
		Bundle extras = getIntent().getExtras();
		String classDetail = extras.getString("CLASS_DETAIL");
		try {
			JSONObject json = new JSONObject(classDetail);
			TextView eventName = (TextView) findViewById(R.id.eventName);
			TextView eventDate = (TextView) findViewById(R.id.eventDate);
			TextView eventStartTime = (TextView) findViewById(R.id.eventStartTime);
			TextView eventEndTime = (TextView) findViewById(R.id.eventEndTime);
			TextView eventEmployeeName = (TextView) findViewById(R.id.eventEmployeeName);
			TextView eventLocation = (TextView) findViewById(R.id.eventLocation);
			TextView eventEnrollment = (TextView) findViewById(R.id.eventEnrollment);
			eventName.setText(json.getString("eventTypeName"));
			eventDate.setText(json.getString("eventDate"));
			eventStartTime.setText(json.getString("eventStartTime"));
			eventEndTime.setText(json.getString("eventEndTime"));
			JSONObject employee = json.getJSONObject("employee");
			eventEmployeeName.setText(employee.getString("employeeName"));
			eventLocation.setText(json.getString("locationName"));
			eventEnrollment.setText(json.getString("enrollment") + "/" + json.getString("maxEnrollment"));
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}