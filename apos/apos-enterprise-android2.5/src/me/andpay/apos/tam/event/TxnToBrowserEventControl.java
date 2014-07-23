package me.andpay.apos.tam.event;


import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.timobileframework.flow.activity.TiFlowActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class TxnToBrowserEventControl  extends AbstractEventController  {
	
	
	@Inject
	public TxnControl txnControl;
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		
		txnControl.changeTxnStatus(TxnStatus.WAIT_BROWSER_PHONENO, (TiFlowActivity)activity);
	}
}
