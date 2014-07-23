package me.andpay.apos.tam.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.tam.activity.recover.RecoverTxnFaildActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class RecoverTxnFaildEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	@Inject
	private ExceptionPayTxnInfoService exceptionPayTxnInfoService;

	public void onClick(Activity activity, FormBean formBean, View view) {

		RecoverTxnFaildActivity faildActivity = (RecoverTxnFaildActivity) activity;
		TxnContext txnContext = txnControl.getTxnContext();
		if (faildActivity.retryBtn.getId() == view.getId()) {
			exceptionPayTxnInfoService.removeExceptionTxn(txnContext
					.getTxnActionResponse().getTermTraceNo(), txnContext
					.getTxnActionResponse().getTermTxnTime());
			TiFlowControlImpl.instanceControl().nextSetup(faildActivity,
					FlowConstants.FINISH);
		}

	}
}