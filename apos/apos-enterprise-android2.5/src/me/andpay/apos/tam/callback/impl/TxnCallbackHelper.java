package me.andpay.apos.tam.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.RoboGuiceUtil;

public class TxnCallbackHelper {

	public static void convertResponse(TxnActionResponse reActionResponse) {
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		txnContext.setTxnActionResponse(reActionResponse);
		txnContext.setPinErrorCount(reActionResponse.getPinErrorCount());
		if (reActionResponse.getGoodsFileURL() != null) {
			txnContext.setGoodsFileURL(reActionResponse.getGoodsFileURL());
		}
	}

	public static void txnSuccess(TiActivity tiAcitivty,
			TxnActionResponse actionResponse) {
		ExceptionPayTxnInfoService exceptionPayTxnInfoService = RoboGuiceUtil
				.getInjectObject(ExceptionPayTxnInfoService.class, tiAcitivty);
		TxnResponse txnResponse = actionResponse.getTxnResponse();

		if (txnResponse.isSignTxnFlag()) {

			exceptionPayTxnInfoService.changeExceptionStatus(
					actionResponse.getTermTraceNo(),
					actionResponse.getTermTxnTime(),
					ExceptionPayTxnInfo.EXP_STATUS_WAIT_SIGN);

			TiFlowControlImpl.instanceControl().nextSetup(tiAcitivty,
					me.andpay.apos.common.flow.FlowConstants.SUCCESS);
		} else {
			exceptionPayTxnInfoService.removeExceptionTxn(
					actionResponse.getTermTraceNo(),
					actionResponse.getTermTxnTime());
			// 无签名
			TiFlowControlImpl.instanceControl().nextSetup(tiAcitivty,
					me.andpay.apos.common.flow.FlowConstants.SUCCESS_STEP2);
		}
	}

	public static void clearAc(TxnControl txnControl) {
		if (txnControl != null && txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
	}

	public static void txnFailed(TxnContext txnContext,
			TxnActionResponse txnActionResponse, TiActivity tiAcitivty) {
		
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", txnActionResponse.getResponMsg());

		if (ExtTypes.EXT_TYPE_TXN_GET.equals(txnContext.getExtType())) {
			intentData.put("buttonName", "重新获取交易");
		} else {
			intentData.put("buttonName", "重新刷卡");

		}

		TiFlowControlImpl.instanceControl().nextSetup(tiAcitivty,
				me.andpay.apos.common.flow.FlowConstants.FAILED, intentData);

	}
}
