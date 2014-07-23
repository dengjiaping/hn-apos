package me.andpay.apos.config;

import me.andpay.apos.scm.action.ScmFeedBackAction;
import me.andpay.apos.scm.action.ScmPartyLimitAction;
import me.andpay.apos.scm.event.BackfeedCancelOnclickController;
import me.andpay.apos.scm.event.CurrentUserClickController;
import me.andpay.apos.scm.event.EmailButtonClickController;
import me.andpay.apos.scm.event.FeedBackSendButtonController;
import me.andpay.apos.scm.event.FeedBackSensorEventListener;
import me.andpay.apos.scm.event.IntentOnclickController;
import me.andpay.apos.scm.event.LimitReturnClickControl;
import me.andpay.apos.scm.event.LoginOutClickController;
import me.andpay.apos.scm.event.PhoneBtnClickController;
import me.andpay.apos.scm.event.PwdSureButtonClickController;
import me.andpay.apos.scm.event.PwdTextChangeEventController;
import me.andpay.apos.scm.event.ShowPartyLimitController;
import me.andpay.apos.scm.event.ShowWeiboClickEventControl;
import me.andpay.apos.scm.event.UpdateClickController;
import me.andpay.apos.scm.event.WebviewBackClickController;
import me.andpay.apos.scm.event.WebviewClickController;
import me.andpay.timobileframework.config.TiMobileModule;

import com.google.inject.Scopes;

public class ScmModule extends TiMobileModule {

	@Override
	protected void configure() {
		// bind controller
		bindEventController(FeedBackSendButtonController.class);
		bindEventController(IntentOnclickController.class);
		bindEventController(LoginOutClickController.class);
		bindEventController(PhoneBtnClickController.class);
		bindEventController(EmailButtonClickController.class);
		bindEventController(PwdSureButtonClickController.class);
		bindEventController(PwdTextChangeEventController.class);
		bindEventController(UpdateClickController.class);
		bindEventController(BackfeedCancelOnclickController.class);
		bindEventController(CurrentUserClickController.class);
		bindEventController(WebviewBackClickController.class);
		bindEventController(WebviewClickController.class);
		bindEventController(ShowWeiboClickEventControl.class);
		bindEventController(ShowPartyLimitController.class);
		bindEventController(LimitReturnClickControl.class);
		bindAction(ScmFeedBackAction.class);
		bindAction(ScmPartyLimitAction.class);

		bind(FeedBackSensorEventListener.class).in(Scopes.SINGLETON);
	}
}
