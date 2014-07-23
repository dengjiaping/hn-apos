package me.andpay.timobileframework.mvc.form.android.componet;

import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.Spinner;


public class SpinnerValueGetter implements WidgetValueGetter{

	public Object getWidgetValue(View widget) {
		Spinner sp = (Spinner)widget;
		return sp.getSelectedItemId();
	}

	public String supportType() {
		return Spinner.class.getName();
	}

}
