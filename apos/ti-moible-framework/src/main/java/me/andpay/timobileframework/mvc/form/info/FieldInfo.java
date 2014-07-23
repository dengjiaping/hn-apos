package me.andpay.timobileframework.mvc.form.info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.annotation.IsConst;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

/**
 * 表单字段反射信息
 * @author tinyliu
 *
 */
public class FieldInfo {
	
	private Field field = null;

	private ParamId paramId = null;

	private List<Annotation> fieldValidates = new ArrayList<Annotation>();

	private IsConst isConst = null;

	public FieldInfo(Field field) {
		this.field = field;
		resolver(field);

	}

	private void resolver(Field field) {
		paramId = field.getAnnotation(ParamId.class);
		isConst = field.getAnnotation(IsConst.class);
		Annotation[] annos = field.getDeclaredAnnotations();
		if (annos == null) {
			return;
		}
		for (Annotation anno : annos) {
			if (anno instanceof ParamId) {
				this.paramId = (ParamId) anno;
				continue;
			}
			if (anno.annotationType().isAnnotationPresent(
					FieldValidate.class)) {
				fieldValidates.add(anno);
			}
		}
	}

	public void setFieldValue(Object formBean, Object value)
			throws FormException {
		this.field.setAccessible(true);
		try {
			this.field.set(formBean, value);
		} catch (Exception e) {
			throw new FormException(
					FormProcessErrorCode.ERROR_FIELDVALUSET, e);
		}
	}

	public Object getFieldValue(Object obj) throws FormException {
		this.field.setAccessible(true);
		try {
			return this.field.get(obj);
		} catch (Exception e) {
			throw new FormException(
					FormProcessErrorCode.ERROR_FIELDVALUGET, e);
		} 
	}

	public IsConst getIsConst() {
		return isConst;
	}

	public Field getField() {
		return field;
	}

	public Integer getParamId() {
		return paramId == null ? null : paramId.value();
	}
	
	public ParamId getParamIdAnno() {
		return this.paramId;
	}

	public List<Annotation> getFieldValidates() {
		return fieldValidates;
	}

	public boolean isDynamicField() {
		return false;
	}

	public String getName() {
		return field.getName();
	} 
}
