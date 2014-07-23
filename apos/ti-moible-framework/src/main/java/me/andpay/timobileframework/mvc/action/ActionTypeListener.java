package me.andpay.timobileframework.mvc.action;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * 用来监听action的存在
 * 
 * @author tinyliu
 * 
 */
public class ActionTypeListener implements TypeListener {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (type.getRawType().isAnnotationPresent(ActionMapping.class)) {
			encounter.register(new InjectionListener() {
				public void afterInjection(Object injectee) {
					ActionMapping mapping = injectee.getClass().getAnnotation(
							ActionMapping.class);
					if (mapping == null || mapping.domain() == null
							|| mapping.domain().equals("")) {
						throw new RuntimeException(
								"the action mapping must be define");
					}
					ActionMappingHandler.registerMappings(mapping.domain(),
							(Action) injectee);
				}
			});

		}
	}
}
