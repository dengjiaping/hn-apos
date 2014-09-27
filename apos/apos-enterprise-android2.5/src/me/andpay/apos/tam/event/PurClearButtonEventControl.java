package me.andpay.apos.tam.event;

import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class PurClearButtonEventControl extends AbstractEventController {
	@Inject
	private TxnControl txnControl;

	public void onClick(Activity activity, FormBean formBean, View view) {

		/*
		 * PurchaseFirstActivity tiActivity = (PurchaseFirstActivity)activity;
		 * 
		 * view.setVisibility(View.GONE);
		 * 
		 * if(view.getId() == R.id.tam_amt_delete) {
		 * tiActivity.amtEditText.clear();
		 * Selection.setSelection(tiActivity.amtEditText.getEditableText(),
		 * tiActivity.amtEditText.length());
		 * 
		 * }else if(view.getId() == R.id.tam_order_delete) {
		 * tiActivity.orderText.setText(""); }else if(view.getId() ==
		 * R.id.tam_memo_delete) { tiActivity.memoText.setText(""); }
		 */

	}
}
