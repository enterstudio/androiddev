package com.tihonchik.lenonhonor360.ui.user;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.notifications.NotifyService;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.util.AppUtils;

public class BlogDisplayFragment extends BaseFragment {

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
			getActivity().startService(new Intent(getActivity(), NotifyService.class));
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
				R.layout.activity_blog_display, container, false);

		_newBlogImage = (ImageView) rootView.findViewById(R.id.new_blog_image);
		// tf = FontUtils.getEffraMedium();
		Button newBlog = (Button) rootView.findViewById(R.id.btn_new_blog);
		// b.setTypeface(tf);
		newBlog.setOnClickListener(mBlogDetailListener);

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
