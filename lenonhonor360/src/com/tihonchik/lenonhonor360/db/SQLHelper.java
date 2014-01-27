package com.tihonchik.lenonhonor360.db;

public interface SQLHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "blogDb";
	public static final String TABLE_BLOG_ENTRIES = "blogEntries";

	public static final String KEY_ID = "id";
	public static final String KEY_CREATED = "created";
	public static final String KEY_TITLE = "title";
	public static final String KEY_BLOG = "bog";
	public static final String KEY_BLOG_DATE = "date";

	public static final String CREATE_CONTACTS_TABLE = "CREATE TABLE "
			+ TABLE_BLOG_ENTRIES + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_CREATED + " TEXT, " + KEY_TITLE + " TEXT, " + KEY_BLOG
			+ " TEXT," + KEY_BLOG_DATE + " TEXT)";

	public static final String SELECT_ENTRIES = "SELECT * FROM "
			+ TABLE_BLOG_ENTRIES + " ORDER BY " + KEY_CREATED + " ASC";

	public static final String GET_NEWEST_ID = "SELECT " + KEY_ID + " FROM "
			+ TABLE_BLOG_ENTRIES + " ORDER BY " + KEY_CREATED
			+ " ASC Limit 1";

	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_BLOG_ENTRIES;
}
