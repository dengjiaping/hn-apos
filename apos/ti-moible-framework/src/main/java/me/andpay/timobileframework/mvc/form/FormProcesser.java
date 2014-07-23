package me.andpay.timobileframework.mvc.form;

import java.util.List;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.ValidateInfo;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;

/**
 * 表单处理接口 用于表单数据的加载与验证
 * 
 * @author tinyliu
 * 
 */
public interface FormProcesser {

	public static interface FormProcessPattern {
		public static String ANDROID = "Android_form_pattern";
	}

	/**
	 * load Form value，create Form Bean but not validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBean(ValueContainer container, String processPattern)
			throws FormException;

	/**
	 * load Form value，create Form Bean and validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBeanAndValidate(ValueContainer container,
			Map<String, List<ValidateInfo>> dynamicValidate,
			String processPattern) throws FormException;

	/**
	 * load Form value，create Form Bean and validate values
	 * 
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public FormBean loadFormBeanAndValidate(ValueContainer container,
			String processPattern) throws FormException;

	/**
	 * validate Form value and translate error info
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	public List<ValidateErrorInfo> validateForm(Object formData,
			Map<String, List<ValidateInfo>> dynamicValidates) throws FormException;
	
	/**
	 * validate Form value and translate error info
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	public List<ValidateErrorInfo> validateForm(Object formData) throws FormException;
	
	/**
	 * validate Form value and translate error info
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value);

	/**
	 * validate Form value and translate error info
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	public ValidateErrorInfo validateFormField(Object formBean,
			String validateRuleFieldName, Object value,
			List<ValidateInfo> dynamicValidates);

	/**
	 * register fieldValueLoader
	 * 
	 * @param processModel
	 * @param looder
	 */
	public void resigteFieldValueLoader(String processModel,
			FieldValueLoader looder);

}
