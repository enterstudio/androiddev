package com.tihonchik.lenonhonor360.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tihonchik.lenonhonor360.AppConfig;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.ui.BaseFragment;
import com.tihonchik.lenonhonor360.util.AppUtils;

public class AboutFragment extends BaseFragment {

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

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.about_fragment, container, false);

		TextView textView = (TextView) rootView.findViewById(R.id.about_text);
		String aboutText = textView.getText().toString();
		aboutText = aboutText
				.replace("[link1]",
						AppUtils.getFormattedLink(AppConfig.OFFICIAL_WEBSITE_URL))
				.replace("[link2]",
						AppUtils.getFormattedLink(AppConfig.FILMS_WEBSITE_URL))
				.replace("[link3]",
						AppUtils.getFormattedLink(AppConfig.OFFICIAL_360_WEBSITE_URL));

		textView.setText(Html.fromHtml(aboutText));
		textView.setMovementMethod(LinkMovementMethod.getInstance());

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
