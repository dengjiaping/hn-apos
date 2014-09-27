package me.andpay.apos.tam.event;

import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.tam.activity.TxnDetailActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class DetailNextButtonEventControl extends AbstractEventController {

	@Inject
	private UpLoadFileServce upLoadFileServce;

	public void onClick(Activity activity, FormBean formBean, View view) {
		TxnDetailActivity detailActivity = (TxnDetailActivity) activity;
		detailActivity.txnControl.backHomePage(activity);
		upLoadFileServce.uploadFile();
	}
}
