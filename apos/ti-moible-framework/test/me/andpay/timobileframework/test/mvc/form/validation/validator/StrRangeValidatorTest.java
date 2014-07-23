package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.STRRANGE;
import me.andpay.timobileframework.mvc.form.validation.validator.StrRangeValidator;

public class StrRangeValidatorTest extends TestCase {
	
	@FieldValidate.STRRANGE(max = 10, min = 5)
	private FieldValidator validator = null;
	
	public Object[] getStrRangeAnno() throws SecurityException, NoSuchFieldException {
		STRRANGE anno = StrRangeValidatorTest.class.getDeclaredField("validator").getAnnotation(STRRANGE.class);
		return new Object[]{anno.max(), anno.min()};
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = new StrRangeValidator();
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "wertyytr", getStrRangeAnno());
		assertTrue(flag);
	}

	public void testFailed() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "iyiuyiyiyinyiun", getStrRangeAnno());
		assertFalse(flag);
	}
	
	public void testFailed_null() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, null, getStrRangeAnno());
		assertFalse(flag);
	}
	
	public void testFailed_less() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "asdf", getStrRangeAnno());
		assertFalse(flag);
	}
}