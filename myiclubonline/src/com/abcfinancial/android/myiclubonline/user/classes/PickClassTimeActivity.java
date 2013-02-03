package com.abcfinancial.android.myiclubonline.user.classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.abcfinancial.android.myiclubonline.R;
import com.abcfinancial.android.myiclubonline.adapters.SectionedAdapter;
import com.abcfinancial.android.myiclubonline.common.Utils;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class PickClassTimeActivity extends ExpandableListActivity {

	List<Integer> sectionSizeCount;
	List<JSONObject> orderedClassList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String classScheduleObject = extras.getString("CLASS_SCHEDULE");

		List<String> calendarDates = new ArrayList<String>();
		try {
			JSONObject json = new JSONObject(classScheduleObject);
			JSONArray classSchedule = json.getJSONArray("mobileClassSchedule");
			for (int i = 0; i < classSchedule.length(); i++) {
				JSONObject mobileClass = classSchedule.getJSONObject(i);
				if (!calendarDates.contains(mobileClass.getString("eventDate"))) {
					calendarDates.add(mobileClass.getString("eventDate"));
				}
			}

			SectionedAdapter adapter = new SectionedAdapter(this);
			List<Map<String, ?>> classesOnDate;
			sectionSizeCount = new ArrayList<Integer>();
			orderedClassList = new ArrayList<JSONObject>();
			int elementCount = 0;
			for (String date : calendarDates) {
				classesOnDate = new LinkedList<Map<String, ?>>();
				JSONObject mobileClass, employee;
				for (int i = 0; i < classSchedule.length(); i++) {
					mobileClass = classSchedule.getJSONObject(i);
					employee = mobileClass.getJSONObject("employee");
					if (date.equals(mobileClass.getString("eventDate"))) {
						classesOnDate.add(Utils.createItem(mobileClass.getString("eventStartTime"), employee.getString("employeeName")));
						orderedClassList.add(mobileClass);
						elementCount++;
					}
				}
				sectionSizeCount.add(elementCount);
				elementCount = 0;
				adapter.addSection(date, new SimpleAdapter(this, classesOnDate,
						R.layout.list_complex, new String[] { Utils.ITEM_TITLE,
								Utils.ITEM_CAPTION }, new int[] {
								R.id.list_complex_title,
								R.id.list_complex_caption }));
			}
			ListView listView = new ListView(this);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					System.out.println("POSITION: " + position);
					int totalCount = 0;
					JSONObject selectedClass = null;
					for(int i=0; i<sectionSizeCount.size(); i++) {
						if(position <= (i+1+sectionSizeCount.get(i)+totalCount)) {
							selectedClass = orderedClassList.get(position-(i+1));
							break;
						}
						totalCount += sectionSizeCount.get(i);
					}
					try {
						if("0".equals(selectedClass.getString("isFree")) && !selectedClass.getBoolean("alreadyEnrolled")){
							// Call "isServiceAvailable"
						} else {
							Intent intent = new Intent(getParent(), ClassEnrollDetailActivity.class);
							intent.putExtra("CLASS_DETAIL", selectedClass.toString());
							EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
							parentActivity.startChildActivity("ClassEnrollDetailActivity", intent);							
						}
					} catch (JSONException exception) {
						// TODO: throw error dialog
					}
				}
			});
			this.setContentView(listView);
		} catch (Exception exception) {
			// TODO: throw error dialog
		}
	}
}