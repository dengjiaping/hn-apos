package me.andpay.apos.merchantservice.activity;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.DescribeController;
import me.andpay.apos.merchantservice.data.Describe;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

/*结算明细*/
@ContentView(R.layout.ms_settlement_deatail)
public class SettleMentDeatailActivity extends AposBaseActivity implements FinishRequestInterface{
	/* 详情视图 */
	@InjectView(R.id.ms_settlement_deatail_listview)
	private ListView listView;

	private BaseAdapter<Describe> adapter;
	@Inject
	DescribeController describeController;

	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_settlement_deatail_back)
	private ImageView back;
	
	@Inject RequestManager requestManager;
	
	@InjectExtra("ssn")
	private String ssn;
	
	@InjectExtra("txnTime")
	private String txnTime;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter = new BaseAdapter<Describe>();
		adapter.setContext(this);
		adapter.setController(describeController);
		listView.setAdapter(adapter);
		getDetail();
	}
	private CommonDialog txnDialog;
	private void getDetail(){
		
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String,Object> parameter = new HashMap<String, Object>();
		parameter.put(VasOptPropNames.UNRPT_SSN,ssn);
		parameter.put(VasOptPropNames.UNRPT_TXN_TIME,txnTime);
		optRequest.setVasRequestContentObj(parameter);
		optRequest.setMerchantNo("888430179110001");
		optRequest.setOperateType(VasOptTypes.SETTLE_REPORT_QUERY_STAT_INFO);
		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);
		txnDialog = new CommonDialog(this, "请求中...");
		txnDialog.show();
		
		requestManager.startService();
		
	}

	String[][] dateStr = new String[][] { { "系统参考号", "104100" },
			{ "系统跟踪号", "系统跟踪号" }, { "终端编号", "终端编号" },
			{ "交易日期时间", "2014-09-50" }, { "主账户", "主账号" }, { "发卡行", "发卡行" },
			{ "交易金额", "交易金额" }, { "商户费用", "商户费用" }, { "结算金额", "结算金额" },
			{ "交易渠道", "交易渠道" }, { "交易类型", "交易类型" }, { "清场次数", "清场次数" }

	};

	

	public void back(View view) {

		TiFlowControlImpl.instanceControl().previousSetup(this);
	}



	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if(txnDialog!=null&&txnDialog.isShowing()){
			txnDialog.cancel();
		}
		CommonTermOptResponse optResponse = (CommonTermOptResponse)response;
		String jsonStr = (String)optResponse.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
		ShowUtil.showLongToast(this, jsonStr);
		
	}

}
