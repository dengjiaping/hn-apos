package me.andpay.apos.trm.event;

import java.math.BigDecimal;

import me.andpay.apos.trm.activity.RefundInputActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.text.Editable;

public class RefundAmtEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {
		RefundInputActivity refActivity = (RefundInputActivity) activity;
		String inputAmt = refActivity.getAmtEditText().getText().toString();
		if (refActivity.getAmtEditText().isBank()) {
			refActivity.solfKeyBoard.changeSureButton(false, "退款");
		} else {
			refActivity.solfKeyBoard.changeSureButton(true, "退款");

		}

		BigDecimal inputAmtBg = new BigDecimal(
				StringConvertor.convertCurrency2Str(inputAmt));
		if (refActivity.refundInputContext.getRefundAmt().compareTo(inputAmtBg) < 0) {
			s.replace(0, s.length(), StringConvertor
					.convert2Currency(refActivity.refundInputContext
							.getRefundAmt().toString()));

		}
	}
}
