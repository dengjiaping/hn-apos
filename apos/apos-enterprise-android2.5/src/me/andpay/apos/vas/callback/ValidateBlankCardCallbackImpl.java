package me.andpay.apos.vas.callback;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.OpenCardRegisterMainActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;


@CallBackHandler
public class ValidateBlankCardCallbackImpl implements ValidateBlankCardCallback {


	private OpenCardRegisterMainActivity openCardRegisterMainActivity;
	
	public ValidateBlankCardCallbackImpl(
			OpenCardRegisterMainActivity openCardRegisterMainActivity) {
		this.openCardRegisterMainActivity = openCardRegisterMainActivity;
	}

	public void netWorkError() {
		clear();
		
		final PromptDialog dialog = new PromptDialog(
				openCardRegisterMainActivity, "提示", "网络异常，请稍后重试,在网络恢复前请不要退出此页面。");
		dialog.setCancelable(false);

		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				openCardRegisterMainActivity.oprCardRegisterMainControl.submit(openCardRegisterMainActivity);
			}
		});
		dialog.show();
		dialog.setButtonText("重 试");
		
	}

	public void validateSuccess() {
		clear();
		TiFlowControlImpl.instanceControl().nextSetup(openCardRegisterMainActivity, FlowConstants.SUCCESS);
	}
	
	public void validateFaild(String errorMsg) {
		clear();
		Map<String,String> intentData = new HashMap<String,String>();
		intentData.put("errorMessage", errorMsg);
		TiFlowControlImpl.instanceControl().nextSetup(openCardRegisterMainActivity, FlowConstants.FAILED,intentData);
	}
	
	public void clear() {
		openCardRegisterMainActivity.diCommonDialog.cancel();
	}

}
