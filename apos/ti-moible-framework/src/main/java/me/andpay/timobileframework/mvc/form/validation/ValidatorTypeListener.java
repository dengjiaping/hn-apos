package me.andpay.timobileframework.mvc.form.validation;

import me.andpay.timobileframework.util.ReflectUtil;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * 用于监听FieldValidator 自动注入
 * 
 * @author tinyliu
 * 
 */
public class ValidatorTypeListener implements TypeListener {

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (ReflectUtil.isImplInterface(type.getRawType(),
				FieldValidator.class.getName())
				|| ReflectUtil.isImplInterface(type.getRawType(),
						FormDataValidator.class.getName())) {
			encounter.register(new InjectionListener<I>() {

				public void afterInjection(I validator) {
					if (validator.getClass().isAssignableFrom(
							FieldValidator.class)) {
						ValidatorContainer
								.registeFieldValidator((FieldValidator) validator);
					} else {
						ValidatorContainer
								.registeFormDataValidator((FormDataValidator) validator);
					}
				}
			});
		}
	}

}
