package me.andpay.timobileframework.test.mvc.form;

import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;

@FormBind(formBean = TestFormData.class)
public class MockContainer implements ValueContainer{
}
