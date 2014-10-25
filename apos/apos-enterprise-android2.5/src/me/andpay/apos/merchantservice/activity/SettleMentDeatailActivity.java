package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
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
import me.andpay.apos.merchantservice.data.SettlementDetailOrder;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

/*结算明细*/
@ContentView(R.layout.ms_settlement_deatail)
public class SettleMentDeatailActivity extends AposBaseActivity implements
		FinishRequestInterface {
	/* 详情视图 */
	@InjectView(R.id.ms_settlement_deatail_listview)
	private ListView listView;

	private BaseAdapter<Describe> adapter;
	@Inject
	DescribeController describeController;

	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_settlement_deatail_back)
	private ImageView back;

	@Inject
	RequestManager requestManager;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SettlementDetailOrder order = (SettlementDetailOrder) TiFlowControlImpl
				.instanceControl().getFlowContext().get("order");
		adapter = new BaseAdapter<Describe>();
		adapter.setContext(this);
		adapter.setController(describeController);
		listView.setAdapter(adapter);
		Describe d1 = new Describe();
		d1.setTitle("系统参考号");
		d1.setDescribe(order.getRecvOrg());
		Describe d2 = new Describe();
		d2.setTitle("系统跟踪号");
		d2.setDescribe(order.getSsn());
		Describe d3 = new Describe();
		d3.setTitle("终端编号");
		d3.setDescribe(order.getTermNo());
		Describe d4 = new Describe();
		d4.setTitle("交易日期时间");
		d4.setDescribe(order.getTxnTime());
		Describe d5 = new Describe();
		d5.setTitle("主账户");
		d5.setDescribe(order.getAcctNumer());
		Describe d6 = new Describe();
		d6.setTitle("发卡行");
		d6.setDescribe(order.getBank());
		Describe d7 = new Describe();
		d7.setTitle("交易金额");
		d7.setDescribe(order.getTxnAmt());
		Describe d8 = new Describe();
		d8.setTitle("商户费用");
		d8.setDescribe(order.getMchtFee());
		Describe d9 = new Describe();
		d9.setTitle("结算金额");
		d9.setDescribe(order.getOutAmt());
		Describe d10 = new Describe();
		d10.setTitle("交易渠道");
		d10.setDescribe(order.getTxnChnl());
		Describe d11 = new Describe();
		d11.setTitle("交易类型");
		d11.setDescribe(order.getTxnType());

		ArrayList<Describe> list = new ArrayList<Describe>();
		list.add(d1);
		list.add(d2);
		list.add(d3);
		list.add(d4);
		list.add(d5);
		list.add(d6);
		list.add(d7);
		list.add(d8);
		list.add(d9);
		list.add(d10);
		list.add(d11);

		adapter.setList(list);
		adapter.notifyDataSetChanged();

		// getDetail();
	}

	private CommonDialog txnDialog;

	private void getDetail() {

		// CommonTermOptRequest optRequest = new CommonTermOptRequest();
		// HashMap<String,Object> parameter = new HashMap<String, Object>();
		// parameter.put(VasOptPropNames.UNRPT_SSN,ssn);
		// parameter.put(VasOptPropNames.UNRPT_TXN_TIME,txnTime);
		// optRequest.setVasRequestContentObj(parameter);
		// optRequest.setMerchantNo("888430179110001");
		// optRequest.setOperateType(VasOptTypes.SETTLE_REPORT_QUERY_STAT_INFO);
		// requestManager.setOptRequest(optRequest);
		// requestManager.addFinishRequestResponse(this);
		// txnDialog = new CommonDialog(this, "请求中...");
		// txnDialog.show();
		//
		// requestManager.startService();

	}

	String[][] dateStr = new String[][] { { "系统参考号", "104100" },
			{ "系统跟踪号", "系统跟踪号" }, { "终端编号", "终端编号" },
			{ "交易日期时间", "2014-09-50" }, { "主账户", "主账号" }, { "发卡行", "发卡行" },
			{ "交易金额", "交易金额" }, { "商户费用", "商户费用" }, { "结算金额", "结算金额" },
			{ "交易渠道", "交易渠道" }, { "交易类型", "交易类型" }, { "清场次数", "清场次数" }

	};

	public void back(View view) {

		// {"recvOrg":"4825500","mchtNo":"888430179110001","ssn":"546321","txnTime":"20141021145245","termNo":"TC000002","acctNumer":"546321897254","bank":"建设银行","txnAmt":3000000,"mchtFee":30,"outAmt":2999970,"sysNo":"548321546","txnChnl":"银联","txnType":"S21","stlBatch":"1","beginDate":"20141019000000","endDate":"20141019000000","mchtName":"蔡宁有限公司","createDate":"20141020000000"}

		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null) {
			ShowUtil.showShortToast(this,
					getResources().getString(R.string.conection_exception));
			return;
		}
		CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
		String jsonStr = (String) optResponse
				.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
		Log.e("输出", jsonStr);
		ShowUtil.showLongToast(this, jsonStr);

	}

}
