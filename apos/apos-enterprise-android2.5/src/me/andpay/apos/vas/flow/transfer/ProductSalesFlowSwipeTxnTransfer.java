package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ShoppingCart;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import android.app.Activity;

public class ProductSalesFlowSwipeTxnTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		// 设置支付方式
		TiFlowControlImpl.instanceControl()
				.getFlowContextData(ProductSalesContext.class)
				.setPaymeneMethed(PaymentMethods.SWIPING);

		TiActivity tiActivity = (TiActivity) activity;
		TxnControl txnControl = RoboGuiceUtil.getInjectObject(TxnControl.class,
				activity.getApplicationContext());

		ProductSalesContext productSalesContext = TiFlowControlImpl
				.instanceControl()
				.getFlowContextData(ProductSalesContext.class);

		TxnContext txnContext = new TxnContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);

		ShoppingCart shoppingCart = productSalesContext.getShoppingCart();

		txnControl.init();
		txnControl.setCurrActivity(tiActivity);
		txnControl.setTxnCallback(new TxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setSignUplaod(true);
		txnContext.setSalesAmt(shoppingCart.getTotalAmt());
		txnContext.setReceiptNo(TxnUtil.getReceipNo(tiActivity.getAppConfig()));
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setRePostFlag(false);
		txnContext.setHasNewTxnButton(false);
		txnContext.setShoppingCart(shoppingCart);
		return null;
	}

}
