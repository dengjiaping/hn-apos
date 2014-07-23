package me.andpay.apos.common.receiver;

import me.andpay.apos.common.service.ProductSynchroner;
import me.andpay.apos.common.service.TxnConfirmService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.common.util.APOSAlarmUtil;
import me.andpay.timobileframework.mvc.support.TiApplication;
import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;

public class AposMainBroadcastReceiver extends RoboBroadcastReceiver {

	@Inject
	private TxnReversalService txnReversalService;

	@Inject
	private UpLoadFileServce upLoadFileServce;

	@Inject
	private ProductSynchroner productSynchroner;

	@Inject
	private TiApplication tiApplication;
	
	@Inject
	private TxnConfirmService txnConfirmService;

	protected void handleReceive(Context context, Intent intent) {

		Log.i("AposMainService", "AposMainService_onHandleIntent");
		
		txnReversalService.statrtReversal();
		upLoadFileServce.startUpload();
		productSynchroner.sync(tiApplication, false);
		txnConfirmService.sendConfirmTxn();
		
		APOSAlarmUtil.startMainAlarm(context);
		
	}

}
