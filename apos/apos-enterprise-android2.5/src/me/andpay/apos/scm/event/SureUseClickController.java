package me.andpay.apos.scm.event;

import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ConfigAttrValues;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.lam.activity.RiskPromptActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SureUseClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		RiskPromptActivity riskPromptActivity = (RiskPromptActivity) refActivty;

		// 进入产品介绍页面
		riskPromptActivity.getAppConfig().setAttribute(
				ConfigAttrNames.ONCE_INSTALL_USE,
				ConfigAttrValues.ONCE_INSTALL_USE_VALUE);
		TiFlowControlImpl.instanceControl().nextSetup(refActivty,
				FlowConstants.SUCCESS);

	}
}
