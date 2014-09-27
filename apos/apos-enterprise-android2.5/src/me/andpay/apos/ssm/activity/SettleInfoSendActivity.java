package me.andpay.apos.ssm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.ssm.event.BackToHomeController;
import me.andpay.apos.ssm.event.SettleInfoSendController;
import me.andpay.apos.ssm.event.TextWatcherController;
import me.andpay.apos.ssm.form.SendForm;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@ContentView(R.layout.ssm_send_layout)
@FormBind(formBean = SendForm.class)
public class SettleInfoSendActivity extends AposBaseActivity implements
		ValueContainer {

	@InjectView(R.id.ssm_batch_send_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SettleInfoSendController.class, isNeedFormBean = true)
	public Button ssm_batch_send_btn;

	// @InjectView(R.id.com_processing_layout)
	// private View ssm_batch_sending_info_layout;

	@InjectView(R.id.ssm_batch_send_info_layout)
	private View ssm_batch_send_info_layout;

	private Long batchId;

	@InjectResource(R.string.ssm_empty_error_str)
	private String ssm_empty_error_str;

	@InjectResource(R.string.ssm_send_succ_str)
	private String succStr;

	@InjectView(R.id.com_home_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackToHomeController.class, isNeedFormBean = true)
	private ImageView homeBtn;

	@InjectView(R.id.ssm_batch_send_phone_tv)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = TextWatcherController.class, isNeedFormBean = true)
	public EditText phoneEditText;

	@InjectView(R.id.ssm_batch_send_mail_tv)
	@EventDelegate(delegateClass = TextWatcher.class, delegate = "addTextChangedListener", toEventController = TextWatcherController.class, isNeedFormBean = true)
	public EditText mailEditText;

	public CommonDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		batchId = getIntent().getLongExtra("batchId", 0l);

	}

	public Long getBatchId() {
		return this.batchId;
	}

	//
	// public View getSsm_batch_sending_info_layout() {
	// return ssm_batch_sending_info_layout;
	// }

	public View getSsm_batch_send_info_layout() {
		return ssm_batch_send_info_layout;
	}

	public String getSsm_empty_error_str() {
		return ssm_empty_error_str;
	}

	public String getSuccStr() {
		return succStr;
	}

}
