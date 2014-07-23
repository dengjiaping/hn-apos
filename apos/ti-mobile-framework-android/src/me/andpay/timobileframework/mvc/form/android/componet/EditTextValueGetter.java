package me.andpay.timobileframework.mvc.form.android.componet;

import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.EditText;


public class EditTextValueGetter implements WidgetValueGetter{

	public String getWidgetValue(View widget) {
		CharSequence squence = ((EditText)widget).getText();
		return squence == null ? null : squence.toString();
	}

	public String supportType() {
		return EditText.class.getName();
	}

}
