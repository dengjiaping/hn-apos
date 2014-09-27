package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.lam.event.ChangePasswordEventControl;
import me.andpay.apos.lam.event.PwdTextWatcherEventControl;
import me.andpay.apos.lam.form.FirstChangePasswordForm;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 修改密码
 * 
 * @author cpz
 *
 */
@ContentView(R.layout.lam_change_password_layout)
@FormBind(formBean = FirstChangePasswordForm.class)
public class ChangePasswordActivity extends TiActivity implements
		ValueContainer {

	@EventDelegateArray({ @EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = ChangePasswordEventControl.class) })
	@InjectView(R.id.lam_changepwd_btn)
	public Button changePassword;

	@InjectView(R.id.lam_old_password_edit)
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = PwdTextWatcherEventControl.class)
	public EditText oldPwd;

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = PwdTextWatcherEventControl.class)
	@InjectView(R.id.lam_new_password_edit)
	public EditText newPassword;

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = PwdTextWatcherEventControl.class)
	@InjectView(R.id.lam_repeat_password_edit)
	public EditText rePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
