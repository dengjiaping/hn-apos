package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.EmailValidator;

public class EmailValidatorTest extends TestCase {

	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new EmailValidator();
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "tiny.liu@sina.com", null);
		assertTrue(flag);
	}

	public void testFailed() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "123413", null);
		assertFalse(flag);
	}
}
