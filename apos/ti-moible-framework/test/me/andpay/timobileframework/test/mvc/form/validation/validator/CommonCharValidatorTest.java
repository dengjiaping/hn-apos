package me.andpay.timobileframework.test.mvc.form.validation.validator;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.CommonCharValidator;

public class CommonCharValidatorTest extends TestCase{
	private FieldValidator validator = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = (FieldValidator) new CommonCharValidator();
	}

	public void testSucc() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "aaa_123", null);
		assertTrue(flag);
	}
	
	public void testSuccHasInt() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "123123_123", null);
		assertTrue(flag);
	}

	public void testFailed() throws SecurityException, NoSuchFieldException {
		Boolean flag = validator.validate(null, null, "我是中国人aaaa<>", null);
		assertFalse(flag);
	}

	public void testHasSpecialChar() throws SecurityException,
			NoSuchFieldException {
		Boolean flag = validator
				.validate(
						null,
						null,
						"abc123def_456处gh%^&i7理89_+【0结jkl-!@#果$*()_+【】、/，《》|{}[]",
						null);
		assertFalse(flag);
	}
}
