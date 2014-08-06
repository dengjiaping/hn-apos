package me.andpay.apos.lft.adapter.controller;

import me.andpay.apos.R;
import me.andpay.apos.lft.adapter.BaseAdapterController;
import me.andpay.apos.lft.flow.CityTable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 城市缴费选择控制器
 * 
 * @author Administrator
 * 
 */
public class CitySelectController extends BaseAdapterController<String> {
	private LayoutInflater inflater = null;

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (inflater == null) {
			inflater = LayoutInflater.from(getAdpter().getContext());
		}
		Holder holder = null;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.lft_city_item, null);
			holder = new Holder();
			holder.cityName = (TextView) arg1
					.findViewById(R.id.lft_city_item_cityname);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}
		holder.cityName.setText(CityTable.city[arg0][0]);
		return arg1;
	}

	@Override
	public void getEvent(View view, final int position) {
		// TODO Auto-generated method stub
		view.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (getAdpter().getAdpterEventListener() != null) {
					getAdpter().getAdpterEventListener().onEventListener(
							new Object[] { position

							});
				}
			}
		});
	}

	class Holder {
		TextView cityName;
	}

}
