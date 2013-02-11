package com.abcfinancial.android.myiclubonline.user;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.abcfinancial.android.myiclubonline.R;

public class BarcodeActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity_barcode);
		try {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String mobileLoginMember = preferences.getString("mobileLoginMember", "");
		JSONObject json = new JSONObject(mobileLoginMember);
		String barcode = json.getString("barcode");
		new DownloadImageTask((ImageView) findViewById(R.id.barcodeImage))
				.execute("http://chart.apis.google.com/chart?cht=qr&chs=400x400&chl=" + barcode);
		} catch (JSONException exception) {
			// TODO: throw error dialog
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView barcodeImageView;

		public DownloadImageTask(ImageView barcodeImageView) {
			this.barcodeImageView = barcodeImageView;
		}

		protected Bitmap doInBackground(String... urls) {
			String urlDisplay = urls[0];
			Bitmap barcodeImage = null;
			try {
				InputStream in = new java.net.URL(urlDisplay).openStream();
				barcodeImage = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				// TODO: throw error dialog 
			}
			return barcodeImage;
		}

		protected void onPostExecute(Bitmap result) {
			barcodeImageView.setImageBitmap(result);
		}
	}
}