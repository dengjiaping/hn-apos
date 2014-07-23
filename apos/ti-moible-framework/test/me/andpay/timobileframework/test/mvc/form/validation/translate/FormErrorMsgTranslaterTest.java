package me.andpay.timobileframework.test.mvc.form.validation.translate;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import me.andpay.timobileframework.mvc.form.validation.translate.ErrorMsgTranslateSelector;
import me.andpay.timobileframework.mvc.form.validation.translate.FormErroMsgTranslater;
import me.andpay.timobileframework.test.mvc.form.TestFormData;
import me.andpay.timobileframework.test.mvc.form.TestFormData2;

public class FormErrorMsgTranslaterTest extends TestCase {
	private FormErroMsgTranslater translater = new FormErroMsgTranslater();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testSucc() throws FormException {
		TestFormData bean = new TestFormData();
		ValidateErrorInfo errorInfo = new ValidateErrorInfo("maxAge",
				FormProcessErrorCode.ERROR_VALIDATE_INTEGERRANGE);
		ValidateErrorInfo errorInfo1 = new ValidateErrorInfo("commonChar",
				FormProcessErrorCode.ERROR_VALIDATE_COMMONCHAR);
		translater.translateErrors(bean, new ValidateErrorInfo[] { errorInfo,
				errorInfo1 });
		assertTrue(errorInfo.getErrorDescription().equals(
				"Error Msg : Integer value out of the range")
				&& errorInfo1.getErrorDescription().equals(
						"Error Msg : commonChar is not valid"));
	}
	
	public void testSuccWithImplements() throws FormException {
		TestFormData2 bean = new TestFormData2();
		ErrorMsgTranslateSelector.registeImplTranslate(new ErrorMsgTranslaterTest());
		ValidateErrorInfo errorInfo = new ValidateErrorInfo("maxAge",
				FormProcessErrorCode.ERROR_VALIDATE_INTEGERRANGE);
		ValidateErrorInfo errorInfo1 = new ValidateErrorInfo("commonChar",
				FormProcessErrorCode.ERROR_VALIDATE_COMMONCHAR);
		translater.translateErrors(bean, new ValidateErrorInfo[] {errorInfo,
				errorInfo1 });
		assertTrue(errorInfo.getErrorDescription().equals(
				"testError")
				&& errorInfo1.getErrorDescription().equals(
						"testError"));
	}
	
	public void testSucc_withParams() {
		
	}
	
	public void testFailed_NotDescription() throws FormException {
		TestFormData bean = new TestFormData();
		ErrorMsgTranslateSelector.registeImplTranslate(new ErrorMsgTranslaterTest());
		ValidateErrorInfo errorInfo = new ValidateErrorInfo("maxAge",
				"111");
		ValidateErrorInfo errorInfo1 = new ValidateErrorInfo("commonChar",
				"222");
		translater.translateErrors(bean, new ValidateErrorInfo[] {errorInfo,
				errorInfo1 });
		assertTrue(errorInfo.getErrorDescription() == null
				&& errorInfo1.getErrorDescription() == null);
	}
}
