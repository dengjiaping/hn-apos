package me.andpay.apos.common.util;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class BackUtil {

	public static boolean backDialogShow(int keyCode, KeyEvent event, Activity activity) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showBackDialog(activity);
			return false;
		}
		return false;
	}

	public static void showBackDialog(Activity activity) {
		final OperationDialog dialog = new OperationDialog(activity, "提示", "是否确认退出登录",
				true);

		final Activity inActivity = activity;
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				loginOut(inActivity, dialog);
			}
		});

		dialog.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public static void loginOut(Activity activity, OperationDialog dialog) {

		dialog.dismiss();
		//TiApplication application = (TiApplication) activity.getApplication();
		//application.getContextProvider().provider(TiContext.CONTEXT_SCOPE_APPLICATION)
		//		.inValidate();
		Map<String, String> params = new HashMap<String, String>();
		params.put(CommonProvider.COM_STR_RECONN_FLAG, CommonProvider.COM_STR_RECONN_FLAG);
		params.put(CommonProvider.LOGIN_OUT, CommonProvider.LOGIN_OUT);
		TiFlowControlImpl.instanceControl().startFlow(activity, FlowNames.LAM_LOGIN_FLOW,
				params);
		activity.finish();

	}

	public static void loginOut(Activity activity) {
		//TiApplication application = (TiApplication) activity.getApplication();
		//application.getContextProvider().provider(TiContext.CONTEXT_SCOPE_APPLICATION)
		//		.inValidate();
		Map<String, String> params = new HashMap<String, String>();
		params.put(CommonProvider.COM_STR_RECONN_FLAG, CommonProvider.COM_STR_RECONN_FLAG);
		TiFlowControlImpl.instanceControl().startFlow(activity, FlowNames.LAM_LOGIN_FLOW,
				params);
		activity.finish();
	}

}
