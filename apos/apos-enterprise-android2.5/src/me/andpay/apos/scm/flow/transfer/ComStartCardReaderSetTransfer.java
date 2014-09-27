package me.andpay.apos.scm.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class ComStartCardReaderSetTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		CardReaderSetContext cardReaderSetContext = new CardReaderSetContext();
		cardReaderSetContext.setShowBackButton(false);
		cardReaderSetContext.setShowSkipButton(true);
		cardReaderSetContext.setShowCardreaderGuide(false);
		cardReaderSetContext.setOpTraceNo(String.valueOf(System
				.currentTimeMillis()));
		TiFlowControlImpl.instanceControl().setFlowContextData(
				cardReaderSetContext);
		return null;
	}

}
