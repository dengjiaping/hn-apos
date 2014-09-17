package me.andpay.apos.lft.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.lft.data.OftenUser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class OftenPersonController extends BaseAdapterController<OftenUser> {

	private LayoutInflater flater = null;
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (arg1 == null) {
			holder = new Holder();
			if(flater==null){
				flater=LayoutInflater.from(getAdpter()
						.getContext());

			}
			arg1 = flater.inflate(R.layout.lft_often_person_item, null);
			holder.userName = (TextView) arg1
					.findViewById(R.id.lft_often_person_item_user_name);
			holder.bankName = (TextView) arg1
					.findViewById(R.id.lft_often_person_item_bank_name);
			holder.bankNumber = (TextView) arg1
					.findViewById(R.id.lft_often_person_item_bank_number);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}
		OftenUser oftenUser = getAdpter().getList().get(arg0);
		holder.userName.setText(oftenUser.getUserName());
		holder.bankName.setText(oftenUser.getBankName());
		holder.bankNumber.setText(oftenUser.getBankCardNumber());

		return arg1;
	}

	@Override
	public void getEvent(View view,final int position) {
		// TODO Auto-generated method stub
		view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AdpterEventListener listener = getAdpter().getAdpterEventListener();
				if(listener!=null){
					OftenUser oftenUser = getAdpter().getList().get(position);
					listener.onEventListener(new Object[]{oftenUser});
				}
			}
		});

	}

	class Holder {
		TextView userName;
		TextView bankName;
		TextView bankNumber;
	}

}
