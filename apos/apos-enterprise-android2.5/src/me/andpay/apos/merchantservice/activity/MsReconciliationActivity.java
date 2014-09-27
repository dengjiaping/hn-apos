package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.MergeAccountsControler;
import me.andpay.apos.merchantservice.controller.SettleMentController;
import me.andpay.apos.merchantservice.data.MergeOrder;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.inject.Inject;

/*银行对账*/

@ContentView(R.layout.ms_reconciliation)
public class MsReconciliationActivity extends AposBaseActivity implements
		AdpterEventListener {
	/* 返回 */
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_reconciliation_back)
	private ImageView back;

	/* 并账明细 */
	@EventDelegate(type = DelegateType.method, toMethod = "mergeAcccounts", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_reliation_merge_accounts)
	private TextView mergeAccount;

	/* 结算明细 */
	@EventDelegate(type = DelegateType.method, toMethod = "settleMentDeatail", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_reliation_settlement_deatail)
	private TextView settleMentDeatail;

	/* 明细列表 */
	@InjectView(R.id.ms_reliation_listview)
	private ListView listView;

	/* 条件查询 */
	@EventDelegate(type = DelegateType.method, toMethod = "query", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_reconciliation_query)
	private Button query;

	/* 统计类型 */
	@InjectView(R.id.ms_reconciliation_statistical_type)
	private View statisticalType;
	/* 显示统计类型 */
	@InjectView(R.id.ms_reconciliation_statistical_showtxt)
	private TextView showTxt;
	/* 选择统计类型 */
	@EventDelegate(type = DelegateType.method, toMethod = "selectStatistical", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_reconciliation_statistical_select)
	private ImageButton selectBtn;

	

	/* 统计类型 0按终端 1按交易类型 */
	private int statisticalState = 0;

	private BaseAdapter<MergeOrder> mergeAccountsAdapter;
	@Inject
	private MergeAccountsControler mergeAccountController;

	private BaseAdapter<SelementOrder> settlementsAdapter;

	@Inject
	private SettleMentController settlementController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/* 并账明细适配初始 */
		mergeAccountsAdapter = new BaseAdapter<MergeOrder>();
		mergeAccountsAdapter.setController(mergeAccountController);

		mergeAccountsAdapter.setList(getMergeOrders());
		mergeAccountsAdapter.setAdpterEventListener(this);

		/* 结算明细适配初始化 */
		settlementsAdapter = new BaseAdapter<SelementOrder>();
		settlementsAdapter.setController(settlementController);

		settlementsAdapter.setList(getSettleMentOrders());
		settlementsAdapter.setAdpterEventListener(this);
		/* 试图初始化 */

		listView.setDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));

		

		mergeAcccounts(null);

	}

	
	
	/**
	 * 统计popwind
	 */
	private PopupWindow statisPopw = null;
	private View popView=null;
	/**
	 * 显示统计选择界面
	 */
	public void showPopwindow(View view,int x,int y) {
		if (statisPopw == null) {
			Object[] ob = ShowUtil.getCustomPopupWindow(this,
					R.layout.select_statistical_type_popwindow, true);
			statisPopw = (PopupWindow) ob[0];
			popView = (View) ob[1];
			popView.findViewById(R.id.select_statistical_type_popwindow_terminal).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					statisPopw.dismiss();
					statisticalState=0;
					showTxt.setText("按终端统计");
					selectBtn.setImageDrawable(getResources().getDrawable(R.drawable.down));
				}
			});
			popView.findViewById(R.id.select_statistical_type_popwindow_trading_type).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					statisPopw.dismiss();
					statisticalState=1;
					showTxt.setText("按交易类型统计");
					selectBtn.setImageDrawable(getResources().getDrawable(R.drawable.down));
				}
			});
		}
		if(!statisPopw.isShowing()){
			selectBtn.setImageDrawable(getResources().getDrawable(R.drawable.up));
			statisPopw.showAsDropDown(view, x, y);
		}
		
	
	}

	private ArrayList<MergeOrder> getMergeOrders() {
		ArrayList<MergeOrder> list = new ArrayList<MergeOrder>();

		for (int j = 0; j < 3; j++) {
			MergeOrder order = new MergeOrder();
			order.setTitle("并账标题" + j);
			order.setTime("2014-09-16");
			order.setLoan("贷 120");
			order.setDescribe("描述");
			order.setBorrowing("借 230");
			list.add(order);
		}

		return list;

	}

	private ArrayList<SelementOrder> getSettleMentOrders() {
		ArrayList<SelementOrder> list = new ArrayList<SelementOrder>();

		for (int j = 0; j < 3; j++) {
			SelementOrder order = new SelementOrder();
			order.setTitle("标题" + j);
			order.setTime("2014-09-16");
			order.setMerchantsCost("商户花费 120");
			order.setSettlementAccount("结算金额 250");
			order.setTradingAccounts("交易金额 345");
			order.setTradingCount("交易次数 " + String.valueOf(5 + j));
			list.add(order);
		}

		return list;
	}

	/****************************************************************************************************************************/
	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/* 选择统计方式 */
	public void selectStatistical(View view) {
		showPopwindow(selectBtn, -5,5);
	}

	/* 并账明细 */
	@SuppressLint("NewApi")
	public void mergeAcccounts(View view) {
		statisticalType.setVisibility(View.GONE);
		mergeAccount.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));

		// mergeAccount.setBackground(getResources().getDrawable(
		// R.drawable.com_button_blue_img));
		mergeAccount.setTextColor(getResources()
				.getColor(android.R.color.white));
		settleMentDeatail.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		settleMentDeatail.setTextColor(getResources().getColor(
				android.R.color.darker_gray));

		listView.setAdapter(mergeAccountsAdapter);

	}

	/* 结算明细 */
	@SuppressLint("NewApi")
	public void settleMentDeatail(View view) {
		statisticalType.setVisibility(View.VISIBLE);
		settleMentDeatail.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		settleMentDeatail.setTextColor(getResources().getColor(
				android.R.color.white));
		mergeAccount.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		mergeAccount.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		listView.setAdapter(settlementsAdapter);

	}

	/**
	 * 条件查询
	 * 
	 * @param view
	 */
	public void query(View view) {
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowNote.CONDITIONS_QUERY);

	}

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		switch ((Integer) objects[0]) {
		case 0:// 并账详情

			break;

		case 1:// 清算详情
			SelementOrder order = (SelementOrder) objects[1];
			TiFlowControlImpl.instanceControl().getFlowContext()
					.put(SelementOrder.class.getName(), order);
			TiFlowControlImpl.instanceControl().getFlowContext()
					.put("state", statisticalState);
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowNote.SETTLEMENT_DEATAIL_LIST);
			break;
		}

		return false;
	}
}
