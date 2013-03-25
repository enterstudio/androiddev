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

	private EditText username;
	private EditText password;
//	private ProgressDialog progressDialog;
//	private boolean stopBlocking;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView loginButton = (TextView) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.userPassword);
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				stopBlocking = false;
//				new LoadViewTask().execute();
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
			new AlertDialog.Builder(this).setTitle("Login")
					.setMessage("The username or password you entered is incorrect")
					.setNeutralButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			e1.printStackTrace();
			return;
		}
		Intent intent = new Intent(LoginActivity.this, UserActivity.class);
		LoginActivity.this.startActivity(intent);
	}

	private class WebServiceTask extends AsyncTask<String, String, String> {
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
//			stopBlocking = true;
			LoginActivity.this.requestFinished(response);
		}
	}

/*	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				while (!stopBlocking) {
					this.wait(850);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this,
					"Loading...", "Loading application View, please wait...",
					false, false);
		}

		protected void onProgressUpdate(Integer... values) {
			progressDialog.setProgress(values[0]);
		}

		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
		}
	}*/
}