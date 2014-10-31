package me.andpay.apos.merchantservice.activity;

import java.util.Map;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.merchantservice.controller.MergeAccountsControler;
import me.andpay.apos.merchantservice.controller.SettleMentByTxntypeController;
import me.andpay.apos.merchantservice.controller.SettleMentBytermController;
import me.andpay.apos.merchantservice.data.MergeOrder;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.apos.merchantservice.data.SelementOrderByTxntype;
import me.andpay.apos.merchantservice.data.SelementOrderByterm;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.TiFlowCallback;
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
		AdpterEventListener, TiFlowCallback, FinishRequestInterface,
		OnClickListener {
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

	@InjectView(R.id.ms_reconciliation_time)
	private TextView time;

	/**
	 * 商户名称
	 */
	@InjectView(R.id.ms_reconciliation_company)
	private TextView merchanName;
	/**
	 * 商户号
	 */
	@InjectView(R.id.ms_reconciliation_user_code)
	private TextView merchanNo;

	/* 统计类型 0按终端 1按交易类型 */
	private int statisticalState = 0;

	private BaseAdapter<MergeOrder> mergeAccountsAdapter;
	@Inject
	private MergeAccountsControler mergeAccountController;

	/* 终端清算 */
	private BaseAdapter<SelementOrderByterm> settlementsBytermAdapter;
	@Inject
	private SettleMentBytermController settlementBytermController;
	/* 交易类型清算 */
	private BaseAdapter<SelementOrderByTxntype> settlementsBytxntypeAdapter;
	@Inject
	private SettleMentByTxntypeController settlementBytxntypeController;

	@Inject
	private RequestManager requestManager;

	/**
	 * 失败
	 */
	private View faile;
	/**
	 * 空
	 */
	private View empty;

	private Button refreshBtn1;
	private Button refreshBtn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LoginUserInfo logInfo = (LoginUserInfo) this.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		PartyInfo partyInfo = (PartyInfo) this.getAppContext().getAttribute(
				RuntimeAttrNames.PARTY_INFO);

		merchanName.setText("商户名称" + partyInfo.getPartyName());
		merchanNo.setText("商户名称" + logInfo.getUserName());

		txnDialog = new CommonDialog(this, "读取中...");
		faile = findViewById(R.id.ms_reconciliation_faile);
		empty = findViewById(R.id.ms_reconciliation_empty);
		refreshBtn1 = (Button) faile.findViewById(R.id.refresh_btn);
		refreshBtn2 = (Button) empty.findViewById(R.id.refresh_btn);
		refreshBtn1.setOnClickListener(this);
		refreshBtn2.setOnClickListener(this);
		/* 并账明细适配初始 */
		mergeAccountsAdapter = new BaseAdapter<MergeOrder>();
		mergeAccountsAdapter.setController(mergeAccountController);
		mergeAccountsAdapter.setAdpterEventListener(this);

		/* 结算明细适配初始化 */
		settlementsBytermAdapter = new BaseAdapter<SelementOrderByterm>();
		settlementsBytermAdapter.setController(settlementBytermController);
		settlementsBytermAdapter.setAdpterEventListener(this);

		settlementsBytxntypeAdapter = new BaseAdapter<SelementOrderByTxntype>();
		settlementsBytxntypeAdapter
				.setController(settlementBytxntypeController);
		settlementsBytxntypeAdapter.setAdpterEventListener(this);
		/* 试图初始化 */

		listView.setDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));

		time.setText("全部");

		// mergeAcccounts(null);
		settleMentDeatail(null);

	}

	/**
	 * 0空 1有数据-1 失败
	 * 
	 * @param state
	 */
	private void selectStatus(int state) {
		switch (state) {
		case 0:
			empty.setVisibility(View.VISIBLE);
			faile.setVisibility(View.GONE);
			break;

		case 1:
			empty.setVisibility(View.GONE);
			faile.setVisibility(View.GONE);
			break;
		case -1:
			empty.setVisibility(View.GONE);
			faile.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 统计popwind
	 */
	private PopupWindow statisPopw = null;
	private View popView = null;

	/**
	 * 显示统计选择界面
	 */
	public void showPopwindow(View view, int x, int y) {
		if (statisPopw == null) {
			Object[] ob = ShowUtil.getCustomPopupWindow(this,
					R.layout.select_statistical_type_popwindow, true);
			statisPopw = (PopupWindow) ob[0];
			popView = (View) ob[1];
			popView.findViewById(
					R.id.select_statistical_type_popwindow_terminal)
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							// TODO Auto-generated method stub
							statisPopw.dismiss();
							statisticalState = 0;
							showTxt.setText("按终端统计");
							selectBtn.setImageDrawable(getResources()
									.getDrawable(R.drawable.down));
							txnDialog.show();
							getSettleMentOrders(PAGE_SIZE,
									currentSettlePage = 1);
						}
					});
			popView.findViewById(
					R.id.select_statistical_type_popwindow_trading_type)
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							// TODO Auto-generated method stub

							statisPopw.dismiss();
							statisticalState = 1;
							showTxt.setText("按交易类型统计");
							selectBtn.setImageDrawable(getResources()
									.getDrawable(R.drawable.down));
							txnDialog.show();
							getSettleMentOrders(PAGE_SIZE,
									currentSettlePage = 1);
						}
					});
		}
		if (!statisPopw.isShowing()) {
			selectBtn.setImageDrawable(getResources()
					.getDrawable(R.drawable.up));
			statisPopw.showAsDropDown(view, x, y);
		}

	}

	/**
	 * 加载条
	 */
	private CommonDialog txnDialog;
	private final int PAGE_SIZE = 10;
	private int currentMergePage = 1;
	private int currentSettlePage = 1;

	/**
	 * 获取并账目明细
	 * 
	 * @param pageSize
	 * @param page
	 */
	private void getMergeOrders(int pageSize, int page) {

		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		Map<String, Object> mapData = new HashMap();
		if (!StringUtil.isEmpty(beginTime)) {
			mapData.put(VasOptPropNames.UNRPT_BEGIN_DATE, beginTime);
		}
		if (!StringUtil.isEmpty(endTime)) {
			mapData.put(VasOptPropNames.UNRPT_END_DATE, endTime);
		}
		// PartyInfo partyInfo = (PartyInfo) this.getAppContext().getAttribute(
		// RuntimeAttrNames.PARTY_INFO);
		optRequest.setMerchantNo("888430179110001");
		optRequest.setPageSize(pageSize);
		optRequest.setCurPageNo(page);

		optRequest.setOperateType(VasOptTypes.MERGE_REPORT_QUERY_ACCT_INFO);

		optRequest.setVasRequestContentObj(mapData);

		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);

		requestManager.startService();

	}

	/**
	 * 获取清算明细
	 * 
	 * @param pageSize
	 * @param page
	 */

	private void getSettleMentOrders(int pageSize, int page) {

		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		Map<String, Object> mapData = new HashMap();
		if (!StringUtil.isEmpty(beginTime)) {
			mapData.put(VasOptPropNames.UNRPT_BEGIN_DATE, beginTime);
		}

		if (!StringUtil.isEmpty(endTime)) {
			mapData.put(VasOptPropNames.UNRPT_END_DATE, endTime);
		}

		// PartyInfo partyInfo = (PartyInfo) this.getAppContext().getAttribute(
		// RuntimeAttrNames.PARTY_INFO);
		optRequest.setMerchantNo("888430179110001");
		optRequest.setPageSize(pageSize);
		optRequest.setCurPageNo(page);

		optRequest.setVasRequestContentObj(mapData);
		switch (statisticalState) {
		case 0:// 终端
			optRequest
					.setOperateType(VasOptTypes.SETTLE_REPORT_QUERY_STAT_BY_TERM);
			break;

		case 1:// 交易类型
			optRequest
					.setOperateType(VasOptTypes.SETTLE_REPORT_QUERY_STAT_BY_TXNTYPE);
			break;
		}

		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);

		requestManager.startService();

	}

	/****************************************************************************************************************************/
	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/* 选择统计方式 */
	public void selectStatistical(View view) {
		showPopwindow(selectBtn, -5, 5);
	}

	/* 并账 清算 状态 */
	private int getState = 0;// 0并账 1清算明细

	/* 并账明细 */
	@SuppressLint("NewApi")
	public void mergeAcccounts(View view) {
		selectStatus(1);
		getState = 0;
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
		if (mergeAccountsAdapter.getList().size() <= 0) {
			txnDialog.show();
			getMergeOrders(PAGE_SIZE, currentMergePage = 1);
		}

	}

	/* 结算明细 */
	@SuppressLint("NewApi")
	public void settleMentDeatail(View view) {
		selectStatus(1);
		getState = 1;
		statisticalType.setVisibility(View.VISIBLE);
		settleMentDeatail.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		settleMentDeatail.setTextColor(getResources().getColor(
				android.R.color.white));
		mergeAccount.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		mergeAccount.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		switch (statisticalState) {
		case 0:// 终端
			listView.setAdapter(settlementsBytermAdapter);
			if (settlementsBytermAdapter.getList().size() <= 0) {
				txnDialog.show();
				getSettleMentOrders(PAGE_SIZE, currentSettlePage = 1);
			}
			break;

		case 1:// 交易类型
			listView.setAdapter(settlementsBytxntypeAdapter);
			if (settlementsBytxntypeAdapter.getList().size() <= 0) {
				txnDialog.show();
				getSettleMentOrders(PAGE_SIZE, currentSettlePage = 1);
			}
			break;
		}

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

	private String beginTime = "";
	private String endTime = "";

	public void callback(String sourceNodeName) {
		// TODO Auto-generated method stub

		beginTime = (String) TiFlowControlImpl.instanceControl()
				.getFlowContext().get("beginTime");
		endTime = (String) TiFlowControlImpl.instanceControl().getFlowContext()
				.get("endTime");

		if (!StringUtil.isEmpty(beginTime) && !StringUtil.isEmpty(endTime)) {

			beginTime = TimeUtil.getInstance().formatDate(
					TimeUtil.getInstance().formatString(beginTime,
							TimeUtil.DATE_PATTERN_1), TimeUtil.DATE_PATTERN_2);
			endTime = TimeUtil.getInstance().formatDate(
					TimeUtil.getInstance().formatString(endTime,
							TimeUtil.DATE_PATTERN_1), TimeUtil.DATE_PATTERN_2);
			time.setText(beginTime + "至" + endTime);
		}

	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null
				&& (getState == 0 ? currentMergePage : currentSettlePage) == 1) {
			selectStatus(-1);

			return;
		}
		if (response == null) {
			selectStatus(1);

			return;
		}
		CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
		if (!optResponse.isSuccess()
				&& (getState == 0 ? currentMergePage : currentSettlePage) == 1) {
			selectStatus(-1);
			return;
		}
		if (!optResponse.isSuccess()) {
			selectStatus(1);
			return;
		}
		String resultStr = (String) optResponse
				.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
		if (StringUtil.isEmpty(resultStr)
				&& (getState == 0 ? currentMergePage : currentSettlePage) == 1) {
			selectStatus(0);
			return;
		}
		if (StringUtil.isEmpty(resultStr)) {
			selectStatus(1);
			return;
		}

		selectStatus(1);

		switch (getState) {
		case 0:// 并账
			currentMergePage++;
			mergeAccountsAdapter.setList(MergeOrder.getArrays(resultStr));
			mergeAccountsAdapter.notifyDataSetChanged();
			break;

		case 1:// 清算
			switch (statisticalState) {
			case 0:// 按终端
				currentSettlePage++;
				settlementsBytermAdapter.setList(SelementOrderByterm
						.getArrays(resultStr));
				settlementsBytermAdapter.notifyDataSetChanged();
				break;

			case 1:// 按交易类型
				currentSettlePage++;
				settlementsBytxntypeAdapter.setList(SelementOrderByTxntype
						.getArrays(resultStr));
				settlementsBytxntypeAdapter.notifyDataSetChanged();
				break;
			}
			break;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.refresh_btn) {
			refreshData();
		}
	}

	/**
	 * 刷新数据
	 */
	private void refreshData() {
		selectStatus(1);
		switch (getState) {
		case 0:// 并账
			txnDialog.show();
			getMergeOrders(PAGE_SIZE, currentMergePage = 1);
			break;

		case 1:// 清算
			txnDialog.show();
			getSettleMentOrders(PAGE_SIZE, currentSettlePage = 1);
			break;
		}
	}
}
