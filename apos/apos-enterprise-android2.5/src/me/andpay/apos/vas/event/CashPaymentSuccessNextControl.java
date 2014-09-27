package me.andpay.apos.vas.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class CashPaymentSuccessNextControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);

	}

}
