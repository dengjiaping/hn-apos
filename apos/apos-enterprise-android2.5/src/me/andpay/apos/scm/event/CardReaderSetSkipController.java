package me.andpay.apos.scm.event;

import java.util.Set;

import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

public class CardReaderSetSkipController extends AbstractEventController {

	@Inject
	private DefaultCardReaderCallBack defaultCardReaderCallBack;

	public void onClick(Activity activity, FormBean formBean, View view) {

		final TiActivity tiActivity = (TiActivity) activity;

		int cardreaderType = 1;
		PartyInfo partyInfo = (PartyInfo) tiActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		Set<String> remoteMsrTypes = partyInfo.getMsrTypes();
		if (!remoteMsrTypes.isEmpty()) {
			if (remoteMsrTypes.contains(CardReaderTypes.NEW_LAND_AD)) {
				cardreaderType = CardReaderTypes.NEW_LAND_AD;
			} else if (remoteMsrTypes.contains(CardReaderTypes.NEW_LAND_BL)) {
				cardreaderType = CardReaderTypes.NEW_LAND_BL;
			} else if (remoteMsrTypes.contains(CardReaderTypes.ITRON)) {
				cardreaderType = CardReaderTypes.ITRON;
			} else if (remoteMsrTypes.contains(CardReaderTypes.LANDI)) {
				cardreaderType = CardReaderTypes.LANDI;
			} else {
				cardreaderType = Integer.valueOf(remoteMsrTypes.iterator()
						.next());
			}
		}

		final OperationDialog operationDialog = new OperationDialog(activity,
				"提示",
				"您如果跳过此步,系统会默认设置"
						+ CardReaderResourceSelector
								.selectCardreaderCHName(cardreaderType)
						+ "型作为您的刷卡器。");
		final String cardreaderTypeStr = String.valueOf(cardreaderType);
		operationDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				operationDialog.dismiss();
				tiActivity.getAppConfig().setAttribute(
						ConfigAttrNames.CARD_READER_TYPE, cardreaderTypeStr);
				CardReaderManager.initCardReader(tiActivity
						.getApplicationContext(), AposCardReaderUtil
						.genAposSwiperContext(tiActivity.getAppConfig()));
				TiFlowControlImpl.instanceControl().nextSetup(tiActivity,
						FlowConstants.FINISH);
			}
		});

		operationDialog.show();

	}
}
