package com.tihonchik.lenonhonor360.util;

import java.util.List;

import com.tihonchik.lenonhonor360.app.LenonHonor360App;
import com.tihonchik.lenonhonor360.db.BlogDatabase;
import com.tihonchik.lenonhonor360.models.BlogEntry;

public class BlogEntryUtils {
	public static int getNewestBlogId() {
		BlogDatabase db = new BlogDatabase(LenonHonor360App.getInstance()
				.getApplicationContext());
		return db.getNewestBlogId();
	}

	public static void insertBlogEntries(List<BlogEntry> entries) {
		BlogDatabase db = new BlogDatabase(LenonHonor360App.getInstance()
				.getApplicationContext());
		db.insertNewBlogEntries(entries);
	}

	public static List<BlogEntry> getAllBlogEntries() {
		BlogDatabase db = new BlogDatabase(LenonHonor360App.getInstance()
				.getApplicationContext());
		return db.findAllBlogEntries();
	}

	public static void insertImage(int id, String image) {
		BlogDatabase db = new BlogDatabase(LenonHonor360App.getInstance()
				.getApplicationContext());
		db.insertNewImage(id, image);
	}
}
