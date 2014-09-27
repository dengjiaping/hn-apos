package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.lam.form.ChangePasswordForm;
import me.andpay.apos.scm.event.PwdSureButtonClickController;
import me.andpay.apos.scm.event.PwdTextChangeEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@FormBind(formBean = ChangePasswordForm.class)
@ContentView(R.layout.scm_changepwd_layout)
public class ScmChangePwdActivity extends AposBaseActivity implements
		ValueContainer {

	@InjectView(R.id.lam_old_password_edit)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = PwdTextChangeEventController.class)
	EditText oldPwd;

	@InjectView(R.id.lam_new_password_edit)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = PwdTextChangeEventController.class)
	EditText newPwd;

	@InjectView(R.id.lam_repeat_password_edit)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = PwdTextChangeEventController.class)
	EditText repeatPwd;

	@InjectView(R.id.scm_pwd_sure_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PwdSureButtonClickController.class, isNeedFormBean = true)
	Button sureButton;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldPwd.requestFocus();
	}

	public Button getSureButton() {
		return sureButton;

	}

	public EditText getOldPwd() {
		return oldPwd;
	}

	public EditText getNewPwd() {
		return newPwd;
	}

	public EditText getRepeatPwd() {
		return repeatPwd;
	}
}
