package me.andpay.apos.ssm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.apos.ssm.activity.SettleMainActivity;
import me.andpay.apos.ssm.callback.FinishedBatchCallBackHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.DialogUtil;
import roboguice.inject.InjectResource;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

public class MainSettleController extends AbstractEventController {

	@InjectResource(R.string.ssm_settle_operation_str)
	private String ssm_settle_operation_str;

	@Inject
	private PayTxnInfoDao dao;

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		final SettleMainActivity activity = (SettleMainActivity) refActivty;
		final OperationDialog dialog = new OperationDialog(activity, null,
				ssm_settle_operation_str);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				EventRequest request = generateSubmitRequest(activity);
				request.open(SsmProvider.SSM_DOMAIN_SETTLE,
						SsmProvider.SSM_ACTION_SETTLE_SETTLE, Pattern.async);
				request.callBack(new FinishedBatchCallBackHandler(activity, dao));
				request.submit();
				dialog.dismiss();
				DialogUtil.setDialogAllowClose(
						activity.getDialog().getDialog(), false);
				activity.getDialog().getDialog().setCancelable(false);
				activity.getDialog().show();
			}
		});
		dialog.show();

	}
}
