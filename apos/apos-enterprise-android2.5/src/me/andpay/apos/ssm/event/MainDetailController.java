package me.andpay.apos.ssm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainDetailController extends AbstractEventController{

	
	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent intent = new Intent("apos.ssm.unsettleDetail");
		//intent.getExtras().putString("batchStatus", TxnRecord.TERM_RECON_FLAG_WAIT);
		refActivty.startActivity(intent);
	}
}