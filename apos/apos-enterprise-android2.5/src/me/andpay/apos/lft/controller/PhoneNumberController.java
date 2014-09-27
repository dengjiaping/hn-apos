package me.andpay.apos.lft.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.lft.data.PhoneNumber;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhoneNumberController extends BaseAdapterController<PhoneNumber> {
	private LayoutInflater inflater;

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (inflater == null) {
			inflater = LayoutInflater.from(getAdpter().getContext());
		}
		Holder holder = null;
		if (arg1 == null) {

			arg1 = inflater.inflate(R.layout.lft_phone_item, null);
			holder = new Holder();
			holder.name = (TextView) arg1
					.findViewById(R.id.lft_phone_item_name);
			holder.number = (TextView) arg1
					.findViewById(R.id.lft_phone_item_number);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}
		PhoneNumber phoneNumber = getAdpter().getList().get(arg0);
		holder.name.setText(phoneNumber.getDisplayName());
		holder.number.setText(phoneNumber.getDisplayNumber());
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
							getAdpter().getList().get(position));
				}
			}
		});
	}

	class Holder {
		TextView name;
		TextView number;
	}

}
