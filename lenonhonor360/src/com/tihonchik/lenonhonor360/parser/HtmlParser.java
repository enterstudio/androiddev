package com.tihonchik.lenonhonor360.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.tihonchik.lenonhonor360.AppDefines;
import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.util.BlogEntryUtils;

public class HtmlParser implements HtmlPatterns {
	
	public static String parseBlog() throws MalformedURLException, IOException {
		String originalHtml = getHtml(AppDefines.LENONHONOR_APP_PHP_WEBSITE_URI);
		String noWiteSpaceHtml = originalHtml.replaceAll("[\\n\\t]", "")
				.replaceAll(" +", " ").replaceAll(" ", "_");
		Matcher m = hrefPattern.matcher(noWiteSpaceHtml);
		List<String> hrefList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		while (m.find()) {
			hrefList.add(m.group(0));
			titleList.add(m.group(2).replaceAll("_", " ").trim());
		}

		int lastWebBlogId = -1;
		if (hrefList.size() > 0) {
			m = idPattern.matcher(hrefList.get(0));
			if (m.find()) {
				lastWebBlogId = Integer.valueOf(m.group(1));
			}
		}

		String responseValue = null;
		List<BlogEntry> entries = new ArrayList<BlogEntry>();
		int lastDbBlogId = BlogEntryUtils.getNewestBlogId();
		if (lastDbBlogId == -1 && lastWebBlogId == -1) {
			responseValue = AppDefines.BLOG_UNAVAILABLE;
		} else if (lastDbBlogId != -1 && lastWebBlogId == -1) {
			// Something went wrong with parsing, load all DB entries
			// Do nothing, as if there are no updates
		} else if (lastDbBlogId == -1 && lastWebBlogId != -1) {
			// insert all entries, DB has nothing in it
			entries = parseAllEntries(hrefList, titleList, noWiteSpaceHtml,
					hrefList.size());
			BlogEntryUtils.insertBlogEntries(entries);
			responseValue = AppDefines.ISSUE_NOTIFICAION;
		} else if (lastDbBlogId != lastWebBlogId) {
			// insert new web entries into DB
			entries = parseAllEntries(hrefList, titleList, noWiteSpaceHtml,
					lastWebBlogId - lastDbBlogId);
			BlogEntryUtils.insertBlogEntries(entries);
			responseValue = AppDefines.ISSUE_NOTIFICAION;
		}
		return responseValue;
	}

	private static String parseEntryWithId(int id) throws IOException {
		String blogText = "";
		String entryHtml = getHtml(AppDefines.LENONHONOR_APP_PHP_ENTRY_URI + id)
				.replaceAll("[\\n\\t]", "").replaceAll(" +", " ")
				.replaceAll(" ", "_");
		if (entryHtml.contains("<img_src")) {
			Matcher m = textWithImagePattern.matcher(entryHtml);
			if (m.find()) {
				String paragraphedText = m.group(2);
				Matcher mP = paragraphPattern.matcher(paragraphedText);
				int paragraphsFound = 0;
				while (mP.find()) {
					blogText += mP.group(1).replaceAll("<p>", "")
							.replaceAll("</p>", "").replaceAll("_", " ").trim();
					paragraphsFound++;
				}
				if (paragraphsFound == 0) {
					blogText = m.group(4).replaceAll("<div>", "")
							.replaceAll("</div>", "").replaceAll("_", " ")
							.trim();
				}
			}
			m = imageSourcePattern.matcher(entryHtml);
			while (m.find()) {
				BlogEntryUtils.insertImage(id, m.group(2).trim());
			}
		} else {
			Matcher m = textWithNoImagePattern.matcher(entryHtml);
			if (m.find()) {
				blogText = m.group(2).replaceAll("_", " ").trim();
			}
		}
		return blogText;
	}

	private static List<BlogEntry> parseAllEntries(List<String> links,
			List<String> titles, String html, int upperLimit)
			throws IOException {
		List<BlogEntry> entries = new ArrayList<BlogEntry>();
		List<String> dates = new ArrayList<String>();
		Matcher m = datePattern.matcher(html);
		while (m.find()) {
			dates.add(m.group(2).replaceAll("_", " ").trim());
		}
		int counter = 0;
		for (int i = 0; i < upperLimit; i++) {
			m = idPattern.matcher(links.get(i));
			if (m.find()) {
				int id = Integer.valueOf(m.group(1));
				BlogEntry entry = new BlogEntry(id);
				entry.setBlogDate(dates.get(counter));
				entry.setTitle(titles.get(counter));
				entry.setBlog(parseEntryWithId(id));
				entries.add(entry);
			}
			counter++;
		}
		return entries;
	}

	private static String getHtml(String uri) throws MalformedURLException,
			IOException {
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
