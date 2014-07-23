package me.andpay.apos.tcm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tcm.event.CheckChallengeInfoEventController;
import me.andpay.apos.tcm.event.QuitChallengeEventController;
import me.andpay.apos.tcm.event.ShowCVV2EventController;
import me.andpay.apos.tcm.event.SubmitChallengeInfoEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author David.zhang
 * 
 */

@ContentView(R.layout.tcm_infochallenge_layout)
public class InfoChallengeActivity extends AposBaseActivity {

	@InjectView(R.id.tcm_infochallenge_explanation_txnView)
	public TextView explanationTextView;

	@InjectView(R.id.tcm_infochallenge_quit_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = QuitChallengeEventController.class)
	public Button quitButton;

	@InjectView(R.id.tcm_infochallenge_sure_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SubmitChallengeInfoEventController.class)
	public Button sureButton;

	@InjectView(R.id.tcm_infochallenge_phone_edit)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CheckChallengeInfoEventController.class)
	public EditText phoneEditText;

	@InjectView(R.id.tcm_infochallenge_name_edit)
	public EditText nameEditText;

	@InjectView(R.id.tcm_infochallenge_identity_edit)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CheckChallengeInfoEventController.class)
	public EditText identityEditText;

	@InjectView(R.id.tcm_infochallenge_cvv2_edit)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CheckChallengeInfoEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CheckChallengeInfoEventController.class) })
	public EditText cvv2EditText;

	@InjectView(R.id.tcm_infochallenge_cvv2_img)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowCVV2EventController.class)
	public ImageView cvv2ImageView;

	public CommonDialog postDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		if (intent != null && intent.hasExtra("infoMsg"))
			explanationTextView.setText(intent.getStringExtra("infoMsg"));
		// TODO get explanation info
		// TODO set explanationTextView by explanation info
	}

}
