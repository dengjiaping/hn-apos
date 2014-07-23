package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.RequiredValidator;
import me.andpay.timobileframework.test.mvc.form.TestFormData;

public class RequiredValidatorTest extends TestCase {

	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new RequiredValidator();
		TestFormData data = new TestFormData();
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, new Object(), null);
		assertTrue(flag);
	}

	public void testFailed() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, null, null);
		assertFalse(flag);
	}
}