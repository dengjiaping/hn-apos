package me.andpay.apos.scm.event;

import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.AudioUtil;
import android.app.Activity;
import android.view.View;

public class PromptDeviceInsertNextEventController extends AbstractEventController {


	public void onClick(Activity refActivty, FormBean formBean, View v) {
	
		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		
		if(AudioUtil.isHeadsetInsert(refActivty))  {
			TiFlowControlImpl.instanceControl().nextSetup(refActivty, FlowConstants.SUCCESS);
		}else {
			ToastTools.topToast(refActivty,
					"您的设备未正确连接手机",
					ToastTools.LIST_VIEW_TOAST_HEIGHT);	
		}
		
	}


}
