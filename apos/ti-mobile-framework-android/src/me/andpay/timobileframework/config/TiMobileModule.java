package me.andpay.timobileframework.config;

import me.andpay.timobileframework.bugsense.ThrowableHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.action.Action;
import me.andpay.timobileframework.mvc.action.ActionMapping;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.binder.AnnotatedBindingBuilder;

public abstract class TiMobileModule extends AbstractModule {

	protected abstract void configure();

	protected void bindEventController(
			Class<? extends AbstractEventController> eventClass) {
		super.bind(eventClass);
	}

	protected void bindService(Class interfaceClass, Class ImplClass) {
		super.bind(interfaceClass).to(ImplClass).in(Scopes.SINGLETON);
	}

	protected void bindAction(Class<? extends Action> actionClass) {
		ActionMapping mapping = actionClass.getAnnotation(ActionMapping.class);
		if (mapping == null || mapping.domain() == null
				|| mapping.domain().equals("")) {
			throw new RuntimeException("the action mapping must be define");
		}
		super.bind(actionClass).asEagerSingleton();
		// ActionMappingHandler.registerMappings(mapping.domain(),actionClass);
	}

	protected void bindThrowableHandler(
			Class<? extends ThrowableHandler> handlerClass) {
		super.bind(handlerClass).asEagerSingleton();
	}

	@Override
	protected <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz) {
		// TODO Auto-generated method stub
		return super.bind(clazz);
	}

}
