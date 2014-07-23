package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.MASK;
import me.andpay.timobileframework.mvc.form.validation.validator.MaskValidator;

public class MaskValidatorTest extends TestCase {

	@FieldValidate.MASK(pattern = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$")
	private FieldValidator validator = null;

	public Object[] getAnno() throws SecurityException, NoSuchFieldException {
		return new Object[] { MaskValidatorTest.class
				.getDeclaredField("validator").getAnnotation(MASK.class)
				.pattern() };
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new MaskValidator();
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "13761786363", getAnno());
		assertTrue(flag);
	}

	public void testSucc_valueIsNull() throws SecurityException,
			NoSuchFieldException {
		Boolean flag = validator.validate(null, null, null, getAnno());
		assertTrue(flag);
	}

	public void testFailed() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator
				.validate(null, null, "137617863631", getAnno());
		assertFalse(flag);
	}

	public void testFailed_notNumber() throws SecurityException,
			NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "sfsafas", getAnno());
		assertFalse(flag);
	}
}
