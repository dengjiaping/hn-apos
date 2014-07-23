package me.andpay.timobileframework.mvc.form.android;

import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FieldValueLoader;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import android.app.Activity;
import android.view.View;

/**
 * android value resolver
 * 
 * @author tinyliu
 * 
 */
@SuppressWarnings("rawtypes")
public class AndroidFieldValueLoader implements FieldValueLoader {

	private Class resourceClass = null;

	public Object loadValue(ValueContainer container, Field field,
			FormBean formBean, Integer id) throws FormException {
		return getWidgetValue(container, field.getName(),
				id != null ? id.intValue() : null);
	}

	private Object getWidgetValue(ValueContainer container, String fieldName,
			Integer rid) throws FormException {
		Activity activity = (Activity) container;
		synchronized (this) {
			if (resourceClass == null) {
				try {
					initResourceClass(activity);
				} catch (Exception ex) {
					throw new FormException(
							FormProcessErrorCode.ERROR_ANDROID_R_INIT, ex);
				}
			}

		}
		View view = null;
		try {
			rid = getFieldId(fieldName, rid);
		} catch (Exception ex) {
			throw new FormException(FormProcessErrorCode.ERROR_ANDROID_GETRID,
					ex);
		}
		view = activity.findViewById(rid);
		if (view == null) {
			return null;
		}
		if (view instanceof WidgetValueHolder) {
			return ((WidgetValueHolder) view).getWidgetValue();
		} else {
			WidgetValueGetter getter = WidgetValueGetterContainer
					.getGetter(view);

			if (getter == null) {
				return null;
			}
			return getter.getWidgetValue(view);
		}
	}

	/**
	 * 加载R.id Class
	 * 
	 * @param container
	 * @throws ClassNotFoundException
	 */
	private void initResourceClass(Activity activty)
			throws ClassNotFoundException {
		String ridPath = activty.getApplicationContext().getPackageName()
				+ ".R$id";
		resourceClass = Class.forName(ridPath);

	}

	/**
	 * 获取Container中间资源唯一编号
	 * 
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private Integer getFieldId(String fieldName, Integer id)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		if (id == null) {
			return (Integer) resourceClass.getField(fieldName).get(
					resourceClass);
		}
		return id;
	}

}
