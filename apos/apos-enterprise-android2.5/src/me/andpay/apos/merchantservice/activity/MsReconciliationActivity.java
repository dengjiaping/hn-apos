package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseExpandableAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.MergeAccountsExpandableControler;
import me.andpay.apos.merchantservice.controller.SettleMentExpandableController;
import me.andpay.apos.merchantservice.data.MergeOrder;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
	@InjectView(R.id.ms_reliation_expandable_listview)
	private ExpandableListView ms_listView;

	private BaseExpandableAdapter<MergeOrder> mergeAccountsAdapter;
	@Inject
	private MergeAccountsExpandableControler mergeAccountController;
	
	private BaseExpandableAdapter<SelementOrder> settlementsAdapter;
	
	@Inject
	private SettleMentExpandableController settlementController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/* 并账明细适配初始 */
		mergeAccountsAdapter = new BaseExpandableAdapter<MergeOrder>();
		mergeAccountsAdapter.setController(mergeAccountController);

		mergeAccountsAdapter.setList(getMergeOrders());
		mergeAccountsAdapter.setListener(this);
		
		/*结算明细适配初始化*/
		settlementsAdapter= new BaseExpandableAdapter<SelementOrder>();
		settlementsAdapter.setController(settlementController);
		
		settlementsAdapter.setList(getSettleMentOrders());
		settlementsAdapter.setListener(this);
        /*试图初始化*/
		ms_listView.setGroupIndicator(null);
		ms_listView.setDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));
		ms_listView.setChildDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));

		mergeAcccounts(null);

	}

	private ArrayList<ArrayList<MergeOrder>> getMergeOrders() {
		ArrayList<ArrayList<MergeOrder>> list = new ArrayList<ArrayList<MergeOrder>>();
		for (int i = 0; i < 3; i++) {
			ArrayList<MergeOrder> data = new ArrayList<MergeOrder>();
			for (int j = 0; j < 3; j++) {
				MergeOrder order = new MergeOrder();
				order.setTitle("并账标题" + i);
				order.setTime("2014-09-16");
				order.setLoan("贷 120");
				order.setDescribe("描述");
				order.setBorrowing("借 230");
				data.add(order);
			}
			list.add(data);
		}
		return list;

	}

	private ArrayList<ArrayList<SelementOrder>> getSettleMentOrders() {
		ArrayList<ArrayList<SelementOrder>> list = new ArrayList<ArrayList<SelementOrder>>();
		for (int i = 0; i < 3; i++) {
			ArrayList<SelementOrder> data = new ArrayList<SelementOrder>();
			for (int j = 0; j < 3; j++) {
				SelementOrder order = new SelementOrder();
				order.setTitle("标题" + i);
				order.setTime("2014-09-16");
				order.setMerchantsCost("商户花费 120");
				order.setSettlementAccount("结算金额 250");
				order.setTradingAccounts("交易金额 345");
				order.setTradingType("交易类型");
				data.add(order);
			}
			list.add(data);
		}
		return list;
	}

	/****************************************************************************************************************************/
	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/* 并账明细 */
	public void mergeAcccounts(View view) {
		mergeAccount.setBackground(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		mergeAccount.setTextColor(getResources()
				.getColor(android.R.color.white));
		settleMentDeatail.setBackground(getResources().getDrawable(
				R.drawable.com_button_img));
		settleMentDeatail.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		ms_listView.setAdapter(mergeAccountsAdapter);
	}

	/* 结算明细 */
	public void settleMentDeatail(View view) {
		settleMentDeatail.setBackground(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		settleMentDeatail.setTextColor(getResources().getColor(
				android.R.color.white));
		mergeAccount.setBackground(getResources().getDrawable(
				R.drawable.com_button_img));
		mergeAccount.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		ms_listView.setAdapter(settlementsAdapter);
	}

	public boolean onEventListener(Object... objects){
		// TODO Auto-generated method stub
		return false;
	}
}
