package me.andpay.timobileframework.mvc.form.validation.translate;

import me.andpay.timobileframework.util.ReflectUtil;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class ErrorMsgTranslateTypelistener implements TypeListener {

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (ReflectUtil.isImplInterface(type.getRawType(), ErrorMsgTranslate.class.getName())) {
			encounter.register(new InjectionListener<I>() {

				public void afterInjection(I translate) {
					if (translate.getClass().isAssignableFrom(
							ErrorMsgTranslate.class)) {
						ErrorMsgTranslateSelector
								.registeImplTranslate((ErrorMsgTranslate) translate);
					}
				}
			});
		}
	}

}
