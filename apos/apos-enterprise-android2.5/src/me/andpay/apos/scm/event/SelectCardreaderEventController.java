package me.andpay.apos.scm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.AudioUtil;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.inject.Inject;

public class SelectCardreaderEventController extends AbstractEventController {

	public Map<Integer, Integer> cardTypes = new HashMap<Integer, Integer>();

	public boolean isInit;

	@InjectView(R.id.scm_button_one)
	public Button oneBt;
	@InjectView(R.id.scm_button_two)
	public Button twoBt;
	@InjectView(R.id.scm_button_three)
	public Button threeBt;
	@InjectView(R.id.scm_button_four)
	public Button fourBt;
	@InjectView(R.id.scm_button_five)
	public Button fiveBt;
	@InjectView(R.id.scm_button_six)
	public Button sixBt;

	@Inject
	private CardReaderManager cardReaderManager;
	@Inject
	private DefaultCardReaderCallBack defaultCardReaderCallBack;
	@Inject
	private AposContext aposContext;
	
	public interface BluetoothConnectMode{
		public static final String SEARCH = "search";
		public static final String SCAN = "scan";
	}

	public void onClick(final Activity refActivty, FormBean formBean, View v) {

		initData();
		int cardreaderType = cardTypes.get(Integer.valueOf(v.getId()));

		setCardreadType(aposContext, cardreaderType, refActivty);
		
		if (cardreaderType == CardReaderTypes.ITRON
				|| cardreaderType == CardReaderTypes.LANDI) {

			TiFlowControlImpl.instanceControl().nextSetup(refActivty,
					FlowConstants.SUCCESS_STEP1);
			return;
		}
		
		if(cardreaderType == CardReaderTypes.NEW_LAND_BL){
//			final OperationDialog operationDialog = new OperationDialog(refActivty,
//					null, "请选择匹配蓝牙读卡器的方式", "手动搜索", "二维码匹配", true);
//			operationDialog.setSureButtonOnclickListener(new OnClickListener() {
//				public void onClick(View v) {
//					operationDialog.dismiss();
//					aposContext.getAppConfig().setAttribute(
//							ConfigAttrNames.DEFAULT_BLUETOOTH_CONNECT_MODE,
//							BluetoothConnectMode.SEARCH);
//					TiFlowControlImpl.instanceControl().nextSetup(refActivty,
//							FlowConstants.SUCCESS_STEP1);
//					return;
//				}
//			});
//			operationDialog.setCancelButtonOnclickListener(new OnClickListener() {
//				public void onClick(View v) {
//					operationDialog.dismiss();
//					aposContext.getAppConfig().setAttribute(
//							ConfigAttrNames.DEFAULT_BLUETOOTH_CONNECT_MODE,
//							BluetoothConnectMode.SCAN);
//					TiFlowControlImpl.instanceControl().nextSetup(refActivty,
//							FlowConstants.SUCCESS_STEP1);
//					return;
//				}
//			});
//
//			operationDialog.show();
			
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.DEFAULT_BLUETOOTH_CONNECT_MODE,
					BluetoothConnectMode.SEARCH);
			TiFlowControlImpl.instanceControl().nextSetup(refActivty,
					FlowConstants.SUCCESS_STEP1);
		}

		// 6型读卡器能够自动开机
		if (cardreaderType == CardReaderTypes.NEW_LAND_AD) {
			TiFlowControlImpl.instanceControl().nextSetup(refActivty,
					FlowConstants.SUCCESS_STEP2);
		}

		if (cardreaderType == CardReaderTypes.NEW_LAND) {

			if (AudioUtil.isHeadsetInsert(refActivty.getApplicationContext())) {

				TiFlowControlImpl.instanceControl().nextSetup(refActivty,
						FlowConstants.SUCCESS_STEP3);
			} else {
				TiFlowControlImpl.instanceControl().nextSetup(refActivty,
						FlowConstants.SUCCESS_STEP2);
			}
			return;
		}

		if (cardreaderType == CardReaderTypes.CLOUD_POS) {
			TiFlowControlImpl.instanceControl().nextSetup(refActivty,
					FlowConstants.SUCCESS_STEP3);
			return;
		}

	}
	
	private void initData() {

		if (!isInit) {
			isInit = true;
			cardTypes.put(Integer.valueOf(oneBt.getId()),
					CardReaderTypes.NEW_LAND);
			cardTypes.put(Integer.valueOf(twoBt.getId()), 
					CardReaderTypes.ITRON);
			cardTypes.put(Integer.valueOf(threeBt.getId()),
					CardReaderTypes.CLOUD_POS);
			cardTypes.put(Integer.valueOf(fourBt.getId()),
					CardReaderTypes.LANDI);
			cardTypes.put(Integer.valueOf(fiveBt.getId()),
					CardReaderTypes.NEW_LAND_BL);
			cardTypes.put(Integer.valueOf(sixBt.getId()),
					CardReaderTypes.NEW_LAND_AD);
		}
	}
	
	public static void setCardreadType(AposContext aposContext, int cardreaderType, Activity refActivty){
		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		cardReaderSetContext.setKsn(null);
		
		// 设置读卡器
		Object oldCardreadType = aposContext.getAppConfig().getAttribute(
				ConfigAttrNames.CARD_READER_TYPE);
		if (oldCardreadType == null
				|| StringUtil.isBlank(oldCardreadType.toString())
				|| Integer.valueOf(oldCardreadType.toString()) != Integer
						.valueOf(cardreaderType)) {
			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.CARD_READER_TYPE,
					Integer.valueOf(cardreaderType));
			CardReaderManager.resetCardreader();
			CardReaderManager.initCardReader(
					refActivty.getApplicationContext(),
					AposCardReaderUtil.genAposSwiperContext(aposContext.getAppConfig()));
		}
		cardReaderSetContext.setCardReaderType(cardreaderType);

	}


}
