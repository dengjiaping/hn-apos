package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;


public class TxnBackEventControl extends AbstractEventController {
	
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		
		TxnAcitivty txnAcitivty = (TxnAcitivty)activity;
		txnAcitivty.txnControl.reInput();
	}
}
