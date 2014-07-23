package me.andpay.timobileframework.mvc.form.android.componet;

import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.RadioGroup;


public class RadioGroupValueGetter implements WidgetValueGetter {

	public Object getWidgetValue(View widget) {
		RadioGroup rg = (RadioGroup)widget;
		return rg.getCheckedRadioButtonId();
	}

	public String supportType() {
		return RadioGroup.class.getName();
	}


}
