package me.andpay.timobileframework.mvc.form.android;

import me.andpay.timobileframework.util.ReflectUtil;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * 用于监听Getter 自动注入
 * 
 * @author tinyliu
 * 
 */
public class WidgetValueGetterTypeListener implements TypeListener {

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (ReflectUtil.isImplInterface(type.getRawType(),
				WidgetValueGetter.class.getName())) {
			try {
				WidgetValueGetterContainer
						.registeGetter((WidgetValueGetter) type.getRawType()
								.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(
						"instance widgetValueGetter happend error, the class type is "
								+ type.getRawType().getName(), e);
			}
		}
	}

}
