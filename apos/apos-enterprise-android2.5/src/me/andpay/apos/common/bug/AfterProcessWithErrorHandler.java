package me.andpay.apos.common.bug;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import me.andpay.timobileframework.bugsense.ThrowableHandler;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.bugsense.ThrowableSense;
import me.andpay.timobileframework.mvc.AfterProcessCallBackHandler;
import me.andpay.timobileframework.mvc.ModelAndView;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 包含网络异常处理callback
 * 
 * @author tinyliu
 * 
 */
public abstract class AfterProcessWithErrorHandler implements
		AfterProcessCallBackHandler, ThrowableHandler {

	public abstract void afterRequest(ModelAndView mv);
	
	public Activity activity;
	
	public AfterProcessWithErrorHandler(Activity activity) {
		this.activity = activity;
	}

	public void processThrowable(ThrowableInfo info) {
		if (ThrowableSense.isAssignThrowable(info.getEx(),
				NetworkErrorException.class)
				|| ThrowableSense.isAssignThrowable(info.getEx(),
						WebSockTimeoutException.class))
			processNetworkError();
		else {
			processOtherError();
		}
	}

	/**
	 * 处理网络异常
	 */
	protected void processNetworkError() {
		final Activity activity = this.activity;
		if(activity.isFinishing()) {
			return;
		}
		OperationDialog dialog = initDialog(ResourceUtil.getString(activity,
				R.string.com_net_error_prompt_str));
		dialog.show();
	}

	/**
	 * 处理业务或者程序逻辑异常
	 */
	protected void processOtherError() {
		final Activity activity = this.activity;
		if(activity.isFinishing()) {
			return;
		}
		OperationDialog dialog = initDialog(ResourceUtil.getString(activity,
				R.string.com_net_error_prompt_str));
		dialog.show();
	}

	protected void refreshAfterNetworkError() {

	}

	protected OperationDialog initDialog(String errorMsg) {
		
		final Activity activity = this.activity;
		
		final OperationDialog dialog = new OperationDialog(activity,
				ResourceUtil.getString(activity, R.string.com_error_title),
				errorMsg, ResourceUtil.getString(activity,
						R.string.com_net_error_button_str),
				ResourceUtil.getString(activity,
						R.string.com_net_error_sure_btn_str), false);
		dialog.setCancelButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				BackUtil.loginOut(activity, dialog);
			}
		});
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				refreshAfterNetworkError();
			}
		});
		dialog.setCancelable(false);
		return dialog;
	}

	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}
