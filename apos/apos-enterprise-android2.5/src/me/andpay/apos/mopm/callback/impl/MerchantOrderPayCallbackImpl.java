package me.andpay.apos.mopm.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ConfigAttrValues;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.apos.mopm.activity.MerchantOrderRealPayActivity;
import me.andpay.apos.mopm.callback.MerchantOrderPayCallback;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class MerchantOrderPayCallbackImpl implements LoginCallback,
		MerchantOrderPayCallback {

	private MerchantOrderRealPayActivity activity;

	public MerchantOrderPayCallbackImpl(MerchantOrderRealPayActivity activity) {
		this.activity = activity;
	}

	public void loginSuccess(LoginResponse response) {
		String onceInstall = (String) activity.getAppConfig().getAttribute(
				ConfigAttrNames.ONCE_INSTALL_USE);

		// 是否安装
		if (!ConfigAttrValues.ONCE_INSTALL_USE_VALUE.equals(onceInstall)) {
			activity.getAppConfig().setAttribute(
					ConfigAttrNames.ONCE_INSTALL_USE,
					ConfigAttrValues.ONCE_INSTALL_USE_VALUE);
		}
	}

	public void loginFaild(String errorMsg) {
		cancelDialog();
		// Intent intent = new Intent();
		// intent.setAction(LamProvider.LAM_LOGIN_ACTIVITY);
		// intent.putExtra("errorMsg", errorMsg);
		// activity.startActivity(intent);
		// activity.finish();

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", errorMsg);
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.FAILED, intentData);
	}

	public void updateApp(String errorCode) {

	}

	public void goActivateCert() {
		cancelDialog();
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.FAILED_SEPT1);
	}

	/**
	 * 订单检验成功
	 */
	public void checkOrderSuccess() {
		cancelDialog();
		activity.checkAndLoginOnBackgroud();
		OrderPayUtil.submitTxn(activity, activity.txnControl);
	}

	public void checkOrderFaild(String errorMsg) {
		cancelDialog();
		final PromptDialog dialog = new PromptDialog(activity, "验证失败", errorMsg);
		dialog.setCancelable(false);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				OrderPayUtil.failBackApp(activity);
			}
		});
		dialog.show();
	}

	public void networkError(String errorMsg) {
		cancelDialog();
		final OperationDialog dialog = new OperationDialog(activity, "网络异常",
				errorMsg);
		dialog.setCancelable(false);
		dialog.setCancelButtonName("返回");
		dialog.setSureButtonName("重试");

		dialog.setCancelButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				OrderPayUtil.failBackApp(activity);
			}
		});

		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				OrderPayContext orderPayContext = OrderPayUtil
						.getOrderPayContext();
				activity.submitRequest(orderPayContext.getOrderPayRequest());
			}
		});
		dialog.show();

	}

	private void cancelDialog() {
		activity.checkDialog.cancel();
	}

}
