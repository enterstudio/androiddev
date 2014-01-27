package com.tihonchik.lenonhonor360.db;

import java.util.ArrayList;
import java.util.List;

import com.tihonchik.lenonhonor360.models.BlogEntry;
import com.tihonchik.lenonhonor360.util.AppUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BlogDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "blogDb";
	private static final String TABLE_BLOG_ENTRIES = "blogEntries";

	private static final String KEY_ID = "id";
	private static final String KEY_CREATED = "created";
	private static final String KEY_TITLE = "title";
	private static final String KEY_BLOG = "bog";
	private static final String KEY_BLOG_DATE = "date";

	public BlogDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BLOG_ENTRIES
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CREATED
				+ " TEXT," + KEY_TITLE + " TEXT," + KEY_BLOG + " TEXT,"
				+ KEY_BLOG_DATE + " TEXT," + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public List<BlogEntry> findAllBlogEntries() {
		return findNBlogEntries(0);
	}

	public List<BlogEntry> findNBlogEntries(int numberOfEntries) {
		List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
		String selectQuery = "SELECT  * FROM " + TABLE_BLOG_ENTRIES
				+ " ORDER BY " + KEY_CREATED + "ASC";

		if (numberOfEntries != 0) {
			selectQuery += " DESC Limit " + numberOfEntries;
		}

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				BlogEntry entry = new BlogEntry();
				entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				entry.setCreated(cursor.getString(cursor
						.getColumnIndex(KEY_CREATED)));
				entry.setTitle(cursor.getString(cursor
						.getColumnIndex(KEY_TITLE)));
				entry.setBlog(cursor.getString(cursor.getColumnIndex(KEY_BLOG)));
				entry.setBlogDate(cursor.getString(cursor
						.getColumnIndex(KEY_BLOG_DATE)));
				blogEntries.add(entry);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return blogEntries;
	}

	public int getNewestBlogId() {
		int blogId = -1;
		String selectQuery = "SELECT " + KEY_ID + " FROM " + TABLE_BLOG_ENTRIES
				+ " ORDER BY " + KEY_CREATED + "ASC DESC Limit 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			blogId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
		}
		cursor.close();
		db.close();
		return blogId;
	}

	public void insertNewBlogEntries(List<BlogEntry> blogEntries) {
		if (blogEntries == null || blogEntries.size() == 0) {
			return;
		}

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();

		for (BlogEntry entry : blogEntries) {
			try {
				ContentValues values = new ContentValues();
				values.put(KEY_CREATED, entry.getCreated());
				values.put(KEY_TITLE, entry.getTitle());
				values.put(KEY_BLOG, entry.getBlog());
				values.put(KEY_BLOG_DATE, entry.getBlogDate());
				db.insert(TABLE_BLOG_ENTRIES, null, values);
			} catch (Exception e) {
				Log.e("LH360", e.getLocalizedMessage(), e);
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
}
