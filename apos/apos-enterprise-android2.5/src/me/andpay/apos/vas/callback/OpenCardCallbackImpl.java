package me.andpay.apos.vas.callback;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.OpenCardRegisterActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class OpenCardCallbackImpl implements OpenCardCallback {

	private OpenCardRegisterActivity openCardRegisterActivity;

	public OpenCardCallbackImpl(
			OpenCardRegisterActivity openCardRegisterActivity) {
		this.openCardRegisterActivity = openCardRegisterActivity;
	}

	public void netWorkError() {
		openCardRegisterActivity.openCardDialog.cancel();

		final PromptDialog dialog = new PromptDialog(
				openCardRegisterActivity, "提示", "网络异常，请稍后重试,在网络恢复前请不要退出此页面。");
		dialog.setCancelable(false);

		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				openCardRegisterActivity.openCardSubmit(null);
			}
		});
		dialog.show();
		dialog.setButtonText("重 试");

	}

	public void openCardSuccess() {
		openCardRegisterActivity.openCardDialog.cancel();
		TiFlowControlImpl.instanceControl().nextSetup(openCardRegisterActivity,
				FlowConstants.SUCCESS);

	}

	public void openCardFaild(String errorMsg) {
		openCardRegisterActivity.openCardDialog.cancel();
		Map<String,String> intentData = new HashMap<String,String>();
		intentData.put("errorMessage", errorMsg);
		TiFlowControlImpl.instanceControl().nextSetup(openCardRegisterActivity, FlowConstants.FAILED,intentData);
	}

}
