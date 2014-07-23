package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.scm.event.FeedBackSendButtonController;
import me.andpay.apos.scm.event.FeedBackTextWatcher;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@ContentView(R.layout.scm_feedback_layout)
public class ScmFeedbackActivity extends AposBaseActivity {

	@InjectView(R.id.scm_feedback_et)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = FeedBackTextWatcher.class)
	EditText text;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	@InjectView(R.id.com_send_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = FeedBackSendButtonController.class)
	Button sendButton;
	
	@InjectResource(R.string.scm_feedback_hint_str)
	String errorMsg;

	@InjectResource(R.string.com_progress_prompt_str)
	String operationMsg;

	@InjectResource(R.string.scm_feedback_error_str)
	String errorSendMsg;

	@InjectResource(R.string.scm_feedback_succ_str)
	String succSendMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public Button getSendButton() {
		return sendButton;
	}

	public EditText getText() {
		return text;
	}

	public String getErrorSendMsg() {
		return errorSendMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getOperationMsg() {
		return operationMsg;
	}

	public String getSuccSendMsg() {
		return succSendMsg;
	}
}
