package me.andpay.timobileframework.test.mvc.form;

import junit.framework.TestCase;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormBeanBuilder;
import me.andpay.timobileframework.mvc.form.FormDataInfoResolver;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.form.exception.FormException;

public class FormBeanBuilderTest extends TestCase {

	private MockContainer container = null;

	MockFieldValueLoader loader = null;
	
	FormBeanBuilder builder = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		container = new MockContainer();
		loader = new MockFieldValueLoader();
		builder = new FormBeanBuilder();
	}

	public void testBuildSucc() throws FormException {
		FormBean formBean = builder
				.container(container)
				.valueLoader(loader)
				.formInfo(
						FormDataInfoResolver.resolver(container
								.getClass().getAnnotation(FormBind.class)
								.formBean()))
				.formBind(container.getClass().getAnnotation(FormBind.class))
				.build();
		TestFormData data = (TestFormData) formBean.getData();
		assertTrue(data.getChineseStr().equals("测试数据")
				&& data.getRequired().equals("测试required数据") && data.getMask().equals("13766666666"));
	}
	
	public void testBuildSetErrorValue() throws FormException {
		FormBean formBean = builder
				.container(container)
				.valueLoader(loader)
				.formInfo(
						FormDataInfoResolver.resolver(container
								.getClass().getAnnotation(FormBind.class)
								.formBean()))
				.formBind(container.getClass().getAnnotation(FormBind.class))
				.build();
		TestFormData data = (TestFormData) formBean.getData();
		assertTrue(formBean.getFieldBindError("price").getErrorCode().equals(FormProcessErrorCode.ERROR_FIELDVALUSET));
	}

}
