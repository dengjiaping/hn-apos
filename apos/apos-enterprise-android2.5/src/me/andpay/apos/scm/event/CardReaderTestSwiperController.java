package me.andpay.apos.scm.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class CardReaderTestSwiperController  extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		
		TiFlowControlImpl.instanceControl().nextSetup(refActivty, FlowConstants.SUCCESS_STEP2);
		
	}

}
