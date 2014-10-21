package me.andpay.apos.lft.controller;

import java.math.BigDecimal;

import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.ac.term.api.vas.txn.VasTxnPropNames;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.lft.activity.CreditCardPaymentsActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 信用卡付款事件控制器
 * 
 * @author Administrator
 *
 */
public class CreditCardPaymentController extends AbstractEventController {
	protected VasTxnService service;

	public void onFocusChange(Activity activity, FormBean formBean, View v,
			boolean hasFocus) {
		if (hasFocus) {
			// 此处为得到焦点时的处理内容
		} else {
			// 此处为失去焦点时的处理内容
			CreditCardPaymentsActivity pcActivity = (CreditCardPaymentsActivity) activity;
			String moneStr = pcActivity.money.getText().toString();
			if (StringUtil.isEmpty(moneStr)) {
				return;
			}
			CommonTermTxnRequest request = new CommonTermTxnRequest();
			request.setVasTxnType(VasTxnTypes.CREDIT_CARD_VERIFY);
			BigDecimal amt = new BigDecimal(moneStr);
			request.setAmt(amt);
			CommonTermTxnResponse response = service.processCommonTxn(request);
			if (response.isSuccess()) {
				pcActivity.poundage.setText(response.getTxnResponseContentObj()
						.get(VasTxnPropNames.TXN_FEE_AMT));
			}

		}
	}
}
