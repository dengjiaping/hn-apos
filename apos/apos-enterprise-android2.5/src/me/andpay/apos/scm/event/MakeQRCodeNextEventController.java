package me.andpay.apos.scm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.constant.QrScanType;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class MakeQRCodeNextEventController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		
		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("scanType", QrScanType.ST_BLUETOOTH);
		TiFlowControlImpl.instanceControl().nextSetup(refActivty,
				FlowConstants.SUCCESS, intentData);
	}
}

