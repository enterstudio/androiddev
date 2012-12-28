package com.abcfinancial.android.myiclubonline.common;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

@TargetApi(11)
public class DatePickerCustomDialog extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	OnDateSelectedListener activityListener;
	private String selectedDate = "";
	private int whichDate;
	
	public DatePickerCustomDialog(int whichDate) {
		this.whichDate = whichDate;
	}
	
	@TargetApi(11)
	@Override
	public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		this.selectedDate = (month+1) + "/" + day + "/" + year;
		activityListener.onDateSelected(this.selectedDate, this.whichDate);
	}

	public interface OnDateSelectedListener {
		public void onDateSelected(String selectedDate, int whichDate);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityListener = (OnDateSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDateSelectedListener");
		}
	}
}