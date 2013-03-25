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
import com.abcfinancial.android.myiclubonline.common.Enums.RequestMethod;
import com.abcfinancial.android.myiclubonline.common.Utils;
import com.abcfinancial.android.myiclubonline.common.WebServiceClient;
import com.abcfinancial.android.myiclubonline.usergroups.EventsGroupActivity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.AsyncTask;
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
		try {
			Bundle extras = getIntent().getExtras();
			String classScheduleObject = extras.getString("CLASS_SCHEDULE");
			
			List<String> calendarDates = new ArrayList<String>();
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
						System.out.println("POSITION: " + selectedClass);
						if("0".equals(selectedClass.getString("isFree")) && !selectedClass.getBoolean("alreadyEnrolled")){
							
	/*						
						    [request addPostValue:self.selectedClub forKey:@"club"];
						    [request addPostValue:memberId forKey:@"memberId"]; 
						    [request addPostValue:eventDate forKey:@"eventDate"];
						    [request addPostValue:[eventData objectForKey:@"eventTypeId"] forKey:@"eventTypeId"];
						    [request addPostValue:[eventData objectForKey:@"levelId"] forKey:@"levelId"];
	*/					    
						    
//							new WebServiceTask().execute("isserviceavailable", memberId, clubNumber, selectedClass.getString("eventDate"), selectedClass.getString("")));
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
	
	private class WebServiceTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			WebServiceClient client = new WebServiceClient(uri[0]);
			try {
				client.addParameter("memberId", uri[1]);
				client.addParameter("club", uri[2]);
				JSONObject classInfo = new JSONObject(uri[3]);
				client.addParameter("bookingWindowHours", classInfo.getString("bookingHourStart"));
				client.addParameter("eventTypeId", classInfo.getString("eventTypeId"));
				client.execute(RequestMethod.POST);
			} catch (JSONException exception) {
				exception.printStackTrace();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return client.getResponse();
		}

		@Override
		protected void onPostExecute(String response) {
			Intent intent = new Intent(getParent(), PickClassTimeActivity.class);
			intent.putExtra("CLASS_SCHEDULE", response);
			EventsGroupActivity parentActivity = (EventsGroupActivity) getParent();
			parentActivity.startChildActivity("PickClassTimeActivity", intent);
		}
	}	
}