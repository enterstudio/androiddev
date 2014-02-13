package com.tihonchik.lenonhonor360.ui.user;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.custom.ResizableImageView;
import com.tihonchik.lenonhonor360.listeners.BlogDetailOnClickListener;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.util.AppUtils;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;

public class BlogDisplayFragment extends BaseFragment {

	private static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
	private ResizableImageView _newBlogImage;

	class loadContentTask extends AsyncTask<String, Void, Drawable> {
		TextView progressText;

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
					_newBlogImage.setBackgroundDrawable(result);
				} else {
					_newBlogImage.setBackground(result);
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

		List<BlogEntry> entries = BlogEntryUtils.getAllBlogEntries();
		if (entries == null || entries.size() == 0) {
			return null;
		}

		String image = "http://placehold.it/320x240";
		if (entries.get(0).getImages()!= null && !"".equals(entries.get(0).getImages().size() > 0)) {
			image = entries.get(0).getImages().get(0);
		}

		new loadContentTask().execute(image);

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.blog_display, container, false);

		/*
		 * Setup new blog entry with image
		 */
		_newBlogImage = (ResizableImageView) rootView.findViewById(R.id.new_blog_image);
		TextView newBlogTitle = (TextView) rootView
				.findViewById(R.id.new_blog_title);
		newBlogTitle.setText(entries.get(0).getTitle());
		TextView newBlogText = (TextView) rootView
				.findViewById(R.id.new_blog_text);
		newBlogText.setText(entries.get(0).getBlog().replaceAll("<br>", " ")
				.replaceAll(" +", " "));

		Button newBlog = (Button) rootView.findViewById(R.id.btn_new_blog);
		BlogDetailOnClickListener mBlogDetailListener = new BlogDetailOnClickListener(getActivity(), entries.get(0));
		newBlog.setOnClickListener(mBlogDetailListener);

		/*
		 * Setup up the table with older blog entries
		 */
		TableLayout tableLayout = (TableLayout) rootView
				.findViewById(R.id.older_posts);
		for (int i = 1; i < entries.size(); i++) {
			TableRow row = new TableRow(getActivity());
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			LinearLayout outerLayout = new LinearLayout(getActivity());
			outerLayout.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			outerLayout.setOrientation(LinearLayout.HORIZONTAL);
			ImageView rowImage = new ImageView(getActivity());
			rowImage.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			rowImage.setImageResource(R.drawable.icon_bullet);
			LinearLayout innerLayout = new LinearLayout(getActivity());
			innerLayout.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			innerLayout.setOrientation(LinearLayout.VERTICAL);
			TextView rowTitle = new TextView(getActivity());
			rowTitle.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			rowTitle.setTextColor(R.color.lh360Clouds);
			rowTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			rowTitle.setText(entries.get(i).getTitle());
			
			RelativeLayout relativeLayout = new RelativeLayout(getActivity());
			relativeLayout.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			TextView rowText = new TextView(getActivity());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			rowText.setLayoutParams(params);
			rowText.setTextColor(R.color.lh360Black);
			rowText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			rowText.setEllipsize(TruncateAt.END);
			rowText.setSingleLine(true);
			rowText.setText(entries.get(i).getBlog().replaceAll("<br>", " ")
					.replaceAll(" +", " "));

			ImageView readMoreImage = new ImageView(getActivity());
			params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			readMoreImage.setLayoutParams(params);
			readMoreImage.setImageResource(R.drawable.read_more);

			relativeLayout.addView(rowText);
			relativeLayout.addView(readMoreImage);
			innerLayout.addView(rowTitle);
			innerLayout.addView(relativeLayout);
			outerLayout.addView(rowImage);
			outerLayout.addView(innerLayout);
			row.addView(outerLayout);
			mBlogDetailListener = new BlogDetailOnClickListener(getActivity(), entries.get(i));
			row.setOnClickListener(mBlogDetailListener);
			tableLayout.addView(row);
		}
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