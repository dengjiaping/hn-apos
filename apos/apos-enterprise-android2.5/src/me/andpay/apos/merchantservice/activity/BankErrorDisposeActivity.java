package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;
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
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.BringAndBackOrderController;
import me.andpay.apos.merchantservice.data.BringAndBackOrder;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.cache.HashMap;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

/*银联差错处理*/
@ContentView(R.layout.bank_error_dispose)
public class BankErrorDisposeActivity extends AposBaseActivity implements
		AdpterEventListener, FinishRequestInterface, OnClickListener {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "apply", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_return_apply)
	private TextView apply;

	
	private TextView report;

	@EventDelegate(type = DelegateType.method, toMethod = "add", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_add)
    TextView add;

	@InjectView(R.id.bank_error_dispose_listview)
	private ListView listView;

	private BaseAdapter<BringAndBackOrder> applyAdapter;
	private BaseAdapter<BringAndBackOrder> bringAdapter;

	private BringAndBackOrderController backOrderController;
	private BringAndBackOrderController bringOrderController;

	/**
	 * 状态 0退货订单 1调单订单
	 */
	private int currentState = 0;

	/**
	 * 链接失败
	 */

	private View faile;

	/**
	 * 数据为空
	 */

	private View empty;

	/**
	 * 刷新
	 */

	private Button refreshBtn1;
	private Button refreshBtn2;

	@Inject
	RequestManager requestManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		txnDialog = new CommonDialog(this, "读取中...");
		faile = findViewById(R.id.bank_error_dispose_faile);
		empty = findViewById(R.id.bank_error_dispose_empty);
		refreshBtn1 = (Button) faile.findViewById(R.id.refresh_btn);
		refreshBtn2 = (Button) empty.findViewById(R.id.refresh_btn);
		
		refreshBtn1.setOnClickListener(this);
		refreshBtn2.setOnClickListener(this);
		
		apply = (TextView)findViewById(R.id.bank_error_dispose_return_apply);
		report = (TextView)findViewById(R.id.bank_error_dispose_order_report);
		apply.setOnClickListener(this);
		report.setOnClickListener(this);

	

		applyAdapter = new BaseAdapter<BringAndBackOrder>();
		applyAdapter.setContext(this);
		backOrderController = new BringAndBackOrderController();
		applyAdapter.setController(backOrderController);

		applyAdapter.setAdpterEventListener(this);

		bringAdapter = new BaseAdapter<BringAndBackOrder>();
		bringAdapter.setContext(this);
		bringOrderController = new BringAndBackOrderController();
		bringAdapter.setController(bringOrderController);

		bringAdapter.setAdpterEventListener(this);
		currentState = 0;
		apply(null);

	}

	/**
	 * 刷新数据
	 * 
	 * @param view
	 */
	public void refreshData() {

		switch (currentState) {
		case 0:
			applyPage = 1;
			txnDialog.show();
			getOrders(pageSize, applyPage, "OSS-ERROR-R");
			break;

		case 1:
			reportPage = 1;
			txnDialog.show();
			getOrders(pageSize, reportPage, "OSS-ERROR-A");
			break;
		}

	}

	/*
	 * 获得退单申请，调单列表
	 */
	private int pageSize = 10;
	private int applyPage = 1;
	private int reportPage = 1;
	private CommonDialog txnDialog;

	private void getOrders(int pageSize, int page, String queryType) {

		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		Map<String, Object> dataMap = new HashMap();
		dataMap.put("action", queryType);
		optRequest.setVasRequestContentObj(dataMap);
		optRequest.setUserName("13838380439");
		optRequest.setPageSize(pageSize);
		optRequest.setCurPageNo(page);
		optRequest.setOperateType(VasOptTypes.OSS_ERROR_HANDLE_LIST_QUERY);

		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);
		requestManager.startService();

	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/* 申请 */

	@SuppressLint("NewApi")
	public void apply(View view) {
		currentState = 0;
		apply.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		apply.setTextColor(getResources().getColor(android.R.color.white));
		report.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		report.setTextColor(getResources()
				.getColor(android.R.color.darker_gray));
		listView.setAdapter(applyAdapter);
		if (applyAdapter.getList().size() <= 0) {
			txnDialog.show();
			getOrders(pageSize, applyPage = 1, "OSS-ERROR-R");
		}

	}

	/* 订单上报 */
	@SuppressLint("NewApi")
	public void report(View view) {
		currentState = 1;
		report.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		report.setTextColor(getResources().getColor(android.R.color.white));
		apply.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		apply.setTextColor(getResources().getColor(android.R.color.darker_gray));
		listView.setAdapter(bringAdapter);
		if (bringAdapter.getList().size() <= 0) {
			txnDialog.show();
			getOrders(pageSize, reportPage = 1, "OSS-ERROR-A");
		}
	}

	/* 添加退单 */
	public void add(View view) {
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowNote.ADD_BACK_ORDER);

	}

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		BringAndBackOrder order = (BringAndBackOrder) objects[1];
		TiFlowControlImpl.instanceControl().getFlowContext()
				.put(BringAndBackOrder.class.getName(), order);
		TiFlowControlImpl.instanceControl().nextSetup(
				this,
				currentState == 0 ? FlowNote.BACK_ORDER_DETAIL
						: FlowNote.REPORT_ORDER_DETAIL);
		return false;
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null
				&& (currentState == 0 ? applyPage : reportPage) == 1) {
			/* 异常 */
			selectShowState(-1);

		} else {
			CommonTermOptResponse optResonpse = (CommonTermOptResponse) response;
			if (!optResonpse.isSuccess()) {// 不成功
				selectShowState(-1);
			} else {
				String jsonStr = (String) optResonpse
						.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
				if (jsonStr == null
						&& (currentState == 0 ? applyPage : reportPage) == 1) {
					selectShowState(0);
				} else if (jsonStr == null) {
					selectShowState(1);
				} else {
					selectShowState(1);
					ArrayList<BringAndBackOrder> dataList = BringAndBackOrder
							.getArrays(jsonStr);
					switch (currentState) {
					case 0:
						applyAdapter.getList().addAll(dataList);
						applyAdapter.notifyDataSetChanged();
						break;

					case 1:
						bringAdapter.getList().addAll(dataList);
						bringAdapter.notifyDataSetChanged();
						break;
					}
				}
			}

		}
		// 3.4退货清单
		// {"pageSize":5,"currentPage":1,"totalPageCount":1,"totalRowCount":3,"partyId":"1017911000000001","errorType":"OSS-ERROR-R","errorHandleList":[{"subject":"标题1","createTime":"20141022161828","description":"描述1","dispose":"0","imagePaths":["image1","image2","image3"],"id":5},{"subject":"标题1","createTime":"20141022161511","description":"描述1","dispose":"0","imagePaths":["image1","image2","image3"],"id":4},{"subject":"标题1","createTime":"20141022161324","description":"描述1","dispose":"0","imagePaths":["image1","image2","image3"],"id":3}]}

		// 3.4调单清单{"pageSize":5,"currentPage":1,"totalPageCount":1,"totalRowCount":1,"partyId":"1017911000000001","errorType":"OSS-ERROR-A","errorHandleList":[{"subject":"调单1","createTime":"20141023005719","description":"调单1调单1","dispose":"0","imagePaths":[],"id":6}]}
		// 基本信息
		// {"merchantName":"测试","name":"杭赣修","idCard":"220183197904228470","adress":"湖南省长沙市芙蓉区解放西路","phoneNumber":"13838380439"}
		// 通知{"pageSize":5,"currentPage":1,"totalPageCount":1,"totalRowCount":1,"userPartyId":"17","annoType":"OSS-ANNO-ACT","announcementList":[{"id":12,"author":"17","subject":"测试1","content":"测试1测试1测试1","annoType":"OSS-ANNO-ACT","order":0,"startTime":"20141022021138","endTime":"20141031021138","receiver":"PG:17","status":"1","createTime":"20141023021138"}]}
	}

	/**
	 * 
	 * @param state
	 *            0空数据状态 1有数据 -1错误
	 */
	private void selectShowState(int state) {
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.refresh_btn) {
			
			refreshData();
		}else if(v.getId() == R.id.bank_error_dispose_return_apply){
			apply(null);
			
		}else if(v.getId() == R.id.bank_error_dispose_order_report){
			report(null);
		}
	}
}
