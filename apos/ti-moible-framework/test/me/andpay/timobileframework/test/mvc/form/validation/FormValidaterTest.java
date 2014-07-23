package me.andpay.timobileframework.test.mvc.form.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.ValidateInfo;
import me.andpay.timobileframework.mvc.form.validation.FormValidater;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.INTEGER;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.STRRANGE;
import me.andpay.timobileframework.test.mvc.form.TestFormData;

public class FormValidaterTest extends TestCase {

	private FormValidater operator = new FormValidater();

	private FormBean bean = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bean = new FormBean();
		bean.setData(new TestFormData());
	}

	private void addError2Bean(List<ValidateErrorInfo> errors) {
			bean.addErrors(errors);
	}

	public void testSucc_withDynamic() throws FormException {
		ValidateInfo info = new ValidateInfo(STRRANGE.class, new Object[]{10, 1});
		Map<String, List<ValidateInfo>> dynamicValidates = new HashMap<String, List<ValidateInfo>>();
		List<ValidateInfo> validates = new ArrayList<ValidateInfo>();
		validates.add(info);
		dynamicValidates.put("equalsValue", validates);
		addError2Bean(operator.validateFormBean(bean.getData(), dynamicValidates));
		assertTrue(bean.getErrors().length == 0);
	}
	
	public void testSucc() throws FormException {
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 0);
	}

	public void testSucc_byField() throws FormException {
		ValidateErrorInfo error = operator.validateFormField(bean.getData(),
				"chineseStr", "我是中国人");
		assertTrue(error == null);
	}
	
	public void testSucc_byField_withDynamic() throws FormException {
		ValidateInfo info = new ValidateInfo(STRRANGE.class, new Object[]{10, 1});
		List<ValidateInfo> validates = new ArrayList<ValidateInfo>();
		validates.add(info);
		ValidateErrorInfo error = operator.validateFormField(bean.getData(),
				"chineseStr", "我是中国人", validates);
		assertTrue(error == null);
	}
	
	public void testSucc_byField_with2Dynamic() throws FormException {
		ValidateInfo info = new ValidateInfo(STRRANGE.class, new Object[]{10, 1});
		ValidateInfo info2 = new ValidateInfo(INTEGER.class, null);
		List<ValidateInfo> validates = new ArrayList<ValidateInfo>();
		validates.add(info);
		validates.add(info2);
		ValidateErrorInfo error = operator.validateFormField(bean.getData(),
				"chineseStr", "我是中国人", validates);
		assertTrue(error.getErrorCode().equals(FormProcessErrorCode.ERROR_VALIDATE_INTEGER));
	}

	public void testFailed_byField() throws FormException {
		ValidateErrorInfo error = operator.validateFormField(bean.getData(),
				"chineseStr", "1234");
		assertTrue(error != null
				&& error.getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_CHINESECHAR));
	}

	public void testFailed_isNotChinese() throws FormException {
		((TestFormData) bean.getData()).setChineseStr("12314");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_CHINESECHAR));
	}

	public void testFailed_phoneNumber() throws FormException {
		((TestFormData) bean.getData()).setPhone("12314");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_PHONENUMBER));
	}

	public void testFailed_email() throws FormException {
		((TestFormData) bean.getData()).setEmail("12314");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_EMAIL));
	}

	public void testFailed_required() throws FormException {
		((TestFormData) bean.getData()).setRequired(null);
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_REQUIRED));
	}

	public void testFailed_equals() throws FormException {
		((TestFormData) bean.getData()).setEquals("123");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_EQUALSFIELD));
	}

	public void testFailed_notCommonChar() throws FormException {
		((TestFormData) bean.getData()).setCommonChar("123@2fsf--");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_COMMONCHAR));
	}

	public void testFailed_composite() throws FormException {
		((TestFormData) bean.getData()).setEquals("123@2fsf--");
		((TestFormData) bean.getData()).setEqualsValue("123@2fsf--");
		addError2Bean(operator.validateFormBean(bean.getData()));
		assertTrue(bean.getErrors().length == 1
				&& bean.getErrors()[0].getErrorCode().equals(
						FormProcessErrorCode.ERROR_VALIDATE_COMMONCHAR));
	}
}
