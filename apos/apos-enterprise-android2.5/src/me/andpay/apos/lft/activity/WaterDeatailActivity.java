package me.andpay.apos.lft.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.controller.WaterOrderController;
import me.andpay.apos.lft.data.WaterOrder;
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
 * 缴纳水费详情
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_water_deatail)
public class WaterDeatailActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_water_deatail_back)
	private ImageView back;// 返回

	@InjectView(R.id.lft_water_deatail_listview)
	private ListView listView;//列表

	@Inject
	BaseAdapter<WaterOrder> adapter;

	@Inject
	WaterOrderController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter.setContext(this);
		adapter.setList(getList());
		adapter.setController(controller);
		listView.setAdapter(adapter);

	}

	private ArrayList<WaterOrder> getList() {
		ArrayList<WaterOrder> list = new ArrayList<WaterOrder>();
		for (int i = 0; i < 10; i++) {
			WaterOrder order = new WaterOrder();
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
