package me.andpay.apos.tqm.event;


import me.andpay.apos.tqm.activity.TxnQueryConditionActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ClearConditionController extends AbstractEventController {

	public void onClick(Activity refActivity, FormBean formBean, View v) {
		TxnQueryConditionActivity condActivity = (TxnQueryConditionActivity)refActivity;
		
		condActivity.startDateView.setText("");
		condActivity.endPickerView.setText("");
//		if(StringUtil.isNotBlank(form.getAmount())) {
//			amtEditText.setText(StringConvertor.convertCurrency2Str(form.getAmount()));
//		}
		
		condActivity.amtEditText.setText("");
		condActivity.cardEdit.setText("");
		condActivity.orderditText.setText("");
		condActivity.txnIdEdit.setText("");

		condActivity.txnTypeSpinner.clear();
		
		condActivity.hasCondImg.setVisibility(View.GONE);
	}
}
