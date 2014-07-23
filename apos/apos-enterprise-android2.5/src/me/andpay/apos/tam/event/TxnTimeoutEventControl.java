package me.andpay.apos.tam.event;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.tam.activity.TxnTimeoutActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

/**
 * 
 * @author cpz
 * 
 */
public class TxnTimeoutEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	@Inject
	private ExceptionPayTxnInfoService exceptionPayTxnInfoService;
	@Inject
	private TxnReversalService txnReversalService;

	public void onClick(Activity activity, FormBean formBean, View view) {

		final TxnTimeoutActivity timeoutActivity = (TxnTimeoutActivity) activity;

		if (timeoutActivity.retryBtn.getId() == view.getId()) {
			timeoutActivity.startRetryTxn("");
			return;
		}

//		if (timeoutActivity.outButton.getId() == view.getId()) {
//			TxnContext txnContext = txnControl.getTxnContext();
//			if (TxnTypes.PURCHASE.endsWith(txnContext.getTxnType())) {
//				purchaseTxnOut(timeoutActivity);
//			} else {
//				refundTxnOut(timeoutActivity);
//			}
//
//			return;
//		}
	}

	public void purchaseTxnOut(final TxnTimeoutActivity timeoutActivity) {
		
		final OperationDialog operationDialog = new OperationDialog(
				timeoutActivity, "提示", "此交易将会在1-10个工作日退还到持卡人账户。");
		operationDialog.setCancelButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				operationDialog.dismiss();
			}
		});

		operationDialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				operationDialog.dismiss();
				TxnContext txnContext = txnControl.getTxnContext();
				// 设置已完成一笔交易
				timeoutActivity.getAppContext().setAttribute(
						RuntimeAttrNames.NEXT_TXN, RuntimeAttrNames.NEXT_TXN);
				
				exceptionPayTxnInfoService.changeExceptionStatus(
						txnContext.getTxnActionResponse().getTermTraceNo(),
						txnContext.getTxnActionResponse().getTermTxnTime(),
						ExceptionPayTxnInfo.EXP_STATUS_WAIT_REVERSE);
				txnReversalService.statrtReversal();

				txnControl.backHomePage(timeoutActivity);
			}
		});
		operationDialog.show();
	}

	public void refundTxnOut(final TxnTimeoutActivity timeoutActivity) {
		final OperationDialog operationDialog = new OperationDialog(
				timeoutActivity, "提示", "此交易状态未明,请联系和付公司4007-288-100。");
		operationDialog.setCancelButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				operationDialog.dismiss();
			}
		});

		operationDialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				operationDialog.dismiss();
				TxnContext txnContext = txnControl.getTxnContext();
				exceptionPayTxnInfoService.removeExceptionTxn(
						txnContext.getTxnActionResponse().getTermTraceNo(),
						txnContext.getTxnActionResponse().getTermTxnTime());
				txnControl.backHomePage(timeoutActivity);
			}
		});
		operationDialog.show();
	}
}
