package me.andpay.apos.trm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.tam.activity.TiDynamicFieldDialog;
import me.andpay.apos.trm.activity.RefundInputActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class TrmExtInfoClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		RefundInputActivity trmActivity = (RefundInputActivity) activity;
		TiDynamicFieldDialog dialog = new TiDynamicFieldDialog(activity,
				trmActivity.dyHelper, TxnTypes.REFUND);
		dialog.setDelegate(trmActivity);
		Map<String, String> values = new HashMap<String, String>();
		values.put(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO, trmActivity.extTraceNo);
		values.put(FlexFieldDefine.FIELD_NAME_MEMO, trmActivity.memo);
		dialog.setFieldValue(values);
		dialog.show();
	}
}
