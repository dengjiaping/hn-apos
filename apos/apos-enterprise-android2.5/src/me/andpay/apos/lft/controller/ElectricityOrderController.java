package me.andpay.apos.lft.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.lft.data.ElectricityOrder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ElectricityOrderController extends BaseAdapterController<ElectricityOrder>{
    private LayoutInflater inflater=null;
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(inflater==null){
			inflater =LayoutInflater.from(getAdpter().getContext());
		}
		Holder holder=null;
		if(arg1==null){
			arg1 = inflater.inflate(R.layout.electricity_order_item,null);
			holder = new Holder();
			holder.time	=(TextView)arg1.findViewById(R.id.electricity_order_item_time);
			holder.oweCost=(TextView)arg1.findViewById(R.id.electricity_order_item_owecost);
			holder.breachCost=(TextView)arg1.findViewById(R.id.electricity_order_item_breachcost);
			holder.shouldbeCost=(TextView)arg1.findViewById(R.id.electricity_order_item_shouldbecost);
			arg1.setTag(holder);
		}else{
			holder=(Holder)arg1.getTag();
		}
		ElectricityOrder eleOrder = getAdpter().getList().get(arg0);
		holder.time.setText(eleOrder.getTime());
		holder.oweCost.setText(eleOrder.getOweCost());
		holder.breachCost.setText(eleOrder.getBreachCost());
		holder.shouldbeCost.setText(eleOrder.getShouldbeCost());
		return arg1;
	}

	@Override
	public void getEvent(View view, int position) {
		// TODO Auto-generated method stub
		
	}
	class Holder{
		TextView time;
		TextView oweCost;
		TextView breachCost;
		TextView shouldbeCost;
	}
	
}
