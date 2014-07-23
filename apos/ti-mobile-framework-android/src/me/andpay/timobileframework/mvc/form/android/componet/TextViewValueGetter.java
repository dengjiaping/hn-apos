package me.andpay.timobileframework.mvc.form.android.componet;

import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.TextView;


public class TextViewValueGetter implements WidgetValueGetter {
	
	public String getWidgetValue(View widget) {
		CharSequence squence = ((TextView)widget).getText();
		return squence == null ? null : squence.toString();
	}
	
	public String supportType() {
		return TextView.class.getName();
	}

}
