package me.andpay.apos.tam.event;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.tam.TamProvider;
import me.andpay.apos.tam.callback.impl.QueryBalanceCallBackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.provider.BundleProvider;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
/**
 * 
 * @author cpz
 * 
 */
public class QueryBalanceEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;
	
	public void onClick(Activity activity, FormBean formBean, View view) {
	
		
		TxnContext txnContext = txnControl.init();
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.INQUIRY_BALANCE);
		txnContext.setBackTagName(TabNames.TXN_PAGE);
		txnControl.setTxnCallback(new QueryBalanceCallBackImpl());
		
		Intent intent = new Intent();
		intent.putExtras(BundleProvider.provid(formBean));
		intent.setAction(TamProvider.TAM_TXN_ACTIVITY);
		activity.startActivity(intent);


	}
}
