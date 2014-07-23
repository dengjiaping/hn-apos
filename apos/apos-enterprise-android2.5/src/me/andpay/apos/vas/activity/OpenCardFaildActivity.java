package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.event.OpenCardFaildControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


@ContentView(R.layout.vas_opencard_faild_layout)
public class OpenCardFaildActivity extends AposBaseActivity {
	
	
	@InjectView(R.id.vas_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenCardFaildControl.class)
	public Button backBtn;
	
	@InjectView(R.id.vas_msg_content)
	public TextView msgContent;
	
	@InjectView(R.id.vas_out_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenCardFaildControl.class)
	public Button outBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResumeProcess() {
		String errorMessage  = (String)getIntent().getExtras().get("errorMessage");
		msgContent.setText(errorMessage);
	}
}
