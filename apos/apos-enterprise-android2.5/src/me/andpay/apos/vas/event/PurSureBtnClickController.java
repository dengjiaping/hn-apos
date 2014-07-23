package me.andpay.apos.vas.event;

import java.math.BigDecimal;

import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.PurchaseQueryCondActivity;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.View;

/**
 * 条件查询确定按钮点击事件
 * 
 * @author tinyliu
 * 
 */
public class PurSureBtnClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		PurchaseQueryCondActivity activity = (PurchaseQueryCondActivity) refActivty;
		QueryPurchaseOrderInfoCond cond = new QueryPurchaseOrderInfoCond();
		Boolean isHasCond = false;
		if (!StringUtil.isEmpty((String) activity.getStartDateView().getWidgetValue())) {
			isHasCond = true;
			String dateStr = (String) activity.getStartDateView().getWidgetValue();
			cond.setMinOrderTime(DateUtil.parse(VasProvider.VAS_CONST_DATETIME_PARTTERN,
					dateStr + " " + VasProvider.VAS_CONST_BEGINTIMES));
		}
		if (!StringUtil.isEmpty((String) activity.getEndPickerView().getWidgetValue())) {
			isHasCond = true;
			String dateStr = (String) activity.getEndPickerView().getWidgetValue();
			cond.setMaxOrderTime(DateUtil.parse(VasProvider.VAS_CONST_DATETIME_PARTTERN,
					dateStr + " " + VasProvider.VAS_CONST_ENDTIMES));
		}
		if (!StringUtil.isEmpty((String) activity.getAmtEditText().getWidgetValue())) {
			isHasCond = true;
			cond.setSalesAmt(new BigDecimal(StringConvertor
					.convertCurrency2Str((String) activity.getAmtEditText()
							.getWidgetValue())));
		}
		if (!StringUtil.isEmpty((String) activity.payMethodSp.getWidgetValue())) {
			isHasCond = true;
			cond.setPaymentMethod(activity.payMethodSp.getWidgetValue().toString());
		}
		
		cond.setHasViewCond(isHasCond);
		activity.getControl().setFlowContextData(cond);
		activity.getControl().previousSetup(activity);
	}
}
