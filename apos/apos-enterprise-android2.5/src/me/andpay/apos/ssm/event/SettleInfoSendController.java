package me.andpay.apos.ssm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.ssm.activity.SettleInfoSendActivity;
import me.andpay.apos.ssm.callback.FinishSendCallBackHandler;
import me.andpay.apos.ssm.form.SendForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SettleInfoSendController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		SettleInfoSendActivity activity = (SettleInfoSendActivity) refActivty;

		SendForm form = (SendForm) formBean.getData();
		if (StringUtil.isEmpty(form.getEmail())
				&& StringUtil.isEmpty(form.getPhone())) {
			showErrorInfo(activity.getSsm_empty_error_str(), refActivty);
			return;
		}

		if (formBean.getErrors().length != 0) {
			showErrorInfo(formBean.getErrors()[0].getErrorDescription(),
					refActivty);
			return;

		}

		form.setBatchId(activity.getBatchId());
		EventRequest request = this.generateSubmitRequest(refActivty);
		request.open(formBean, Pattern.async);
		request.callBack(new FinishSendCallBackHandler(activity));
		activity.progressDialog = new CommonDialog(activity,
				ResourceUtil.getString(activity, R.string.ssm_sending_str));
		activity.progressDialog.show();
		request.submit();

		// activity.getSsm_batch_sending_info_layout().setVisibility(View.VISIBLE);
		// activity.getSsm_batch_send_info_layout().setVisibility(View.GONE);
	}

	private void showErrorInfo(String errormsg, Activity refActivty) {
		PromptDialog dialog = null;
		dialog = new PromptDialog(refActivty, null, errormsg);
		dialog.show();
	}
}
