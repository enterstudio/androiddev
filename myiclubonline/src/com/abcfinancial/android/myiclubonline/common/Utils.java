package com.abcfinancial.android.myiclubonline.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Utils {

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";

	public static Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	public static String addDaysToDate(String originalDate, int daysToAdd) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(originalDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, daysToAdd);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		return sdf1.format(calendar.getTime());
	}
	
	public static String addMonthsToDate(String originalDate, int monthsToAdd) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(originalDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, monthsToAdd);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		return sdf1.format(calendar.getTime());
	}	
}
