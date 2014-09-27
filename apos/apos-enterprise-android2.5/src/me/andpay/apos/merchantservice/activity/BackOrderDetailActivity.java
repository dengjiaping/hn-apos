package me.andpay.apos.merchantservice.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 退货申请详情
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.back_order_detail)
public class BackOrderDetailActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.back_order_detail_back)
	private ImageView back;

	@InjectView(R.id.back_order_detail_title)
	private TextView title;

	@InjectView(R.id.back_order_detail_dispose)
	private TextView dispose;

	@InjectView(R.id.back_order_detail_time)
	private TextView time;

	@InjectView(R.id.back_order_detail_describle)
	private TextView describle;

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

}
