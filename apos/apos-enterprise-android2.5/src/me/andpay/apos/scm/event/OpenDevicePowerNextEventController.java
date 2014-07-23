package me.andpay.apos.scm.event;

import com.google.inject.Inject;

import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.event.SelectCardreaderEventController.BluetoothConnectMode;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OpenDevicePowerNextEventController extends AbstractEventController {

	@Inject
	private AposContext aposContext;

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		
		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		
		if(CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {
			TiFlowControlImpl.instanceControl().nextSetup(refActivty, FlowConstants.SUCCESS);
		}else{
			if(CardReaderTypes.NEW_LAND_BL == CardReaderManager.getCardReaderType() 
					&& BluetoothConnectMode.SCAN.equals(aposContext.getAppConfig().getAttribute(
					ConfigAttrNames.DEFAULT_BLUETOOTH_CONNECT_MODE))){
				TiFlowControlImpl.instanceControl().nextSetup(refActivty, FlowConstants.SUCCESS_STEP3);
			}else{
				TiFlowControlImpl.instanceControl().nextSetup(refActivty, FlowConstants.SUCCESS_STEP2);
			}
		}
	
	}
}
