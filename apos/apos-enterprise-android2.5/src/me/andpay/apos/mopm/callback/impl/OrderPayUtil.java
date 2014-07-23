package me.andpay.apos.mopm.callback.impl;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.orderpay.OrderPayRequest;
import me.andpay.orderpay.OrderPayResponse;
import me.andpay.orderpay.OrderValueKeyNames;
import me.andpay.orderpay.util.OrderObjectConverter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;

public class OrderPayUtil {

	public static void submitTxn(TiActivity activity, TxnControl txnControl) {

		TxnContext txnContext = txnControl.init();
		txnControl.setTxnCallback(new TxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setSignUplaod(true);
		// 可回主页
		txnContext.setBackTagName(TabNames.TXN_PAGE);
		OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
		OrderPayRequest orderPayRequest = orderPayContext.getOrderPayRequest();

		txnContext.setSalesAmt(orderPayRequest.getAmt());
		txnContext.setExtTraceNo(orderPayRequest.getOrderId());
		txnContext.setTxnType(TxnTypes.PURCHASE);

		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);

	}

	public static OrderPayContext getOrderPayContext() {
		OrderPayContext orderPayContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(OrderPayContext.class);
		return orderPayContext;
	}

	@SuppressLint("InlinedApi")
	public static void failBackApp(final TiActivity activity) {
		OrderPayRequest orderPayRequest = OrderPayUtil.getOrderPayContext()
				.getOrderPayRequest();
		Intent intent = new Intent();
		ComponentName comp = new ComponentName(
				orderPayRequest.getAppCancelUriPackage(),
				orderPayRequest.getAppCancelUriActivityName());
		intent.setComponent(comp);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction("android.intent.action.VIEW");
		intent.putExtra(OrderValueKeyNames.CANCEL_FLAG,
				OrderValueKeyNames.CANCEL_FLAG);
		intent.putExtra(OrderValueKeyNames.CALLBACK_FLAG,
				OrderValueKeyNames.CALLBACK_FLAG);
		clearOrderContext(activity.getAppContext());
		activity.startActivity(intent);
		activity.overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		if (activity.getAppContext().getAttribute(RuntimeAttrNames.PARTY_INFO) != null) {
			Intent homeIntent = new Intent(activity, HomePageActivity.class);
			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(homeIntent);
		} else {
			Intent loginIntent = new Intent(activity, LoginActivity.class);
			loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(loginIntent);
		}
		activity.finish();

		// System.exit(0);

	}

	public static void clearOrderContext(TiContext tiContext) {
		tiContext.removeAttribute(RuntimeAttrNames.CLIENT_ORDER_PAY_FLAG);
	}

	public static void successBackApp(TiActivity activity) {

		OrderPayResponse orderPayResponse = OrderPayUtil.getOrderPayContext()
				.getOrderPayResponse();
		OrderPayRequest orderPayRequest = OrderPayUtil.getOrderPayContext()
				.getOrderPayRequest();
		Intent intent = OrderObjectConverter.responseToIntent(orderPayResponse);
		ComponentName comp = new ComponentName(
				orderPayRequest.getAppTnpUriPackage(),
				orderPayRequest.getAppTnpUriActivityName());
		intent.setComponent(comp);
		intent.putExtra(OrderValueKeyNames.CALLBACK_FLAG,
				OrderValueKeyNames.CALLBACK_FLAG);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		clearOrderContext(activity.getAppContext());
		activity.startActivity(intent);
		Intent homeIntent = new Intent(activity, HomePageActivity.class);
		homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(homeIntent);
		activity.finish();
		// TiFlowControlImpl.instanceControl().nextSetup(activity,
		// FlowConstants.FINISH);
	}

	public static boolean isOrderPay(TiContext context) {

		Object flag = context
				.getAttribute(RuntimeAttrNames.CLIENT_ORDER_PAY_FLAG);
		if (flag != null
				&& flag.toString().equals(
						RuntimeAttrNames.CLIENT_ORDER_PAY_FLAG)) {
			return true;
		} else {
			return false;
		}
	}

	public static void goToOrderCheck(TiActivity tiActivity) {
		TiFlowControlImpl.instanceControl().nextSetup(tiActivity,
				FlowConstants.SUCCESS);
	}

}
