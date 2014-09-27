package me.andpay.apos.scm.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.apos.tam.callback.impl.QueryBalanceCallBackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import android.app.Activity;

public class CardreaderSetBalanceTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);

		TxnControl txnControl = RoboGuiceUtil.getInjectObject(TxnControl.class,
				activity);

		TxnContext txnContext = txnControl.init();
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.INQUIRY_BALANCE);
		txnContext.setBackTagName(TabNames.BALANCE_PAGE);
		txnControl.setTxnCallback(new QueryBalanceCallBackImpl());
		txnContext.setOpTraceNo(cardReaderSetContext.getOpTraceNo());
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);

		return null;
	}

}
