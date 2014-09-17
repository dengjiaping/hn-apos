package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.apos.vas.spcart.ShoppingCart;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import android.app.Activity;

/**
 * 预付费卡充值流程交易子流程参数初始化
 * 
 * @author tinyliu
 * 
 */
public class SvcDepositeTxnTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity, Map<String, String> data,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext) {

		AposBaseActivity payActivity = (AposBaseActivity) activity;
		String paymentMethod = payActivity.getFlowContextData(SvcDepositeContext.class).getPaymentMethod();
		if (PaymentMethods.CASH.equals(paymentMethod)) {
			configContextByCash(payActivity);
		}
		if (PaymentMethods.SWIPING.equals(paymentMethod)) {
			configContextBySwipe(payActivity);
		}
		return null;
	}

	private void configContextByCash(AposBaseActivity payActivity) {
		SvcDepositeContext dContext = payActivity
				.getFlowContextData(SvcDepositeContext.class);
		dContext.setPaymentMethod(PaymentMethods.CASH);
		CashPaymentContext cashPaymentContext = new CashPaymentContext();
		payActivity.setFlowContextData(cashPaymentContext);
		cashPaymentContext.setShoppingCart(payActivity
				.getFlowContextData(ShoppingCart.class));
	}

	private void configContextBySwipe(AposBaseActivity payActivity) {
		TxnControl txnControl = RoboGuiceUtil.getInjectObject(TxnControl.class,
				payActivity.getApplicationContext());

		TxnContext txnContext = new TxnContext();
		payActivity.setFlowContextData(txnContext);

		ShoppingCart shoppingCart = payActivity.getFlowContextData(ShoppingCart.class);

		txnControl.init();
		txnControl.setCurrActivity(payActivity);
		txnControl.setTxnCallback(new TxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setSignUplaod(true);
		txnContext.setSalesAmt(shoppingCart.getTotalAmt());
		txnContext.setReceiptNo(TxnUtil.getReceipNo(payActivity.getAppConfig()));
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setRePostFlag(false);
		txnContext.setHasNewTxnButton(false);
		txnContext.setShoppingCart(shoppingCart);
	}

}
