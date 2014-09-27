package me.andpay.apos.tcm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.TiTimeButton;
import me.andpay.apos.cmview.TiTimeButton.OnTimeoutListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tcm.event.CheckVerificationCodeEventController;
import me.andpay.apos.tcm.event.QuitChallengeEventController;
import me.andpay.apos.tcm.event.RequestVerificationCodeEventController;
import me.andpay.apos.tcm.event.SubmitVerificationCodeEventController;
import me.andpay.apos.tcm.flow.ChallengeContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author David.zhang
 * 
 */

@ContentView(R.layout.tcm_smschallenge_layout)
public class SmsChallengeActivity extends AposBaseActivity {

	public static final String TIME_SUFFIX = "$TIME$";

	@InjectView(R.id.tcm_smschallenge_info_txtView)
	public TextView explanationTextView;

	@InjectView(R.id.tam_smschallenge_quit_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = QuitChallengeEventController.class)
	public Button quitButton;

	@InjectView(R.id.tcm_smschallenge_next_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SubmitVerificationCodeEventController.class)
	public Button nextButton;

	@InjectView(R.id.tcm_smschallenge_request_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = RequestVerificationCodeEventController.class)
	public TiTimeButton codeButton;

	@InjectView(R.id.tcm_smschallenge_input_edit)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CheckVerificationCodeEventController.class)
	public EditText inputEditText;

	private static final String INITIAL_NUMBER = "$PHONE$";
	private static final int REQUEST_LIMIT = 3;
	private int requestCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestCount = 0;
		ChallengeContext challengeContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(ChallengeContext.class);
		String phoneNumber = challengeContext.getPhoneNumber();
		phoneNumber = hidePhoneNumber(phoneNumber);
		highlightPhoneNumber(phoneNumber);

		codeButton.setOnTimeoutListener(new OnTimeoutListener() {
			public void onTimeout() {
				if (SmsChallengeActivity.this.isFinishing()) {
					return;
				}
				requestCount++;
				if (requestCount <= REQUEST_LIMIT)
					codeButton.setEnabled(true);
				codeButton.setText(R.string.tcm_smschallenge_request_str);
			}
		});
		StringBuffer buffer = new StringBuffer();
		buffer.append(codeButton.getText().toString().trim());
		buffer.append(TIME_SUFFIX);
		codeButton.setText(buffer.toString());
		codeButton.startTimer(60);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void highlightPhoneNumber(String phoneNumber) {
		String explanation = explanationTextView.getText().toString();
		explanation = explanation.replace(INITIAL_NUMBER, phoneNumber);
		SpannableStringBuilder style = new SpannableStringBuilder(explanation);
		style.setSpan(new ForegroundColorSpan(Color.parseColor("#2e86ca")), 22,
				33, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		explanationTextView.setText(style);
	}

	private String hidePhoneNumber(String phoneNumber) {
		String hideString = "****";
		return phoneNumber.substring(0, 3) + hideString
				+ phoneNumber.substring(7);
	}

}
