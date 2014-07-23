package me.andpay.timobileframework.mvc.form.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.andpay.timobileframework.mvc.form.annotation.ErrorCode;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.REQUIRED;

/**
 * 字段验证链条
 * 
 * @author tinyliu
 * 
 */
class FieldValidateChain {

	private List<FieldValidator> validateList = new ArrayList<FieldValidator>();

	private FieldValidateInfo info = null;

	FieldValidateChain(FieldValidateInfo info) {
		this.info = info;
		for (Class annoClass : info.getValidates().keySet()) {
			this.addValidator2Chain(annoClass);
		}
		if (info.isHasRequiredValidate()) {
			this.validateList.add(0,
					ValidatorContainer.getFieldValidator(REQUIRED.class));
		}
	}

	private void addValidator2Chain(Class annoClass) {
		validateList.add(ValidatorContainer.getFieldValidator(annoClass));
	}

	public ValidateErrorInfo validate(Object formdata, Field field, Object value) {
		for (FieldValidator validator : validateList) {
			Object[] args = info.getValidates().get(validator.support());
			if (!validator.validate(formdata, field, value, args)) {
				
				String errorStr = validator.getErrorCode();
				ErrorCode errorCode =  field.getAnnotation(ErrorCode.class);
				if(errorCode != null) {
					errorStr = errorCode.value();
				}
				ValidateErrorInfo error = new ValidateErrorInfo(
						field.getName(),errorStr );
				ParamId paramId = field.getAnnotation(ParamId.class);
				if(paramId!=null || paramId.value() >=0) {
					error.setParamId( paramId.value());
				}
				error.setErrorDescriptionParam(args);
				return error;
			}
		}
		return null;
	}
}