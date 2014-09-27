package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import com.crashlytics.android.internal.ad;
import com.google.inject.Inject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.DescribeController;
import me.andpay.apos.merchantservice.data.Describe;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/*结算明细*/
@ContentView(R.layout.ms_settlement_deatail)
public class SettleMentDeatailActivity extends AposBaseActivity {
	/* 详情视图 */
	@InjectView(R.id.ms_settlement_deatail_listview)
	private ListView listView;

	private BaseAdapter<Describe> adapter;
	@Inject
	DescribeController describeController;

	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.ms_settlement_deatail_back)
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter = new BaseAdapter<Describe>();
		adapter.setContext(this);
		adapter.setController(describeController);
		adapter.setList(getList());

		listView.setAdapter(adapter);
	}

	String[][] dateStr = new String[][] { { "系统参考号", "104100" },
			{ "系统跟踪号", "系统跟踪号" }, { "终端编号", "终端编号" },
			{ "交易日期时间", "2014-09-50" }, { "主账户", "主账号" }, { "发卡行", "发卡行" },
			{ "交易金额", "交易金额" }, { "商户费用", "商户费用" }, { "结算金额", "结算金额" },
			{ "交易渠道", "交易渠道" }, { "交易类型", "交易类型" }, { "清场次数", "清场次数" }

	};

	/* 获得数据 */

	private ArrayList<Describe> getList() {
		ArrayList<Describe> list = new ArrayList<Describe>();
		for (int i = 0; i < dateStr.length; i++) {
			Describe dc = new Describe();
			dc.setTitle(dateStr[i][0]);
			dc.setDescribe(dateStr[i][1]);
			list.add(dc);
		}
		return list;

	}

	public void back(View view) {

		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

}
