package me.andpay.apos.lft.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.controller.ElectricityOrderController;
import me.andpay.apos.lft.data.ElectricityOrder;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

/**
 * 电费详情
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_electricity_deatail)
public class ElectricityDeatailActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_electricity_deatail_back)
	private ImageView back;// 返回

	@InjectView(R.id.lft_electricity_deatail_listview)
	private ListView listView;// 列表

	@Inject
	BaseAdapter<ElectricityOrder> adapter;

	@Inject
	ElectricityOrderController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter.setContext(this);
		adapter.setList(getList());
		adapter.setController(controller);
		listView.setAdapter(adapter);

	}

	private ArrayList<ElectricityOrder> getList() {
		ArrayList<ElectricityOrder> list = new ArrayList<ElectricityOrder>();
		for (int i = 0; i < 10; i++) {
			ElectricityOrder order = new ElectricityOrder();
			order.setTime(i + 1 + "月");
			order.setOweCost(100 + i + "");
			order.setBreachCost(150 + i + "");
			order.setShouldbeCost(200 + i + "");
			list.add(order);
		}
		return list;
	}

	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}
}
