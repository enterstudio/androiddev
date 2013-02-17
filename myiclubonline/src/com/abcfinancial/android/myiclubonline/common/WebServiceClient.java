package com.abcfinancial.android.myiclubonline.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.abcfinancial.android.myiclubonline.http.CustomHttpClient;

public class WebServiceClient {
	private static final String URL = "https://wstest.abcfinancial.com/mobile/orchestrate/";
	public static final String USERNAME = "qa";
	public static final String PASSWORD = "test";
	
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;

	private String fullUrl;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public WebServiceClient(String operation) {
		this.fullUrl = URL + operation;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
		headers.add(new BasicNameValuePair("Authorization", "Basic cWE6dGVzdA=="));
	}

	public void addParameter(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void execute(RequestMethod method) throws Exception {
		switch (method) {
			case GET: {
				String combinedParams = "";
				if (!params.isEmpty()) {
					combinedParams += "?";
					for (NameValuePair p : params) {
						String paramString = p.getName() + "="
								+ URLEncoder.encode(p.getValue(), "UTF-8");
						if (combinedParams.length() > 1) {
							combinedParams += "&" + paramString;
						} else {
							combinedParams += paramString;
						}
					}
				}
	
				HttpGet request = new HttpGet(fullUrl + combinedParams);
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
				executeRequest(request, fullUrl);
				break;
			}
			case POST: {
				HttpPost request = new HttpPost(fullUrl);
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
	
				if (!params.isEmpty()) {
					request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				}
	
				executeRequest(request, fullUrl);
				break;
			}
		}
	}

	private void executeRequest(HttpUriRequest request, String url) {
		HttpClient client = new CustomHttpClient();
		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
