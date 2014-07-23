package me.andpay.timobileframework.mvc.form.validation;

import java.util.List;

import me.andpay.timobileframework.mvc.form.exception.FormException;

/**
 * 自定义表单验证接口
 * 
 * 定义此接口后表单数据校验全部交由定义接口
 * 
 * @author tinyliu
 *
 */
public interface FormDataValidator {
	/**
	 * 主要接口，用于验证Form Bean的信息
	 * 
	 * @param formBean
	 * @return
	 * @throws FormException
	 */
	public List<ValidateErrorInfo> validateFormBean(Object formBean)
			throws FormException;
}
