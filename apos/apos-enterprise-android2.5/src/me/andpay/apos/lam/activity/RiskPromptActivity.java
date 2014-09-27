package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.scm.event.PhoneBtnClickController;
import me.andpay.apos.scm.event.SureUseClickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

@ContentView(R.layout.lam_risk_prompt_layout)
public class RiskPromptActivity extends TiActivity {

	@InjectView(R.id.lam_call_phone_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PhoneBtnClickController.class)
	public Button callPhoneBtn;

	@InjectView(R.id.lam_sure_use_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SureUseClickController.class)
	public Button sureBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
}
