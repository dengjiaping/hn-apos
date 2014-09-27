package me.andpay.apos.vas.event;

import static me.andpay.apos.vas.activity.ProductPaymentModeActivity.PAYMENT_METHOD_KEY;
import me.andpay.apos.vas.activity.ProductPaymentModeActivity;
import me.andpay.apos.vas.flow.FlowConstants;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SelectPaymentControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		ProductPaymentModeActivity modeActivity = (ProductPaymentModeActivity) activity;

		if (modeActivity.cashPayLay.getId() == view.getId()) {
			modeActivity.getFlowContext().put(PAYMENT_METHOD_KEY,
					FlowConstants.ND_CASH_PAYMENT);
			modeActivity.nextSetup(FlowConstants.ND_CASH_PAYMENT);
			return;
		}

		if (modeActivity.cardPayLay.getId() == view.getId()) {
			modeActivity.getFlowContext().put(PAYMENT_METHOD_KEY,
					FlowConstants.ND_CARD_PAYMENT);
			modeActivity.nextSetup(FlowConstants.ND_CARD_PAYMENT);
			return;
		}

	}

}
