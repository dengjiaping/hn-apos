package me.andpay.apos.trm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.trm.event.ReundSuccessControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.tam_txn_success_layout)
public class RefundSuccessActivity  extends TiActivity{
	
	
	@InjectView(R.id.com_msg_content)
	public TextView msgContent;
	
	@InjectView(R.id.com_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ReundSuccessControl.class)
	public Button returnHomeBtn;
	
	private String message;
	
	private String buttonName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		message = ResourceUtil.getIntentStr(getIntent(), "message");
		buttonName = ResourceUtil.getIntentStr(getIntent(), "buttonName");

		msgContent.setText(message);
		returnHomeBtn.setText(buttonName);
	}
}
