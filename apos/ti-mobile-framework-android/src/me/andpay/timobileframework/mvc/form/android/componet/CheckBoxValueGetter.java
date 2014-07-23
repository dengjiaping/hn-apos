package me.andpay.timobileframework.mvc.form.android.componet;

import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.CheckBox;


public class CheckBoxValueGetter implements WidgetValueGetter{

	public Integer getWidgetValue(View widget) {
		return ((CheckBox)widget).isChecked() ? widget.getId() : null;
	}

	public String supportType() {
		return CheckBox.class.getName();
	}

}
