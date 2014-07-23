package me.andpay.timobileframework.test.mvc.form;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.DefaultFormProcesser;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.exception.FormException;

public class FormProcessTest extends TestCase {

	private MockContainer container = null;

	private MockFieldValueLoader loader = null;

	private FormProcesser processer = null;

	private String MOCK_PARTTERN = "mock";

	@Override
	protected void setUp() throws Exception {
		container = new MockContainer();
		loader = new MockFieldValueLoader();
		processer = new DefaultFormProcesser();
		processer.resigteFieldValueLoader("mock", loader);
		super.setUp();
	}

	public void testProcessSucc() throws FormException {
		FormBean formBean = processer.loadFormBeanAndValidate(container,
				MOCK_PARTTERN);
		TestFormData data = (TestFormData) formBean.getData();
		System.out.println(formBean.getErrors().length);
		assertTrue(data.getChineseStr().equals("测试数据")
				&& data.getRequired().equals("测试required数据")
				&& data.getMask().equals("13766666666")
				&& formBean.getFieldBindError("price").getErrorCode()
						.equals(FormProcessErrorCode.ERROR_FIELDVALUSET));
		
	}

}
