package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.INTEGERRANGE;
import me.andpay.timobileframework.mvc.form.validation.validator.IntegerRangeValidator;

public class IntegerRangeValidatorTest extends TestCase{
	@FieldValidate.INTEGERRANGE(max = 20, min = 12)
	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new IntegerRangeValidator();
	}
	
	public Object[] getRangeAnno() throws SecurityException, NoSuchFieldException {
		INTEGERRANGE anno = IntegerRangeValidatorTest.class.getDeclaredField("validator").getAnnotation(INTEGERRANGE.class);	
		return new Object[]{anno.max(), anno.min()};
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "15", getRangeAnno());
		assertTrue(flag);
	}
	
	public void testSucc_ValueIsNull() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, null, getRangeAnno());
		assertTrue(flag);
	}
	
	public void testFailed_ValueIsDouble() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 13.12, getRangeAnno());
		assertFalse(flag);
	}

	public void testFailed_outRange() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "70", getRangeAnno());
		assertFalse(flag);
	}
	
	public void testFailed_valueNotInteger() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "str", getRangeAnno());
		assertFalse(flag);
	}
}