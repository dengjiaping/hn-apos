package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.SolfKeyBoardView;
import me.andpay.apos.cmview.SolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.cmview.TiTimeButton;
import me.andpay.apos.cmview.TiTimeButton.OnTimeoutListener;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.apos.lam.callback.impl.ActivateCodeCallbackImpl;
import me.andpay.apos.lam.event.ActivateCodeTextWatcherEventControl;
import me.andpay.apos.lam.event.RetryActivateCodeEventControl;
import me.andpay.apos.lam.event.SubmitActivateCodeEventControl;
import me.andpay.apos.lam.form.ActivateCodeForm;
import me.andpay.apos.mopm.callback.impl.MOPMActivateCodeCallbackImpl;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.tam.event.PasswordEditOnTouchEventControl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.FormProcesser.FormProcessPattern;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.inject.Inject;

@FormBind(formBean = ActivateCodeForm.class)
@ContentView(R.layout.lam_activate_code_layout)
public class ActivateCodeActivity extends TiActivity implements ValueContainer,
		OnKeyboardListener {

	@InjectView(R.id.tam_retry_code_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = RetryActivateCodeEventControl.class)
	public TiTimeButton retryCodeBtn;

	@InjectView(R.id.lam_activate_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = SubmitActivateCodeEventControl.class)
	public Button activateBtn;

	@InjectView(R.id.lam_activate_code_edit)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PasswordEditOnTouchEventControl.class),
			@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = ActivateCodeTextWatcherEventControl.class) })
	public EditText activateCodeText;

	public CommonDialog submitDialog;

	@InjectView(R.id.tam_foot_lay)
	public RelativeLayout footLayout;

	public SolfKeyBoardView solfKeyBoard;

	@Inject
	private FormProcesser formProcesser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		retryCodeBtn.setEnabled(false);

		final Activity activityFinal = this;
		retryCodeBtn.setOnTimeoutListener(new OnTimeoutListener() {
			public void onTimeout() {
				if (activityFinal.isFinishing()) {
					return;
				}
				retryCodeBtn.setEnabled(true);
				retryCodeBtn.setText(getResources().getString(
						R.string.lam_resend_str));
			}
		});
		retryCodeBtn.startTimer(60);

		solfKeyBoard = SolfKeyBoardView.instance(getApplicationContext(),
				footLayout, this);
		solfKeyBoard.getHintImgeBtn().setVisibility(View.GONE);
		solfKeyBoard.setSureButtonFlag(false);
		solfKeyBoard.showKeyboard(activateCodeText);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			BackUtil.showBackDialog(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void sureClick() {
		submitActivateCode();
	}

	public void submitActivateCode() {

		EventRequest request = this.generateSubmitRequest(this);
		FormBean formBean = null;
		try {
			formBean = formProcesser.loadFormBean(this,
					FormProcessPattern.ANDROID);
		} catch (FormException e) {

		}

		request.open(formBean, Pattern.async);
		submitDialog = new CommonDialog(this, this.getResources().getString(
				R.string.lam_activating_str));
		submitDialog.setCancelable(false);
		submitDialog.show();
		submitDialog.setCancelable(false);

		if (OrderPayUtil.isOrderPay(getAppContext())) {
			request.callBack(new MOPMActivateCodeCallbackImpl(this));
		} else {
			request.callBack(new ActivateCodeCallbackImpl(this));
		}
		request.submit();

	}

}
