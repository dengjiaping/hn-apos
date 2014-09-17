package me.andpay.apos.merchantservice.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseExpandableAdapterController;
import me.andpay.apos.merchantservice.data.MergeOrder;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*并账控制器*/
public class MergeAccountsExpandableControler extends
		BaseExpandableAdapterController<MergeOrder> {
	LayoutInflater lyInflater = LayoutInflater.from(TiApplication.getContext()); 

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		// TODO Auto-generated method stub
		Holder1 holder;
		if(arg3==null){
			arg3 = lyInflater.inflate(R.layout.merge_order_item,null);
			holder = new Holder1();
			holder.title=(TextView) arg3.findViewById(R.id.merge_order_item_title);
			holder.describe = (TextView)arg3.findViewById(R.id.merge_order_item_describe);
			holder.borrowing = (TextView)arg3.findViewById(R.id.merge_order_item_borrowing);
			holder.loan = (TextView)arg3.findViewById(R.id.merge_order_item_loan);
			arg3.setTag(holder);
		}
		holder = (Holder1)arg3.getTag();
		
		MergeOrder order = getAdapter().getList().get(arg0).get(arg1);		
		holder.title.setText(order.getTitle());
		holder.describe.setText(order.getDescribe());
		holder.borrowing.setText(order.getBorrowing());
		holder.loan.setText(order.getLoan());
		
		return arg3;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(arg2==null){
			arg2 = lyInflater.inflate(R.layout.head_data_item, null);
			holder = new Holder();
			holder.time = (TextView)arg2.findViewById(R.id.head_data_item_time);
			arg2.setTag(holder);
		}
		holder=(Holder)arg2.getTag();
		MergeOrder order = getAdapter().getList().get(0).get(0);
		holder.time.setText(order.getTime());
		return arg2;
	}
	
	class Holder{
		TextView time;
	}
	class Holder1{
		TextView title;
		TextView describe;
		TextView borrowing;
		TextView loan;
		
		
	}

}
