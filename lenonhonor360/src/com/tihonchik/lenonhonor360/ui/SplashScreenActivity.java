package com.tihonchik.lenonhonor360.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.R;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.models.LoadType;
import com.tihonchik.lenonhonor360.services.BlogPullService;
import com.tihonchik.lenonhonor360.ui.user.MainActivity;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;

public class SplashScreenActivity extends BaseActivity {
	private String loadType;
	private int numberNewEntries;
	private Pattern hrefPattern = Pattern.compile("(?i)<a_style([^>]+)>(.+?)</a>");
	private Pattern idPattern = Pattern.compile("(?i)id=(\\d{1,})");
	private Pattern datePattern = Pattern.compile("(?i)<div_style=(\\\"color:#66[^>]+)>(.+?)</div>");

	class HtmlParserTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... args) {
			try {
				String originalHtml = getHtml(AppDefines.LENONHONOR_APP_PHP_WEBSITE_URI);
				String noWiteSpaceHtml = originalHtml.replaceAll("[\\n\\t]", "").replaceAll(" +", " ").replaceAll(" ", "_");
				Matcher m = hrefPattern.matcher(noWiteSpaceHtml);
				List<String> hrefList = new ArrayList<String>();
				List<String> titleList = new ArrayList<String>();
				while (m.find()) {
					hrefList.add(m.group(0));
					titleList.add(m.group(2).replaceAll("_", " ").trim());
				}

				int lastWebBlogId = -1;
				if (list.size() > 0) {
					m = idPattern.matcher(list.get(0));
					if (m.find()) {
						lastWebBlogId = Integer.valueOf(m.group(1));
					}
				}

				List<BlogEntry> entries = new ArrayList<BlogEntry>();
				int lastDbBlogId = BlogEntryUtils.getNewestBlogId();
				if (lastDbBlogId == -1 && lastWebBlogId == -1) {
					showSingleErrorDialog("blogUnavailable");
					loadType = LoadType.LOAD_NOTHING.getName();
				} else if (lastDbBlogId != -1 && lastWebBlogId == -1) {
					// Something went wrong with parsing, load all DB entries
					loadType = LoadType.LOAD_DB.getName();
				} else if (lastDbBlogId == -1 && lastWebBlogId != -1) {
					// insert all entries, DB has nothing in it
					entries = parseAllEntries(lastWebBlogId);
					BlogEntryUtils.insertBlogEntries(entries);
					loadType = LoadType.LOAD_WEB.getName();
					numberNewEntries = entries.size();
				} else if (lastDbBlogId != lastWebBlogId) {
					// insert new web entries into DB
					while( lastWebBlogId >= lastDbBlogId ) {
						parseEntryWithId(lastWebBlogId);
					}
					loadType = LoadType.LOAD_NEW.getName();
					numberNewEntries = lastWebBlogId - lastDbBlogId;
				}
			} catch (MalformedURLException exception) {
				Log.d("LH360", " > MalformedURLException: " + exception);
			} catch (IOException exception) {
				Log.d("LH360", " > URISyntaxException: " + exception);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("laodType", loadType);
			i.putExtra("numberNewEntries", numberNewEntries);
			startActivity(i);
			finish();
		}

		private BlogEntry parseEntryWithId(int id) throws IOException {
			BlogEntry entry = new BlogEntry(id);
			String entryHtml = getHtml(AppDefines.LENONHONOR_APP_PHP_ENTRY_URI + id);
			
			return entry;
		}

		private List<BlogEntry> parseAllEntries(int lastId) throws IOException {
			List<BlogEntry> entries = new ArrayList<BlogEntry>();
			for (int i = 1; i <= lastId; i++) {
				entries.add(parseEntryWithId(i));
			}
			return entries;
		}
		
		private String getHtml(String uri) throws MalformedURLException, IOException {
			URL url = new URL(uri);
			URLConnection conn = url.openConnection();
			InputStreamReader streamReader = new InputStreamReader(
					conn.getInputStream());
			BufferedReader br = new BufferedReader(streamReader);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	@Override
	protected Context getContextforBase() {
		return this;
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.splash_screen;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(null);

		Intent intent = new Intent(this, BlogPullService.class);
		intent.putExtra("com.tihonchik.lenonhonor360.triggerTime", 3000l);
		intent.putExtra(BlogPullService.PARAM_IN_MSG, "");
		// startService(intent);

		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
				0);
		long trigger = System.currentTimeMillis() + (3000);
		final AlarmManager alarmManager = (AlarmManager) getBaseContext()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);

		new HtmlParserTask().execute();
	}
}