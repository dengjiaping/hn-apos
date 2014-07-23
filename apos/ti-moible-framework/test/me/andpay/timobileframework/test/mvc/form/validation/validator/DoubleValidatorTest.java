package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.DoubleValidator;

public class DoubleValidatorTest extends TestCase {

	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new DoubleValidator();
	}
	
	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 1234.34, null);
		assertTrue(flag);
	}
	
	public void testSucc_isInt() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 12, null);
		assertTrue(flag);
	}

	public void testSucc_withDoubleStr() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "1234.34", null);
		assertTrue(flag);
	}
	
	public void testFailed_notDoubleStr() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "asd123", null);
		assertFalse(flag);
	}
}
