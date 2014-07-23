package me.andpay.apos.scm.event;

import javax.inject.Inject;

import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class GuideButtonController extends AbstractEventController {

	@Inject
	private AposContext aposContext;

	public void onClick(Activity activity, FormBean formBean, View view) {

		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CardReaderSetContext.class);
		
		
		cardReaderSetContext.setShowCardreaderGuide(false);
		cardReaderSetContext.setHasSelectCardreader(false);
		cardReaderSetContext.setKsn(null);
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);

	}
}
