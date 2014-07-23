package me.andpay.timobileframework.mvc.form.android;

/**
 * 控件值获取接口
 * 自定义组件可以实现该接口，组件实现接口，在表单参数值加载的时候会直接调用getValue方法来获取组件值。
 * 优先级别高于WidgetValueGetter
 * @author tinyliu
 *
 */
public interface WidgetValueHolder {

	public Object  getWidgetValue();
	
}
