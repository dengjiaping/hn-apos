package me.andpay.apos.merchantservice.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.data.BringAndBackOrder;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		BringAndBackOrder order = (BringAndBackOrder)TiFlowControlImpl.instanceControl().getFlowContext().get(BringAndBackOrder.class.getName());
		title.setText(order.getSubject());
		if(order.getDispose().equals("0")){
			dispose.setTextColor(getResources().getColor(R.color.red));
			dispose.setText("未处理");
		}else{
			dispose.setTextColor(getResources().getColor(R.color.auxiliary));
			dispose.setText("已处理");
		}
		time.setText(order.getCreateTime());
		describle.setText(order.getDescription());
	}
	
	

}
