package me.andpay.timobileframework.mvc.form.android;

import android.view.View;

/**
 * widget组件vlaue获取接口
 * 
 * @author tinyliu
 *
 */
public interface WidgetValueGetter {
	
	public Object getWidgetValue(View widget);
	/**
	 * 支持控件的类全名，需要包含全包路径
	 * @return
	 */
	public String supportType();
}
