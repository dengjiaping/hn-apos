package me.andpay.timobileframework.config;

import me.andpay.timobileframework.bugsense.TextThrowableRecorder;
import me.andpay.timobileframework.bugsense.ThrowableHandlerTypeListener;
import me.andpay.timobileframework.bugsense.ThrowableRecorder;
import me.andpay.timobileframework.mvc.EventDelegateProcess;
import me.andpay.timobileframework.mvc.FormProcessProvider;
import me.andpay.timobileframework.mvc.action.ActionTypeListener;
import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.android.WidgetValueGetterTypeListener;
import me.andpay.timobileframework.mvc.form.validation.ValidatorTypeListener;
import me.andpay.timobileframework.mvc.form.validation.translate.ErrorMsgTranslateTypelistener;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;

public class TiMobileRoboGuiceBaseModule extends AbstractModule {

	@Override
	protected void configure() {
		/**
		 * Form process default config Begin
		 * *********************************************************************
		 */
		bind(EventDelegateProcess.class).in(Scopes.SINGLETON);
		bind(DispatchCenter.class).in(Scopes.SINGLETON);
		bind(FormProcesser.class).toProvider(FormProcessProvider.class).in(
				Scopes.SINGLETON);
		bindListener(Matchers.any(), new WidgetValueGetterTypeListener());
		bindListener(Matchers.any(), new ValidatorTypeListener());
		bindListener(Matchers.any(), new ErrorMsgTranslateTypelistener());
		bindListener(Matchers.any(), new ActionTypeListener());
		/**
		 * Form process default config End
		 * **************************************
		 * *********************************
		 */
		/**
		 * BugSense config Begin
		 * *********************************************************************
		 */
		bind(ThrowableRecorder.class).to(TextThrowableRecorder.class).in(
				Scopes.SINGLETON);
		bindListener(Matchers.any(), new ThrowableHandlerTypeListener());
		/**
		 * BugSense config End
		 * **************************************************
		 * *********************
		 */
	}

}
