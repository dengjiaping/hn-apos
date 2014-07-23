package me.andpay.timobileframework.bugsense;

import me.andpay.timobileframework.util.ReflectUtil;

import android.app.Activity;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class ThrowableHandlerTypeListener implements TypeListener {

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (ReflectUtil.isImplInterface(type.getRawType(),
				ThrowableHandler.class.getName())) {
			encounter.register(new InjectionListener<Object>() {
				public void afterInjection(Object injectee) {
					if (ReflectUtil.isImplInterface(injectee.getClass(),
							ThrowableHandler.class.getName()))
						ThrowableSense
								.registerHandler((ThrowableHandler) injectee);
				}
			});
		}
	}

}
