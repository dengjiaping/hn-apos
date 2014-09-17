package me.andpay.apos.cardreader.callback;

import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Application;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.google.inject.Inject;

/**
 * 默认刷卡器回调
 * 
 * @author cpz
 * 
 */
public class DefaultCardReaderCallBack implements CardReaderCallback {

	@Inject
	private Application application;

	public void onDevicePlugged() {

		TiContext tiConfig = ((TiApplication) application).getContextProvider()
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
		Integer cardType = Integer.valueOf((String) tiConfig
				.getAttribute(ConfigAttrNames.CARD_READER_TYPE));

		Toast toast = Toast.makeText(application.getApplicationContext(), "",
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundColor(application.getApplicationContext()
				.getResources().getColor(R.color.com_translucent_col));

		ImageView imageCodeProject = new ImageView(
				application.getApplicationContext());
		imageCodeProject.setImageResource(CardReaderResourceSelector
				.selectShowImg(cardType, CardReaderResourceSelector.CONNECT));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		toastView.addView(imageCodeProject, params);
		toast.show();

	}

	public void onDeviceUnplugged() {
		TiContext tiConfig = ((TiApplication) application).getContextProvider()
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);

		Integer cardType = CardReaderManager.getCardReaderType();

		Toast toast = Toast.makeText(application.getApplicationContext(), "",
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundColor(application.getApplicationContext()
				.getResources().getColor(R.color.com_translucent_col));

		ImageView imageCodeProject = new ImageView(
				application.getApplicationContext());
		int imageId = 0;
		try {
			imageId = CardReaderResourceSelector.selectShowImg(cardType,
						CardReaderResourceSelector.SWIPER);
		}catch(Exception ex) {
			
		}
	
		if(imageId >0) {
			imageCodeProject.setImageResource(imageId);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			toastView.addView(imageCodeProject, params);
			toast.show();
		}

		

	}

	public void onTimeout() {
		// TODO Auto-generated method stub

	}

	public void onWaitingForCardSwipe() {
		// TODO Auto-generated method stub

	}

	public void onDecodeCompleted(CardInfo cardInfo) {
		// TODO Auto-generated method stub

	}

	public void scanning() {
		// TODO Auto-generated method stub

	}

	public void onDeviceError(String msg, String errorCode) {
		// TODO Auto-generated method stub

	}

	public void onCancel() {
		// TODO Auto-generated method stub

	}

	public void onDecodingStart() {
		// TODO Auto-generated method stub

	}

	public void onReceiveDataStart() {
		// TODO Auto-generated method stub

	}

	public void onDecodeError(String msg) {
		// TODO Auto-generated method stub

	}

	public void OnWaitingPin() {
		// TODO Auto-generated method stub

	}

	public void onSeachDevice() {
		System.out.println("asdsd");
	}

	public void onShowSwiper() {
		// TODO Auto-generated method stub

	}

	public void seachDeviceComplete(List<CardReaderInfo> cardReaderInfos) {
		// TODO Auto-generated method stub

	}

	public void getTxnError() {
		// TODO Auto-generated method stub

	}

	public void onSendDataError() {
		// TODO Auto-generated method stub

	}

	public void matchBluetoothHelp() {
		// TODO Auto-generated method stub

	}

	public void onConnectError() {
		// TODO Auto-generated method stub

	}

	public void seachFoundDevice(CardReaderInfo cardReaderInfo,
			List<CardReaderInfo> cardReaderInfos) {
		// TODO Auto-generated method stub

	}

	public void initKeyError(String errorMsg) {
		// TODO Auto-generated method stub

	}

	public void onICFinished(boolean finish, AposICCardDataInfo icCardDataInfo) {
		// TODO Auto-generated method stub

	}

	public void onICSwipeRefuse() {
		// TODO Auto-generated method stub

	}

	public void onICRequestOnline(AposICCardDataInfo icCardDataInfo) {
		// TODO Auto-generated method stub

	}

	public void onSecondIssuanceError() {
		// TODO Auto-generated method stub

	}

}
