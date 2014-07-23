package me.andpay.apos.common.receiver;

import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.service.TxnConfirmService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.google.inject.Inject;

/**
 * APOS网络监听
 * 
 * @author cpz
 * 
 */
public class AposNetworkChangeReceiver extends BroadcastReceiver {

	/**
	 * 是否已经注册
	 */
	public boolean register;

	@Inject
	public UpLoadFileServce upLoadFileServce;

	@Inject
	public TxnReversalService txnReversalService;
	
	@Inject
	public TxnConfirmService txnConfirmService;
	
	//@Inject
	//AposReportPersistenceThrowService throwService;

	@Inject
	public TiRpcClient tiRpcClient;


	@Override
	public void onReceive(Context context, Intent intent) {
//		
		if(CommonProvider.BROADCAST_CLOSEAPP_ACTION.equals(intent.getAction())) {
			Intent intentDate = new Intent();
			intentDate.setAction(CommonProvider.COM_FIRSTPAGE_ACTIVITY);
			TiAndroidRuntimeInfo.getCurrentActivity().startActivity(intentDate);
			TiAndroidRuntimeInfo.getCurrentActivity().finish();
			return;
		}

		if (NetWorkUtil.isNetworkConnected(context)) {
			upLoadFileServce.uploadFile();
			txnConfirmService.sendConfirmTxn();		
		} else {
//			tiRpcClient.pause();
		}

	}
	
	

	public void register(Context context) {
		if (register) {
			return;
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction(CommonProvider.BROADCAST_CLOSEAPP_ACTION);
		context.registerReceiver(this, filter);
		register = true;
	}

	public void unRegister(Context context) {
		if (!register) {
			return;
		}
		context.unregisterReceiver(this);
		register = false;
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

}
