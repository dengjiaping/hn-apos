package me.andpay.timobileframework.mvc.form.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.FormDataInfoResolver;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.FieldInfo;
import me.andpay.timobileframework.mvc.form.info.FormDataInfo;
import me.andpay.timobileframework.mvc.form.info.ValidateInfo;

/**
 * 用于操作form表单验证
 * 
 * @author tinyliu
 * 
 */
public class FormValidater {

	private Map<String, FieldValidateChain> caches = new HashMap<String, FieldValidateChain>();

	/**
	 * 主要接口，用于验证Form Bean的信息
	 * 
	 * @param formBean
	 * @param dynamicValidates
	 * @return
	 * @throws FormException
	 */
	public List<ValidateErrorInfo> validateFormBean(Object formBean)
			throws FormException {
		return validateFormBean(formBean, null);
	}

	/**
	 * 主要接口，用于验证Form Bean的信息
	 * 
	 * @param formBean
	 * @return
	 * @throws FormException
	 */
	public List<ValidateErrorInfo> validateFormBean(Object formBean,
			Map<String, List<ValidateInfo>> dynamicValidates)
			throws FormException {
		List<ValidateErrorInfo> errors = new ArrayList<ValidateErrorInfo>();
		FormDataInfo formDataInfo = FormDataInfoResolver.resolver(formBean
				.getClass());
		/**
		 * 判断存在自定义的form表单验证数据，如果存在直接返回处理结果
		 */
		if (formDataInfo.getValidator() != null) {
			return ValidatorContainer.getFormDataValidator(
					formDataInfo.getValidator().validator()).validateFormBean(
					formBean);
		}
		for (FieldInfo fieldInfo : formDataInfo.getFormFields()) {
			ValidateErrorInfo errorInfo = validateFormField(
					formBean,
					fieldInfo.getField().getName(),
					fieldInfo.getFieldValue(formBean),
					dynamicValidates != null
							&& dynamicValidates.containsKey(fieldInfo
									.getField().getName()) ? dynamicValidates
							.get(fieldInfo.getField().getName()) : null);
			if (errorInfo != null) {
				errors.add(errorInfo);
			}
		}
		return errors;
	}

	/**
	 * 直接验证校验框架
	 * 
	 * @param formBean
	 * @param validateRuleFieldName
	 * @param value
	 * @param dynamicValidates
	 * @return
	 */
	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value) {
		return validateFormField(formBean, validateRuleFieldName, value, null);
	}

	/**
	 * 直接验证校验框架
	 * 
	 * @param formBean
	 * @param validateRuleFieldName
	 * @param value
	 * @param dynamicValidates
	 * @return
	 */
	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value,
			List<ValidateInfo> dynamicValidates) {
		FormDataInfo info = FormDataInfoResolver.resolver(formBean.getClass());
		FieldInfo field = info.getFieldInfo(validateRuleFieldName);
		FieldValidateChain chain = getFieldValidateChain(formBean.getClass(),
				field, dynamicValidates);
		if (chain == null) {
			return null;
		}
		return chain.validate(formBean, field.getField(), value);
	}

	private FieldValidateChain getFieldValidateChain(Class formClass,
			FieldInfo field, List<ValidateInfo> dynamicValidates) {
		if (field.getFieldValidates().isEmpty()
				&& (dynamicValidates == null || dynamicValidates.isEmpty())) {
			return null;
		}
		FieldValidateInfo info = new FieldValidateInfo(field, dynamicValidates);
		if (dynamicValidates == null || dynamicValidates.isEmpty()) {
			String cacheKey = getCacheKey(formClass, field.getField().getName());
			if (!caches.containsKey(cacheKey)) {
				caches.put(cacheKey, new FieldValidateChain(info));
			}
			return caches.get(cacheKey);
		} else {
			return new FieldValidateChain(info);
		}

	}

	private String getCacheKey(Class formClass, String validateRuleFieldName) {
		return formClass.getName() + "_" + validateRuleFieldName;
	}
}
