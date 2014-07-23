package me.andpay.apos.vas.callback;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.activity.OpenCardSuccessActivity;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.widget.Toast;

@CallBackHandler
public class SendEcardCallbackImp implements SendEcardCallback {

	
	private OpenCardSuccessActivity openCardSuccessActivity;
	
	public SendEcardCallbackImp(OpenCardSuccessActivity openCardSuccessActivity) {
		this.openCardSuccessActivity = openCardSuccessActivity;
	}

	public void netWorkError() {
		openCardSuccessActivity.commonDialog.cancel();
		Toast toast = Toast.makeText(openCardSuccessActivity.getApplicationContext(), "网络异常", Toast.LENGTH_LONG);
		toast.show();

	}

	public void sendSuccess() {
		openCardSuccessActivity.commonDialog.cancel();
		Toast toast = Toast.makeText(openCardSuccessActivity.getApplicationContext(), "发送成功！", Toast.LENGTH_LONG);
		toast.show();
		openCardSuccessActivity.resendBtn.setText(ResourceUtil.getString(openCardSuccessActivity.getApplicationContext(), R.string.vas_resend_time_str));
		openCardSuccessActivity.resendBtn.setEnabled(false);
		openCardSuccessActivity.resendBtn.startTimer(20);
	
	}

	public void sendFaild(String errorMsg) {
		openCardSuccessActivity.commonDialog.cancel();
	}

}
