package me.andpay.apos.cmview.tispinner;

import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TiSpinnerAdapter extends BaseAdapter {

	private List<TiSpinnerItem> tiSpinnerItemems;

	public TiSpinnerAdapter(List<?> items) {
		tiSpinnerItemems = new ArrayList<TiSpinnerItem>();
		for (int i = 0; i < items.size(); i++) {
			TiSpinnerItem item = TiSpinnerItem.class.cast(items.get(i));
			item.setIndex(i);
			tiSpinnerItemems.add(item);
		}
	}

	public int getCount() {
		return tiSpinnerItemems.size();
	}

	public Object getItem(int position) {
		return tiSpinnerItemems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return position;
	}

	public int getViewTypeCount() {
		return tiSpinnerItemems.size();
	}

	public boolean isEmpty() {
		return false;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	public List<TiSpinnerItem> getTiSpinnerItemems() {
		return tiSpinnerItemems;
	}

}
