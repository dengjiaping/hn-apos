package me.andpay.apos.merchantsmaking.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
/**
 * 商户添加提交详情
 * @author Administrator
 *
 */
@ContentView(R.layout.merchants_commit_detail_layout)
public class MerchantsJoinCommitDetailActivity extends AposBaseActivity{
	
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
	@InjectView(R.id.merchants_commit_detail_back)
	private ImageView back;
	
	@EventDelegate(type=DelegateType.method,toMethod="sure",delegateClass=OnClickListener.class)
	@InjectView(R.id.merchants_commit_detail_sure)
	private Button sure;

	public void back(View view){
		TiFlowControlImpl.instanceControl().nextSetup(this,FlowConstants.FINISH);
		
	}
	public void sure(View view){
		TiFlowControlImpl.instanceControl().nextSetup(this,FlowConstants.FINISH);
	}
}
