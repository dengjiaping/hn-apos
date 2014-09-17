package me.andpay.apos.merchantservice.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.flow.FlowNames;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/*商户服务*/
@ContentView(R.layout.merchants_service)
public class MerchantsServiceActivity extends AposBaseActivity {
	/* 对账 */
	@EventDelegate(type=DelegateType.method,toMethod="reconciliation",delegateClass=OnClickListener.class)
	@InjectView(R.id.ms_reconciliation_layout)
	private RelativeLayout reconLayout;
	/* 差错处理 */
	@EventDelegate(type=DelegateType.method,toMethod="errorDispose",delegateClass=OnClickListener.class)
	@InjectView(R.id.ms_error_dispose_layout)
	private RelativeLayout errorDisposeLayout;
	/* 信息管理 */
	@EventDelegate(type=DelegateType.method,toMethod="messageManager",delegateClass=OnClickListener.class)
	@InjectView(R.id.ms_message_manager_layout)
	private RelativeLayout messageManagerLayout;
	
	public void reconciliation(View v){
		TiFlowControlImpl.instanceControl().startFlow(this, FlowNames.MS_RECONCILIATION);
	}
	public void errorDispose(View v){
		
	}
	public void messageManager(View v){
		
	}
	
}
