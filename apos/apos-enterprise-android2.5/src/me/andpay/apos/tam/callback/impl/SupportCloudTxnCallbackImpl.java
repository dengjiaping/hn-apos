package me.andpay.apos.tam.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.callback.CloudPosCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

@CallBackHandler
public class SupportCloudTxnCallbackImpl implements CloudPosCallback {

	private TxnControl txnControl;

	public SupportCloudTxnCallbackImpl(TxnControl txnControl) {
		this.txnControl = txnControl;
	}

	public void pushOrderSucc(String cloudOrderId) {
		TxnContext context = txnControl.getTxnContext();
		if (txnControl.getTxnDialog() != null && txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		Log.i(this.getClass().getName(), "couldOrderId is [" + cloudOrderId + "]");
		context.setCloudOrderId(cloudOrderId);
		DisplayMetrics metric = new DisplayMetrics();
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//		activity.gifView.setShowDimension(metric.widthPixels,
//				Float.valueOf((260 * metric.density)).intValue());
//		activity.gifView.setGifImage(R.drawable.tam_cardreader_apos3_gif);
		
		APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity.getResources(),
				R.drawable.tam_cardreader_apos3_gif);
		
		activity.gifView.setVisibility(View.VISIBLE);
		activity.topTextView.setText(R.string.tam_top_cloud_order_waitpay_str);
	}

	public void pushOrderNetworkError(String errorMsg) {
		if (txnControl.getTxnDialog() != null && txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", errorMsg);
		intentData.put("buttonName", "重新刷卡");

		TiFlowControlImpl.instanceControl().nextSetup(txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FAILED, intentData);
	}

}
