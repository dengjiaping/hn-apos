package me.andpay.apos.scm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class CardReaderAdapterSuccessStartButtonController extends
		AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		// CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
		// .instanceControl().getFlowContextData(
		// CardReaderSetContext.class);
		//
		//
		//
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put(CommonProvider.TAGNAME, TabNames.TXN_PAGE);

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.FINISH, intentData);

	}

}
