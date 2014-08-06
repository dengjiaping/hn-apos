package me.andpay.apos.message.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.message.data.Message;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 通知详情
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.msg_message_deatail)
public class MessageDeatailActivity extends AposBaseActivity {
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
	@InjectView(R.id.msg_message_deatail_back)
	private ImageView back;//返回
	
	@InjectView(R.id.msg_message_deatail_content)
	private TextView content;//内容
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Message message=(Message)TiFlowControlImpl.instanceControl().getFlowContext().get(Message.class.getName());
		if(message!=null){
			content.setText(message.getContent());
		}
	}
	public void back(View v){
		
		
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}
}
