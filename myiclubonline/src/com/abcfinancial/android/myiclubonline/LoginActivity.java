package com.abcfinancial.android.myiclubonline;

import com.abcfinancial.android.myiclubonline.common.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
		
		TextView mainScreen = (TextView) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.userPassword);
		mainScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new WebServiceTask().execute("lookup", username.getText().toString(), password.getText().toString());
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
	}
	
	private class WebServiceTask extends AsyncTask<String, String, String>{
		
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
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        LoginActivity.this.requestFinished(result);
	    }	
	}	
}
