package me.andpay.apos.scm.event;

import javax.inject.Inject;

import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.activity.BluetoothListActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

public class BluetoothListItemClickController extends AbstractEventController {

	@Inject
	private AposContext aposContext;

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {

		BluetoothListActivity bluetoothListActivity = (BluetoothListActivity) refActivty;
		CardReaderInfo cardReaderInfo = (CardReaderInfo) bluetoothListActivity
				.getBluetoothCardReaderListAdapter().getItem(position);

		setCardreader(cardReaderInfo);
		TiFlowControlImpl.instanceControl().nextSetup(refActivty,
				FlowConstants.SUCCESS);
	}

	public void setCardreader(CardReaderInfo cardReaderInfo) {
		aposContext.getAppConfig().setAttribute(
				CardReaderResourceSelector.getBluetoothIdKey(CardReaderManager
						.getCardReaderType()), cardReaderInfo.getmIdentifier());
		aposContext
				.getAppConfig()
				.setAttribute(
						CardReaderResourceSelector.getBluetoothNameKey(CardReaderManager
								.getCardReaderType()),
						cardReaderInfo.getmName());

	}
//	public void setCardreader(CardReaderInfo cardReaderInfo) {
//		setCardreaderContent(aposContext, cardReaderInfo.getmIdentifier(), cardReaderInfo.getmName());
//	}
//	
	//TODO应该是UTIL吧
	public static void setCardreaderContent(AposContext aposContext, String identifier, String name){
		if (CardReaderTypes.NEW_LAND_BL == CardReaderManager
				.getCardReaderType()) {
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_IDENTIFIER,
					identifier);
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_NAME,
					name);
		} else {
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_IDENTIFIER,
					identifier);
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_NAME,
					name);
		}
	}

}
