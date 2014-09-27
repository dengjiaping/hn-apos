package me.andpay.apos.scm.activity;

import java.util.Map;

import me.andpay.ac.term.api.auth.Privileges;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.lam.event.ShowHelpClickControl;
import me.andpay.apos.scm.event.IntentOnclickController;
import me.andpay.apos.scm.event.UpdateClickController;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

@ContentView(R.layout.scm_main_layout)
@TIFLowSignTask
public class ScmMainActivity extends AposBaseActivity {

	@InjectView(R.id.scm_main_pwd_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = IntentOnclickController.class)
	private View pwd_layout;

	@InjectView(R.id.scm_main_set_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = IntentOnclickController.class)
	private View set_layout;

	@InjectView(R.id.scm_main_help_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = ShowHelpClickControl.class)
	private View help_layout;

	@InjectView(R.id.scm_main_update_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = UpdateClickController.class)
	private View update_layout;

	@InjectView(R.id.scm_main_about_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = IntentOnclickController.class)
	private View about_layout;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@InjectView(R.id.scm_party_limit_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = IntentOnclickController.class)
	public View partyLmitView;

	@InjectView(R.id.scm_settle_layout)
	@EventDelegate(delegateClass = OnTouchListener.class, toEventController = IntentOnclickController.class)
	public View settleView;

	@InjectView(R.id.scm_settle_layout)
	public View settleImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PartyInfo partyInfo = (PartyInfo) getAppContext().getAttribute(
				RuntimeAttrNames.PARTY_INFO);
		Map<String, String> privileges = partyInfo.getPrivileges();

		if (privileges.containsKey(Privileges.BATCH_TXN)) {
			settleView.setVisibility(View.VISIBLE);
			settleImageView.setVisibility(View.VISIBLE);
		} else {
			settleView.setVisibility(View.GONE);
			settleImageView.setVisibility(View.GONE);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
