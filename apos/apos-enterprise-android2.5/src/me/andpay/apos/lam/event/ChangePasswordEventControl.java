package me.andpay.apos.lam.event;

import me.andpay.apos.common.util.ValidateHelper;
import me.andpay.apos.lam.callback.impl.ChangePasswordCallbackImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ChangePasswordEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		if(ValidateHelper.validate(activity, formBean, null)) {
			return;
		}
		
		EventRequest request = this.generateSubmitRequest(activity);
		request.open(formBean, Pattern.async);
		request.callBack(new ChangePasswordCallbackImpl(activity, formBean,
				view));
		request.submit();
	}
}
