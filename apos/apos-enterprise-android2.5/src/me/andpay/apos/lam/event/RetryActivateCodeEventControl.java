package me.andpay.apos.lam.event;



import me.andpay.apos.R;
import me.andpay.apos.lam.action.ActivateCodeAction;
import me.andpay.apos.lam.activity.ActivateCodeActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class RetryActivateCodeEventControl extends AbstractEventController {
	
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		
		ActivateCodeActivity activateCodeActivity = (ActivateCodeActivity)activity;
		activateCodeActivity.retryCodeBtn.setEnabled(false);
		activateCodeActivity.retryCodeBtn.startTimer(60);
		
		EventRequest request = activateCodeActivity
				.generateSubmitRequest(activateCodeActivity);
		request.open(ActivateCodeAction.DOMAIN_NAME,
				ActivateCodeAction.SEDN_ACIVATE_CODE, Pattern.async);
		request.submit();
		
		Toast.makeText(activity.getApplicationContext(), R.string.lam_activate_code_send_success_str,
			     Toast.LENGTH_LONG).show();
	}
}
