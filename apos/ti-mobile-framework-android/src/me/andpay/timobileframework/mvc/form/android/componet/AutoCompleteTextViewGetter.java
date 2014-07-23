package me.andpay.timobileframework.mvc.form.android.componet;

import android.view.View;
import android.widget.AutoCompleteTextView;
import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;

public class AutoCompleteTextViewGetter implements WidgetValueGetter{

	
	public String getWidgetValue(View widget) {
		CharSequence squence = ((AutoCompleteTextView)widget).getText();
		return squence == null ? null : squence.toString();
	}

	public String supportType() {
		return AutoCompleteTextView.class.getName();
	}
}
