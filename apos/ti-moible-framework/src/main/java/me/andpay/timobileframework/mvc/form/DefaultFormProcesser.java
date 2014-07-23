package me.andpay.timobileframework.mvc.form;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.ValidateInfo;
import me.andpay.timobileframework.mvc.form.validation.FormValidater;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import me.andpay.timobileframework.mvc.form.validation.translate.FormErroMsgTranslater;

@SuppressWarnings({ "rawtypes" })
public class DefaultFormProcesser implements FormProcesser{

	private Map<String, FieldValueLoader> loaders = new ConcurrentHashMap<String, FieldValueLoader>();
	
	private FormValidater validater = null;
	
	private FormErroMsgTranslater translater = null;

	public DefaultFormProcesser() { 
		validater = new FormValidater();
		translater = new FormErroMsgTranslater();
	}

	/**
	 * load Form value，create Form Bean but not validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBean(ValueContainer container, String processPattern)
			throws FormException {
		FieldValueLoader loader = loaders.get(processPattern);
		if (loader == null) {
			throw new FormException(FormProcessErrorCode.ERROR_NOTDEFINE);
		}
		FormBind bind = container.getClass().getAnnotation(FormBind.class);
		if (bind == null) {
			throw new FormException(FormProcessErrorCode.ERROR_NOTDEFINE);
		}
		return new FormBeanBuilder()
				.formBind(bind)
				.formInfo(FormDataInfoResolver.resolver(bind.formBean()))
				.container(container)
				.valueLoader(loader)
				.build();
	}

	/**
	 * load Form value，create Form Bean and validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBeanAndValidate(ValueContainer container,
			String processPattern) throws FormException {
		return loadFormBeanAndValidate(container, null, processPattern);
	}
	/**
	 * load Form value，create Form Bean and validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBeanAndValidate(ValueContainer container,
			Map<String, List<ValidateInfo>> dynamicValidate, String processPattern)
			throws FormException {
		FormBean formBean = loadFormBean(container, processPattern);
		List<ValidateErrorInfo> errors = validater.validateFormBean(formBean.getData(), dynamicValidate);
		if(errors != null && !errors.isEmpty()) {
			formBean.addErrors(errors);
			translater.translateErrors(formBean.getData(), formBean.getErrors());
		}
		
		return formBean;
	}

	/**
	 * registe FieldValue
	 * 
	 * @param containerType
	 * @param looder
	 */
	public void resigteFieldValueLoader(String processModel,
			FieldValueLoader looder) {
		this.loaders.put(processModel, looder);
	}

	public List<ValidateErrorInfo> validateForm(Object formData,
			Map<String, List<ValidateInfo>> dynamicValidates) throws FormException {
		List<ValidateErrorInfo> errors = validater.validateFormBean(formData, dynamicValidates);
		translater.translateErrors(formData, errors.toArray(new ValidateErrorInfo[errors.size()]));
		return errors;
	}

	public List<ValidateErrorInfo> validateForm(Object formData)
			throws FormException {
		return validateForm(formData, null);
	}

	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value) {
		return validateFormField(formBean, validateRuleFieldName, value, null);
	}

	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value,
			List<ValidateInfo> dynamicValidates) {
		return validater.validateFormField(formBean, validateRuleFieldName, value, dynamicValidates);
	}
	
	public static void main(String [] args) {
		DefaultFormProcesser processer = new DefaultFormProcesser();
		System.out.print(processer.getClass().getSuperclass().isAssignableFrom(FormProcesser.class));
	}
}
