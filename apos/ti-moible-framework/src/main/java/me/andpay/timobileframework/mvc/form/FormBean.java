package me.andpay.timobileframework.mvc.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;

/**
 * formBean 表单映射信息
 * 
 * @author tinyliu
 * 
 */
public class FormBean {

	private String domain;

	private String action;

	private String formName;

	private Map<String, ParamId> idMaps = new HashMap<String, ParamId>();

	private Object data = null;

	private Map<String, ValidateErrorInfo> errors = new HashMap<String, ValidateErrorInfo>();

	private Map<String, FormException> fieldBindErrors = new HashMap<String, FormException>();

	public Map<String, FormException> getFieldBindErrors() {
		return fieldBindErrors;
	}

	public FormException getFieldBindError(String fieldName) {
		return fieldBindErrors.get(fieldName);
	}

	public void addFieldBindErrors(String fieldName, FormException ex) {
		fieldBindErrors.put(fieldName, ex);
	}

	public void addErrors(List<ValidateErrorInfo> errorInfos) {
		for (ValidateErrorInfo error : errorInfos) {
			errors.put(error.getFieldName(), error);
		}
	}

	public void addParamIdMapping(String fieldName, ParamId paramId) {
		if (paramId == null) {
			return;
		}
		this.idMaps.put(fieldName, paramId);
	}

	public ParamId getParamId(String fieldName) {
		return this.idMaps.get(fieldName);
	}

	public ValidateErrorInfo[] getErrors() {
		return errors.values().toArray(
				new ValidateErrorInfo[errors.values().size()]);
	}

	public ValidateErrorInfo getError(String fieldName) {
		return errors.get(fieldName);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
