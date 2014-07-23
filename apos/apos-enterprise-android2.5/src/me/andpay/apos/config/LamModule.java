package me.andpay.apos.config;

import me.andpay.apos.lam.action.ActivateCodeAction;
import me.andpay.apos.lam.action.ChangePasswordAction;
import me.andpay.apos.lam.action.GenMsrKeysAction;
import me.andpay.apos.lam.action.LoginAction;
import me.andpay.apos.lam.event.ActivateCodeTextWatcherEventControl;
import me.andpay.apos.lam.event.ChangePasswordEventControl;
import me.andpay.apos.lam.event.ForgetPasswordEventControl;
import me.andpay.apos.lam.event.HelpStartPageChangeEventControl;
import me.andpay.apos.lam.event.IntroduceStartPageChangeEventControl;
import me.andpay.apos.lam.event.LoginSumitButtonEventControl;
import me.andpay.apos.lam.event.RetryActivateCodeEventControl;
import me.andpay.apos.lam.event.ShowHelpClickControl;
import me.andpay.apos.lam.event.ShowLoginControl;
import me.andpay.apos.lam.event.SubmitActivateCodeEventControl;
import me.andpay.apos.lam.event.UseBeginButtonEventControl;
import me.andpay.apos.lam.event.UserNameAutoTextEventControl;
import me.andpay.apos.scm.event.SureUseClickController;
import me.andpay.timobileframework.config.TiMobileModule;


public class LamModule extends TiMobileModule{

	@Override
	protected void configure() {	
		bindEventController(IntroduceStartPageChangeEventControl.class);
		bindEventController(LoginSumitButtonEventControl.class);
		bindEventController(UseBeginButtonEventControl.class);
		bindEventController(UserNameAutoTextEventControl.class);
		bindEventController(ChangePasswordEventControl.class);
		bindEventController(ForgetPasswordEventControl.class);
		bindEventController(ActivateCodeTextWatcherEventControl.class);
		bindEventController(RetryActivateCodeEventControl.class);
		bindEventController(SubmitActivateCodeEventControl.class);
		bindEventController(HelpStartPageChangeEventControl.class);
		bindEventController(ShowHelpClickControl.class);
		bindEventController(ShowLoginControl.class);
		bindEventController(SureUseClickController.class);
		bindAction(LoginAction.class);
		bindAction(ChangePasswordAction.class);
		bindAction(ActivateCodeAction.class);
		bindAction(GenMsrKeysAction.class);
	}
	
}
