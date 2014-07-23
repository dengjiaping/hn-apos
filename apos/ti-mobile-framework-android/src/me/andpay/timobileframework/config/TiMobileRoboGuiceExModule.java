package me.andpay.timobileframework.config;

import me.andpay.timobileframework.bugsense.TextThrowableRecorder;
import me.andpay.timobileframework.bugsense.ThrowableHandlerTypeListener;
import me.andpay.timobileframework.bugsense.ThrowableRecorder;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;

public class TiMobileRoboGuiceExModule  extends AbstractModule {
	@Override
	protected void configure() {
		/**
		 * BugSense config Begin
		 * *********************************************************************
		 */
		bind(ThrowableRecorder.class).to(TextThrowableRecorder.class).in(Scopes.SINGLETON);
		bindListener(Matchers.any(), new ThrowableHandlerTypeListener());
		/**
		 * BugSense config End
		 * ***********************************************************************
		 */
	}
}
