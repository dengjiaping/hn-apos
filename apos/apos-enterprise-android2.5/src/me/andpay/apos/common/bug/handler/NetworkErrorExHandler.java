package me.andpay.apos.common.bug.handler;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import me.andpay.timobileframework.bugsense.ThrowableHandler;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.bugsense.ThrowableSense;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

/**
 * 接收全局网络异常
 * 
 * 如果网络连接尝试3次仍然失败，提示用户重新登录
 * 
 * @author tinyliu
 * 
 */
public class NetworkErrorExHandler implements ThrowableHandler {

	@Inject
	TiRpcClient tiRpcClient;

	private int maxTryCount = 3;

	private OperationDialog dialog = null;

	public void processThrowable(ThrowableInfo info) {
		if (info.isAlwaysThrows()) {
			return;
		}
		Integer lastErrorCount = 0;
		Throwable ex = ThrowableSense.getAssignThrowable(info.getEx(),
				NetworkErrorException.class);
		final Activity activity = TiAndroidRuntimeInfo.getCurrentActivity();
		if (ex == null) {
			ex = ThrowableSense.getAssignThrowable(info.getEx(),
					WebSockTimeoutException.class);
			lastErrorCount = ((WebSockTimeoutException) ex).getLastErrorCount();
		} else {
			lastErrorCount = ((NetworkErrorException) ex).getLastErrorCount();
		}
		if (lastErrorCount == null
				|| lastErrorCount.intValue() < this.maxTryCount) {
			return;
		}
		if (dialog != null && dialog.getDialog().isShowing()) {
			return;
		}
		dialog = initDialog(activity);
		dialog.show();
	}

	private OperationDialog initDialog(final Activity activity) {
		final OperationDialog dialog = new OperationDialog(
				TiAndroidRuntimeInfo.getCurrentActivity(), null, activity
						.getResources().getString(
								R.string.com_net_error_prompt_str), activity
						.getResources().getString(
								R.string.com_net_error_sure_btn_str), null,
				false);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				BackUtil.loginOut(activity, dialog);
			}
		});
		return dialog;
	}

	public boolean isSupport(Thread thread, Throwable ex) {
		return false;
		/*
		 * return ThrowableSense .isAssignThrowable(ex,
		 * NetworkErrorException.class) || ThrowableSense.isAssignThrowable(ex,
		 * WebSockTimeoutException.class);
		 */
	}

}
