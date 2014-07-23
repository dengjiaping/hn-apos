package me.andpay.apos.tqm.event;

import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class TxnQueryConditionSureClickController extends
		AbstractEventController {
	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent resultItent = new Intent();
		QueryConditionForm form = (QueryConditionForm)formBean.getData();
		if(!StringUtil.isEmpty(form.getAmount())) {
			form.setAmount(StringConvertor.convertCurrency2Str(form.getAmount()));
		}
		resultItent.putExtra(
				"queryConditionForm",
				JacksonSerializer.newPrettySerializer().serialize(
						QueryConditionForm.class, formBean.getData()));
		refActivty
				.setResult(Activity.RESULT_OK, resultItent);
		refActivty.finish();
	}
}
