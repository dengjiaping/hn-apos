package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import com.google.inject.Inject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseExpandableAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SettleMentDetailListExpandableController;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.apos.merchantservice.data.SettlementDetailOrder;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
/**
 * 结算明细列表
 * @author Administrator
 *
 */
@ContentView(R.layout.settlement_detail_list)
public class SettleMentDetailListActivity extends AposBaseActivity implements AdpterEventListener{
	
	/*返回*/
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
	@InjectView(R.id.settlement_detail_list_back)
	private ImageView backImg;
	
	
	/*标题*/
	@InjectView(R.id.settlement_detail_list_title)
	private TextView titleTxt;
	
	/*结算订单*/
	private SelementOrder order;
	/*统计方式 0 按终端 1按交易类型*/
	private int state;
	
	/*视图列表*/
	@InjectView(R.id.settlement_detail_list_listview)
	private ExpandableListView listView;
	
	
	private BaseExpandableAdapter<SettlementDetailOrder> adapter;
	
	@Inject SettleMentDetailListExpandableController controller;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		order = (SelementOrder)TiFlowControlImpl.instanceControl().getFlowContext().get(SelementOrder.class.getName());
		state=(Integer)TiFlowControlImpl.instanceControl().getFlowContext().get("state");
		switch (state) {
		case 0:
			titleTxt.setText("终端号"+order.getTitle());
			break;

		case 1:
			titleTxt.setText("消费");
			break;
		}
		adapter = new BaseExpandableAdapter<SettlementDetailOrder>();
		adapter.setList(getList());
		adapter.setController(controller);
		adapter.setListener(this);
		
		listView.setGroupIndicator(null);

//		listView.setDivider(getResources().getDrawable(
//				R.drawable.scm_solid_line_img));
		listView.setChildDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));
		listView.setAdapter(adapter);
		expandGroup(-1);
		
		
	}
	public void back(View view){
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}
	
	/* 展开expand */
	private void expandGroup(int i) {
		ExpandableListAdapter adapter = listView.getExpandableListAdapter();
		if (adapter == null) {
			return;
		}
		int gCount = adapter.getGroupCount();
		if (i < 0) {
			for (int j = 0; j < gCount; j++) {
				listView.expandGroup(j);

			}
		} else if (i < gCount) {
			listView.expandGroup(i);
		}

	}

	
	private ArrayList<ArrayList<SettlementDetailOrder>> getList(){
		ArrayList<ArrayList<SettlementDetailOrder>> list = new ArrayList<ArrayList<SettlementDetailOrder>>();
		for(int i=0;i<2;i++){
			ArrayList<SettlementDetailOrder> ls = new ArrayList<SettlementDetailOrder>();
			SettlementDetailOrder order= new SettlementDetailOrder();
			order.setTitle("标题"+i);
			order.setTradingAccounts("交易金额 250");
			order.setMerchantsCost("商户花费 100");
			order.setSettlementAccount("结算金额 200");
			order.setTime("2010-09-12");
			ls.add(order);
			list.add(ls);
		}
		return list;
	}


	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		SettlementDetailOrder order = (SettlementDetailOrder)objects[0];
		TiFlowControlImpl.instanceControl().nextSetup(this, FlowNote.SETTLEMENT_DEATAIL);
		return false;
	}
}
