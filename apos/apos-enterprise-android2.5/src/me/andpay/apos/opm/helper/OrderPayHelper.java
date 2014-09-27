package me.andpay.apos.opm.helper;

import java.math.BigDecimal;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import android.app.Activity;

public class OrderPayHelper {

	public static void sendTxn(Activity activity, OrderInfo orderInfo) {

		TxnControl txnControl = RoboGuiceUtil.getInjectObject(TxnControl.class,
				activity);
		TxnContext txnContext = txnControl.init();
		txnControl.setTxnCallback(new TxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setGoodsUpload(false);
		txnContext.setSignUplaod(true);
		txnContext.setSalesAmt(new BigDecimal(orderInfo.getOrderAmt()
				.toString()));
		txnContext.setExtTraceNo(orderInfo.getOrderId());
		txnContext.setExtType(ExtTypes.EXT_TYPE_ORDER_QUERY);

		// 可回主页
		txnContext.setBackTagName(TabNames.ORDERPAY_PAGE);
		txnContext.setTxnType(TxnTypes.PURCHASE);

		TiFlowControlImpl.instanceControl().startFlow(activity,
				FlowNames.TXN_FLOW);
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);
	}
}
