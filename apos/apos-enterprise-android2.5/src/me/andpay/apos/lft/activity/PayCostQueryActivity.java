package me.andpay.apos.lft.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.lft.flow.PayCostType;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 缴费查询
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_paycost_query)
public class PayCostQueryActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_lft_paycost_query_back)
	private ImageView back;

	@InjectView(R.id.lft_paycost_query_unit)
	private TextView unit;// 单位

	@InjectView(R.id.lft_paycost_query_id)
	private TextView serialNumber;// 编号

	@InjectView(R.id.lft_paycost_query_money)
	private TextView money;// 应缴金额

	@InjectView(R.id.lft_paycost_query_true_money)
	private TextView trueMoney;// 实缴金额

	@InjectView(R.id.lft_paycost_query_username)
	private TextView userName;// 用户姓名

	@InjectView(R.id.lft_paycost_query_sure)
	private Button sure;// 确认缴费

	@EventDelegate(type = DelegateType.method, toMethod = "deatail", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_paycost_query_deatail)
	private TextView deatail;// 详情

	@InjectExtra("unit")
	private String unitStr;// 单位

	@InjectExtra("serialNumber")
	private String serialNumberStr;// 单号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		unit.setText(unitStr);
		serialNumber.setText(serialNumberStr);
	}

	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public void deatail(View v) {
		PayCostType type = (PayCostType) TiFlowControlImpl.instanceControl()
				.getFlowContext().get("type");
		if (type == PayCostType.ELECTRICITY) {// 电费详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.ELE_DEATAIL);
		} else if (type == PayCostType.WATER) {// 水费详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.WATER_DEATAIL);
		}
	}

}
