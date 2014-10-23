package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseExpandableAdapter;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SettleMentDetailListExpandableController;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.apos.merchantservice.data.SelementOrderByTxntype;
import me.andpay.apos.merchantservice.data.SelementOrderByterm;
import me.andpay.apos.merchantservice.data.SettlementDetailOrder;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 结算明细列表
 * 
 * @author Administrator
 *
 */

@ContentView(R.layout.settlement_detail_list)
public class SettleMentDetailListActivity extends AposBaseActivity implements
		AdpterEventListener, FinishRequestInterface {

	/* 返回 */
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.settlement_detail_list_back)
	private ImageView backImg;

	/* 标题 */
	@InjectView(R.id.settlement_detail_list_title)
	private TextView titleTxt;

	/* 结算订单 */
	private SelementOrder order;
	/* 统计方式 0 按终端 1按交易类型 */
	private int state;

	/* 视图列表 */
	@InjectView(R.id.settlement_detail_list_listview)
	private ExpandableListView listView;

	private BaseExpandableAdapter<SettlementDetailOrder> adapter;

	@Inject
	SettleMentDetailListExpandableController controller;
	/* 请求管理器 */
	@Inject
	RequestManager requestManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		order = (SelementOrder) TiFlowControlImpl.instanceControl()
				.getFlowContext().get(SelementOrder.class.getName());
		state = (Integer) TiFlowControlImpl.instanceControl().getFlowContext()
				.get("state");
		switch (state) {
		case 0:
			SelementOrderByterm orderByterm = (SelementOrderByterm) order;
			titleTxt.setText("终端号:" + orderByterm.getTermNo());
			break;

		case 1:
			SelementOrderByTxntype orderBytxntype = (SelementOrderByTxntype) order;
			titleTxt.setText("消费类型:" + orderBytxntype.getTxnType());
			break;
		}
		adapter = new BaseExpandableAdapter<SettlementDetailOrder>();
		adapter.setController(controller);
		adapter.setListener(this);
		listView.setGroupIndicator(null);

		listView.setChildDivider(getResources().getDrawable(
				R.drawable.scm_solid_line_img));
		listView.setAdapter(adapter);
		getSettlementDetail(pageSize, page);
		// expandGroup(-1);
	}

	/**
	 * 获得清算详情
	 */
	private CommonDialog txnDialog;
	private int pageSize = 10;
	private int page = 1;

	private void getSettlementDetail(int pageSize, int page) {
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		Map<String, Object> mapData = new HashMap();

		mapData.put(VasOptPropNames.UNRPT_BEGIN_DATE, order.getBeginDate());

		mapData.put(VasOptPropNames.UNRPT_END_DATE, order.getEndDate());
		optRequest.setVasRequestContentObj(mapData);
		optRequest.setMerchantNo("888430179110001");
		optRequest.setPageSize(pageSize);
		optRequest.setCurPageNo(page);
		optRequest
				.setOperateType(VasOptTypes.SETTLE_REPORT_QUERY_STAT_BRIEFINFO);

		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);

		txnDialog = new CommonDialog(this, "读取中...");
		txnDialog.show();
		requestManager.startService();

	}

	public void back(View view) {
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

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		SettlementDetailOrder order = (SettlementDetailOrder) objects[0];
		Map<String, String> data = new HashMap();
		data.put("ssn", order.getSsn());
		data.put("txnTime", order.getTxnTime());
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowNote.SETTLEMENT_DEATAIL, data);

		return false;
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if(response==null){
			ShowUtil.showShortToast(this,getResources().getString(R.string.conection_exception));
		    return;
		}
		CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
		String jsonStr = (String) optResponse
				.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
		adapter.setList(detailOrderScreening(SettlementDetailOrder
				.getArrays(jsonStr)));
		adapter.notifyDataSetChanged();

	}

	/* 订单按日期进行筛选 */
	private ArrayList<ArrayList<SettlementDetailOrder>> detailOrderScreening(
			ArrayList<SettlementDetailOrder> list) {
		TreeMap<String, ArrayList<SettlementDetailOrder>> dataMap = new TreeMap<String, ArrayList<SettlementDetailOrder>>();
		for (int i = 0; i < list.size(); i++) {
			SettlementDetailOrder detailOrder = list.get(i);
			String time = detailOrder.getCreateDate();
			Date tempDate = TimeUtil.getInstance().formatString(time,
					TimeUtil.DATE_PATTERN_11);
			String transferTime = TimeUtil.getInstance().formatDate(tempDate,
					TimeUtil.DATE_PATTERN_2);
			ArrayList<SettlementDetailOrder> dataList = dataMap
					.get(transferTime);
			if (dataList == null) {
				dataList = new ArrayList<SettlementDetailOrder>();
				dataMap.put(transferTime, dataList);
			}
			dataList.add(detailOrder);
		}
		ArrayList<ArrayList<SettlementDetailOrder>> resultList = new ArrayList<ArrayList<SettlementDetailOrder>>();
		Iterator<String> it = dataMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			resultList.add(dataMap.get(key));
		}
		return resultList;
	}
}
