package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.EQUALSFIELD;
import me.andpay.timobileframework.mvc.form.validation.validator.EqualsFieldValidator;
import me.andpay.timobileframework.test.mvc.form.TestFormData;

public class EqualsFieldValidatorTest extends TestCase{
	
	private FieldValidator validator = null;
	
	private TestFormData data = new TestFormData();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		data.setEqualsValue("test");
		data.setEquals("test");
		validator = (FieldValidator) new EqualsFieldValidator();
	}
	
	protected Object[] getAnnoArgs(Object data) throws SecurityException, NoSuchFieldException {
		return new Object[] {data.getClass().getDeclaredField("equals").getAnnotation(EQUALSFIELD.class).paramName()};
	}
	

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(data, null, data.getEquals(), getAnnoArgs(data));
		assertTrue(flag);
	}
	
	public void testFailed() throws SecurityException, NoSuchFieldException {
		data.setEqualsValue("not equals");
		Boolean flag = validator.validate(data, null, data.getEquals(), getAnnoArgs(data));
		assertFalse(flag);
	}

	public void testFailed_isNull() throws SecurityException, NoSuchFieldException {
		data.setEquals(null);
		Boolean flag = validator.validate(data, null, data.getEquals(), getAnnoArgs(data));
		assertFalse(flag);
	}
	
	public void testSucc_allNull() throws SecurityException, NoSuchFieldException {
		data.setEquals(null);
		data.setEqualsValue(null);
		Boolean flag = validator.validate(data, null, data.getEquals(), getAnnoArgs(data));
		assertTrue(flag);
	}
}