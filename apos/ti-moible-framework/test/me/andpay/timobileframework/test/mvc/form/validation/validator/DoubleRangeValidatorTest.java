package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.DOUBLERANGE;
import me.andpay.timobileframework.mvc.form.validation.validator.DoubleRangeValidator;


public class DoubleRangeValidatorTest extends TestCase{
	@FieldValidate.DOUBLERANGE(max = 20, min = 12)
	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new DoubleRangeValidator();
	}
	
	public Object[] getDoubleRangeAnno() throws SecurityException, NoSuchFieldException {
		DOUBLERANGE anno = DoubleRangeValidatorTest.class.getDeclaredField("validator").getAnnotation(DOUBLERANGE.class);
		return new Object[]{anno.max(), anno.min()};
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "15.123", getDoubleRangeAnno());
		assertTrue(flag);
	}
	
	public void testSucc_ValueIsNull() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, null, getDoubleRangeAnno());
		assertTrue(flag);
	}
	
	public void testSucc_ValueIsInteger() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, 15, getDoubleRangeAnno());
		assertTrue(flag);
	}

	public void testFailed_outRange() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "70", getDoubleRangeAnno());
		assertFalse(flag);
	}
	
	public void testFailed_valueNotDouble() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "str", getDoubleRangeAnno());
		assertFalse(flag);
	}
}
