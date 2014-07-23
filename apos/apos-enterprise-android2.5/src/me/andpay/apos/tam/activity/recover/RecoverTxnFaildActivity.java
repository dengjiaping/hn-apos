package me.andpay.apos.tam.activity.recover;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.event.RecoverTxnFaildEventControl;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


@ContentView(R.layout.tam_recover_txn_faild_layout)
public class RecoverTxnFaildActivity extends TiActivity {
	
	
	
	@InjectView(R.id.com_title_tv)
	public TextView topTitle;
	

	
	@InjectView(R.id.com_msg_content)
	public TextView msgContent;
	
	@InjectView(R.id.com_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = RecoverTxnFaildEventControl.class)
	public Button retryBtn;

	
	private String errorMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		errorMsg = ResourceUtil.getIntentStr(getIntent(), "errorMsg");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!StringUtil.isBlank(errorMsg)) {
			msgContent.setText(errorMsg);
		}
	}
}
