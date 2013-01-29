package com.abcfinancial.android.myiclubonline.common;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public final static String ITEM_TITLE = "title";  
    public final static String ITEM_CAPTION = "caption";
	
    public static Map<String,?> createItem(String title, String caption) {  
        Map<String,String> item = new HashMap<String,String>();  
        item.put(ITEM_TITLE, title);  
        item.put(ITEM_CAPTION, caption);  
        return item;  
    } 
}
