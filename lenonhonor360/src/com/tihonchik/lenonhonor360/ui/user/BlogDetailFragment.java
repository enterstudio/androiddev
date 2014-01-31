package com.tihonchik.lenonhonor360.ui.user;

import java.io.IOException;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.util.AppUtils;

public class BlogDetailFragment extends BaseFragment {

	private static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
	private ImageView _blogImage;

	class loadImageTask extends AsyncTask<String, Void, Drawable> {

		@Override
		protected Drawable doInBackground(String... args) {
			try {
				return AppUtils.getImageFromURL(args[0]);
			} catch (IOException exception) {
				return null;
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				if (SDK_VERSION < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					_blogImage.setBackgroundDrawable(result);
				} else {
					_blogImage.setBackground(result);
				}
			}
		}
	}

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

		Bundle args = getArguments();
		BlogEntry blogEntry = (BlogEntry) args
				.getSerializable(AppDefines.TAG_BLOG_DISPLAY_DETAIL);

//		new loadImageTask().execute(blogEntry.getImages().get(0));

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.blog_detail,
				container, false);

		_blogImage = (ImageView) rootView.findViewById(R.id.detail_blog_image);
		TextView blogTitle = (TextView) rootView
				.findViewById(R.id.detail_blog_title);
		blogTitle.setText(blogEntry.getTitle());
		TextView blogText = (TextView) rootView
				.findViewById(R.id.detail_blog_text);
		blogText.setText(Html.fromHtml(blogEntry.getBlog()));

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Activity a = getActivity();
		if (a == null) {
			return;
		}
	}
}