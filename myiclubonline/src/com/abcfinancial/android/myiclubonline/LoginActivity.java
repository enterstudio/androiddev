package com.abcfinancial.android.myiclubonline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.common.Enums.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private ProgressDialog progressDialog;
	private EditText username;
	private EditText password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView loginButton = (TextView) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.userPassword);
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new WebServiceTask().execute("lookup", username.getText().toString(), password.getText().toString());
			}
		});

		TextView forgotPasswordScreen = (TextView) findViewById(R.id.btnForgotPassword);
		forgotPasswordScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
				startActivity(intent);
			}
		});
	}

	public void requestFinished(String response) {
		try {
			JSONObject json = new JSONObject(response);
			JSONObject mobileLoginMember = json.getJSONObject("mobileLoginMember");
			JSONObject mobileHomeClub = json.getJSONObject("mobileHomeClub");
			JSONArray mobileLoginClubs = json.getJSONArray("mobileLoginClubs");
			JSONArray mobileLoginBookableAppointments = json.getJSONArray("mobileLoginBookableAppointments");

			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("mobileLoginMember", mobileLoginMember.toString());
			editor.putString("mobileHomeClub", mobileHomeClub.toString());
			editor.putString("mobileLoginClubs", mobileLoginClubs.toString());
			editor.putString("mobileLoginBookableAppointments", mobileLoginBookableAppointments.toString());
			editor.putString("memberId", mobileLoginMember.getString("memberId"));
			editor.commit();
		} catch (JSONException e1) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setMessage("Do you want to exit?").setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
			adb.create();			
			e1.printStackTrace();
			return;
		}
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		Intent intent = new Intent(LoginActivity.this, UserActivity.class);
		LoginActivity.this.startActivity(intent);
	}

	private class WebServiceTask extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...");
			super.onPreExecute();
		}		
		
		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			client.addParameter("username", uri[1]);
			client.addParameter("password", uri[2]);
			try {
				client.execute(RequestMethod.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			LoginActivity.this.requestFinished(response);
		}
	}
}