package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.IntegerValidator;

public class IntegerValidatorTest extends TestCase {

	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new IntegerValidator();
	}
	
	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 1234, null);
		assertTrue(flag);
	}

	public void testSucc_withIntStr() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "1234", null);
		assertTrue(flag);
	}

	public void testFailed_notInt() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 12.34, null);
		assertFalse(flag);
	}
	
	public void testFailed_notIntStr() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "12.34", null);
		assertFalse(flag);
	}
}