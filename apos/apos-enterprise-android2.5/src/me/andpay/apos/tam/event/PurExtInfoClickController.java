package me.andpay.apos.tam.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.apos.tam.activity.TiDynamicFieldDialog;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 显示动态字段
 * 
 * @author tinyliu
 *
 */
public class PurExtInfoClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		PurchaseFirstActivity purActivity = (PurchaseFirstActivity) activity;
		TiDynamicFieldDialog dialog = new TiDynamicFieldDialog(activity,
				purActivity.dyHelper, TxnTypes.PURCHASE);
		dialog.setDelegate(purActivity);
		Map<String, String> values = new HashMap<String, String>();
		values.put(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO,
				purActivity.extTraceNo);
		values.put(FlexFieldDefine.FIELD_NAME_MEMO, purActivity.memo);
		dialog.setFieldValue(values);
		dialog.show();
	}
}
