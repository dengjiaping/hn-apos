package me.andpay.apos.tam.event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.tam.activity.RepostVoucherActivity;
import me.andpay.apos.tam.callback.impl.RepostVoucherCallbackImpl;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.tam.form.PostVoucherForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class RepostVcEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		RepostVoucherActivity repostActivity = (RepostVoucherActivity) activity;
		PostVoucherForm postForm = (PostVoucherForm) formBean.getData();

		if (view.getId() == R.id.tam_repost_send_btn) {
			String phone = repostActivity.phoneEditText.getText().toString();
			if (phone.indexOf("*******") >= 0) {
				phone = repostActivity.realPhone;
				postForm.setPhone(phone);
			} else {
				boolean isPhone = validatePhoneNo(phone);
				if (!isPhone) {
					showErrorMsg(repostActivity, "手机号码格式不正确，请重新输入。");
					return;
				}
				postForm.setPhone(phone);
			}
			
		}
		postForm.setTxnId(TiFlowControlImpl.instanceControl()
				.getFlowContextData(PostVoucherContext.class).getTxnId());
		EventRequest request = this.generateSubmitRequest(activity);
		request.open(formBean,
				me.andpay.timobileframework.mvc.EventRequest.Pattern.async);
		repostActivity.postDialog = new CommonDialog(activity, "凭证发送中...");
		repostActivity.postDialog.show();
		request.callBack(new RepostVoucherCallbackImpl((repostActivity)));
		request.submit();
	}

	private boolean validatePhoneNo(String value) {
		if (value == null || StringUtil.isEmpty(value.toString())) {
			return false;
		}
		Pattern pattern = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(value.toString());
		return matcher.find();
	}

	private void showErrorMsg(RepostVoucherActivity activity, String error) {
		PromptDialog dialog = new PromptDialog(activity, null, error);
		View view = activity.findViewById(activity.phoneEditText.getId());
		view.requestFocus();
		dialog.show();
	}

}
