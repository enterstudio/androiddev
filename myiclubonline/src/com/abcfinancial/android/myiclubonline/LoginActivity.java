package com.abcfinancial.android.myiclubonline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.common.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	EditText username;
	EditText password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView loginButton = (TextView) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.userPassword);
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new WebServiceTask().execute("lookup", username.getText()
						.toString(), password.getText().toString());
			}
		});

		TextView forgotPasswordScreen = (TextView) findViewById(R.id.btnForgotPassword);
		forgotPasswordScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ForgotPasswordActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
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
			
			Intent intent = new Intent(LoginActivity.this, UserActivity.class);
			LoginActivity.this.startActivity(intent);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}	
	
	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			client.addParameter("username", uri[1]);
			client.addParameter("password", uri[2]);
			client.addHeader("Authorization", "Basic cWE6dGVzdA==");
			try {
				client.execute(RequestMethod.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
	    	Intent intent = new Intent(LoginActivity.this,UserActivity.class);
	    	LoginActivity.this.startActivity(intent);
	        LoginActivity.this.requestFinished(response);
		}
	}
}
