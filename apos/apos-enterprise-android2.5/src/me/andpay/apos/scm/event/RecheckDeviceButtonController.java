package me.andpay.apos.scm.event;

import android.app.Activity;
import android.view.View;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.activity.CardReaderAdapterSuccessActivity;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class RecheckDeviceButtonController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		cardReaderSetContext.getOpLogData().put(
				OperationDataKeys.OPKEYS_RECHECK_FLAG, String.valueOf(true));
		cardReaderSetContext
				.setTryTimes(cardReaderSetContext.getTryTimes() + 1);
		CardReaderAdapterSuccessActivity cardReaderAdapterSuccessActivity = (CardReaderAdapterSuccessActivity) refActivty;
		cardReaderAdapterSuccessActivity.checkDevice();
	}
}
