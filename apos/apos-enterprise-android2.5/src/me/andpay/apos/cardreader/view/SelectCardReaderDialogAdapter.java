package me.andpay.apos.cardreader.view;

import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectCardReaderDialogAdapter extends BaseAdapter {

	private List<CardReaderInfo> deviceList;
	private Context context;

	public SelectCardReaderDialogAdapter(Context context,
			List<CardReaderInfo> deviceList) {
		this.context = context;
		this.deviceList = deviceList;

	}

	public void updateData(CardReaderInfo cardReaderInfo) {
		deviceList.add(cardReaderInfo);
	}

	public int getCount() {
		return deviceList.size();
	}

	public Object getItem(int position) {
		return deviceList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		CardReaderInfo cardReaderInfo = (CardReaderInfo) getItem(position);
		TextView textView;
		convertView = LayoutInflater.from(context).inflate(
				R.layout.com_landi_cardreader_list_item, null);
		convertView.setDrawingCacheEnabled(false);
		textView = (TextView) convertView.findViewById(R.id.com_text_view);
		textView.setText(cardReaderInfo.getmName());
		convertView.setTag(textView);
		// 设置交
		return convertView;
	}

}
