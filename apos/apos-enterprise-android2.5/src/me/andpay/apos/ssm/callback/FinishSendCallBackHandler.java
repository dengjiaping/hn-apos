package me.andpay.apos.ssm.callback;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.ssm.activity.SettleInfoSendActivity;
import me.andpay.timobileframework.mvc.AfterProcessCallBackHandler;
import me.andpay.timobileframework.mvc.ModelAndView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class FinishSendCallBackHandler implements AfterProcessCallBackHandler {

	private SettleInfoSendActivity sendActivity;

	public FinishSendCallBackHandler(SettleInfoSendActivity sendActivity) {
		this.sendActivity = sendActivity;
	}

	public void afterRequest(ModelAndView mv) {
		sendActivity.progressDialog.cancel();
		final PromptDialog dialog = new PromptDialog(sendActivity, null,
				sendActivity.getSuccStr());
		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				Intent homePageIntent = new Intent(
						CommonProvider.COM_HOMEPAGE_ACTIVITY);
				
				sendActivity.startActivity(homePageIntent);
				sendActivity.finish();
			}
		});
		dialog.show();
	}

}
