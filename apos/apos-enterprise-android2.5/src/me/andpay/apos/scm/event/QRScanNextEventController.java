package me.andpay.apos.scm.event;

import com.google.inject.Inject;

import android.app.Activity;
import android.view.View;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.event.SelectCardreaderEventController.BluetoothConnectMode;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class QRScanNextEventController extends AbstractEventController{
	
	@Inject
	private AposContext aposContext;
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		
		int cardreaderType = CardReaderTypes.NEW_LAND_BL;
		SelectCardreaderEventController.setCardreadType(aposContext, cardreaderType, activity);
		
		aposContext.getAppConfig().setAttribute(
				ConfigAttrNames.DEFAULT_BLUETOOTH_CONNECT_MODE,
				BluetoothConnectMode.SCAN);
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);
	}

}
