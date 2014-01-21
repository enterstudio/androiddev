package com.tihonchik.lenonhonor360.ui.user;

import java.io.IOException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.util.AppUtils;

public class BlogDisplayFragment extends BaseFragment {

	private static final int LH360_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification notification;
	private ImageView _newBlogImage;

	class loadContentTask extends AsyncTask<String, String, Drawable> {
		TextView progressText;

		@Override
		protected Drawable doInBackground(String... args) {
			try {
				return AppUtils.getImageFromURL("http://placehold.it/320x240");
			} catch (IOException exception) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				int sdk = android.os.Build.VERSION.SDK_INT;
				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					_newBlogImage.setBackgroundDrawable(result);
				} else {
					_newBlogImage.setBackground(result);
				}
			}
		}
	}

	OnClickListener mBlogDetailListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	OnClickListener mNotifcationListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// getActivity().startService(new Intent(getActivity(),
			// NotifyService.class));
			System.out.println("LH360 NOTIFICATION!!!");
			notificationManager.notify(LH360_NOTIFICATION_ID, notification);
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		new loadContentTask().execute();

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.blog_display, container, false);

		_newBlogImage = (ImageView) rootView.findViewById(R.id.new_blog_image);
		// tf = FontUtils.getEffraMedium();
		Button newBlog = (Button) rootView.findViewById(R.id.btn_new_blog);
		// b.setTypeface(tf);
		newBlog.setOnClickListener(mBlogDetailListener);

		Button newNotification = (Button) rootView
				.findViewById(R.id.btn_new_notification);
		newNotification.setOnClickListener(mNotifcationListener);

		TextView link = (TextView) rootView.findViewById(R.id.official_link);
		link.setText(Html
				.fromHtml("<a href='fakehttp://lenonhonor360.com/'>lenonhonor360.com</a>"));
		link.setMovementMethod(LinkMovementMethod.getInstance());

		PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),
				0, new Intent(getActivity(), MainActivity.class),
				Intent.FLAG_ACTIVITY_NEW_TASK);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getActivity());
		notificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.notificationicon)
				.setTicker("Notification!").setWhen(System.currentTimeMillis())
				.setAutoCancel(true).setContentTitle("Notification!")
				.setContentText("LenonHonor360 Notification!").build();

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO: write onResume method to load correct elements
		Activity a = getActivity();
		if (a == null) {
			return;
		}
	}
}
