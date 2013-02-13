package com.abcfinancial.android.myiclubonline.user.billing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.adapters.SectionedAdapter;
import com.abcfinancial.android.myiclubonline.common.Utils;

public class HistoryDisplayActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Bundle extras = getIntent().getExtras();
			int searchType = extras.getInt("SEARCH_TYPE");
			String results = extras.getString("SEARCH_RESULT");

			List<String> historicDates = new ArrayList<String>();
			JSONObject json = new JSONObject(results);
			String typeOfHistory = "payments";
			if(searchType == 1) {
				typeOfHistory = "transactions";
			} else if(searchType == 2){
				typeOfHistory = "checkIns";
			} else {
				// TODO: throw error dialog
			}
			JSONArray jsonArray = json.getJSONArray(typeOfHistory);
			String historicDate;
			for( int i=0; i<json.length(); i++) {
				JSONObject appointment = jsonArray.getJSONObject(i);
				if(searchType == 1 ) {
					historicDate = appointment.getString("purchaseDate");
				} else {
					historicDate = appointment.getString("date");
				}
				if( !historicDates.contains(historicDate)) {
					historicDates.add(historicDate);
				}
			}
			
		    SectionedAdapter adapter = new SectionedAdapter(this);  
			List<Map<String,?>> history;
			List<Integer> sectionSizeCount = new ArrayList<Integer>();
			List<JSONObject> orderedHistoryList = new ArrayList<JSONObject>();
			int elementCount = 0;
			String subtext;
			for( String name : historicDates) {
				history = new LinkedList<Map<String,?>>();
				for( int i=0; i<jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					subtext = "";
					switch(searchType) {
						case 0:
							subtext += "Paid: " + item.getString("amount");
							subtext += "   Late Fee: " + item.getString("lateFee");
							subtext += "   Service Fee: " + item.getString("cancelFee");							
							history.add(Utils.createItem(item.getString("description"), subtext));
							break;
						case 1:
							if(item.getInt("isPosReturn") == 1) {
								subtext = "Return: ";
							} else {
								subtext = "Paid: ";
							}
							subtext += item.getString("totalPaid");
							subtext += "   Payment Method: " + item.getString("payMethods").replace(",", ", ");
							subtext += "   Club: " + item.getString("clubName");
							history.add(Utils.createItem(item.getString("itemList"), subtext));
							break;
						case 2:
							history.add(Utils.createItem(item.getString("clubName"), item.getString("time")));
							break;
						default:
								break;
					}
					orderedHistoryList.add(item);
					elementCount++;
				}
				sectionSizeCount.add(elementCount);
				adapter.addSection(name, new SimpleAdapter(this, history, R.layout.list_complex, new String[] { Utils.ITEM_TITLE, Utils.ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));  
			}
	        ListView list = new ListView(this);  
	        list.setAdapter(adapter);
	        this.setContentView(list); 
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}
