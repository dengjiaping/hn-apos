package me.andpay.apos.ssm.activity;

import java.util.LinkedList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SsmBaseAdapter<T> extends BaseAdapter {
	
	private LinkedList<T> records ;

	private String dateStr;
	
	private Activity activity;
	
	public SsmBaseAdapter(LinkedList<T> records, Activity activity, String dateStr) {
		this.records = records;
		this.dateStr = dateStr;
		this.activity = activity;
	}

	public int getCount() {
		return records.size();
	}

	public Object getItem(int position) {
		return records.get(position);
	}

	public abstract long getItemId(int position); 

	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	public LinkedList<T> getRerords() {
		return this.records;
	}

	public Activity getActivity() {
		return activity;
	}

	public String getDateStr() {
		return dateStr;
	}
	
	
	
	
}