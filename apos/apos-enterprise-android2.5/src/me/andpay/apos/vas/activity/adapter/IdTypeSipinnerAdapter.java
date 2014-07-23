package me.andpay.apos.vas.activity.adapter;

import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cmview.tispinner.TiSpinnerAdapter;
import me.andpay.apos.cmview.tispinner.TiSpinnerItem;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IdTypeSipinnerAdapter extends TiSpinnerAdapter {

	private Activity activity;

	public IdTypeSipinnerAdapter(List<?> items, Activity activity) {
		super(items);
		this.activity = activity;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		TiSpinnerItem item = (TiSpinnerItem) getItem(position);

		convertView = LayoutInflater.from(activity).inflate(
				R.layout.com_idtype_spinner_item_layout, null);
		TextView textView = (TextView) convertView
				.findViewById(R.id.com_type_name_text);
		textView.setText(item.getName());
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.com_id_type_img);
		convertView.setTag(imageView);

		if (item.isSelectFlag()) {
			imageView.setVisibility(View.VISIBLE);
		} else {
			imageView.setVisibility(View.GONE);
		}

		return convertView;
	}

}
