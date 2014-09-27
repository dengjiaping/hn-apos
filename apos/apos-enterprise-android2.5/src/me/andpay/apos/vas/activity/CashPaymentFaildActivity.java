package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.event.CashPaymentFaildControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.tam_txn_faild_layout)
public class CashPaymentFaildActivity extends AposBaseActivity {

	@InjectView(R.id.com_title_tv)
	public TextView topTitle;

	@InjectView(R.id.com_out_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CashPaymentFaildControl.class)
	public Button outButton;

	@InjectView(R.id.com_msg_content)
	public TextView msgContent;

	@InjectView(R.id.com_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CashPaymentFaildControl.class)
	public Button retryBtn;

	private String errorMsg;
	private String buttonName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResumeProcess() {
		errorMsg = ResourceUtil.getIntentStr(getIntent(), "errorMsg");
		buttonName = ResourceUtil.getIntentStr(getIntent(), "buttonName");
		retryBtn.setText(buttonName);
		msgContent.setText(errorMsg);
	}
}
