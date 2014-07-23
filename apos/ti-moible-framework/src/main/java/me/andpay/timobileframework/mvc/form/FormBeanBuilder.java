package me.andpay.timobileframework.mvc.form;

import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.FieldInfo;
import me.andpay.timobileframework.mvc.form.info.FormDataInfo;

/**
 * Form Bean 构建器 test
 * 
 * @author tinyliu
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FormBeanBuilder {

	private ValueContainer container = null;

	private FieldValueLoader valueLoader = null;

	private FormDataInfo formDataInfo;

	private FormBind bind = null;

	/**
	 * 设置属性
	 * 
	 * @param container
	 * @return
	 */
	public FormBeanBuilder container(ValueContainer container) {
		this.container = container;
		return this;
	}

	/**
	 * 设置属性
	 * 
	 * @param container
	 * @return
	 */
	public FormBeanBuilder formBind(FormBind bind) {
		this.bind = bind;
		return this;
	}

	public FormBeanBuilder valueLoader(FieldValueLoader valueLoader) {
		this.valueLoader = valueLoader;
		return this;
	}

	public FormBeanBuilder formInfo(FormDataInfo formDataInfo) {
		this.formDataInfo = formDataInfo;
		return this;
	}

	public FormBean build() throws FormException {
		FormBean formBean = init();
		for (FieldInfo field : formDataInfo.getFormFields()) {
			loadFieldValue(field, formBean);
		}
		return formBean;

	}

	private void loadFieldValue(FieldInfo field, FormBean formBean) {
		if (field.getIsConst() != null) {
			return;
		}
		Object obj = null;
		try {
			obj = valueLoader.loadValue(container, field.getField(), formBean,
					field.getParamId());
		} catch (FormException ex) {
			formBean.addFieldBindErrors(field.getField().getName(), ex);
		}
		formBean.addParamIdMapping(field.getName(), field.getParamIdAnno());
		if (obj == null||(obj.toString().trim().equals(""))) {
			return;
		}
		try {
			field.setFieldValue(formBean.getData(), obj);
		} catch (FormException ex) {
			formBean.addFieldBindErrors(field.getField().getName(),
					new FormException(FormProcessErrorCode.ERROR_FIELDVALUSET,
							ex));
		}
	}

	/**
	 * 初始化表单信息
	 * 
	 * @param formBean
	 * @param formInfo
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	private FormBean init() throws FormException {
		try {
			Class formClass = bind.formBean();
			FormInfo formInfo = (FormInfo) formClass
					.getAnnotation(FormInfo.class);
			FormBean formBean = new FormBean();
			Object dataBean = formClass.newInstance();
			formBean.setAction(formInfo.action());
			formBean.setDomain(formInfo.domain());
			formBean.setFormName(formInfo.formName());
			formBean.setData(dataBean);
			return formBean;
		} catch (Exception ex) {
			throw new FormException(FormProcessErrorCode.ERROR_INITFORMBEAN, ex);
		}
	}
}
