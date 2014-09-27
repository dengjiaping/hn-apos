package me.andpay.apos.cmview.getter;

import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.timobileframework.mvc.form.android.WidgetValueGetter;
import android.view.View;
import android.widget.EditText;

public class AmtEditTextViewGetter implements WidgetValueGetter {

	public String getWidgetValue(View widget) {
		CharSequence squence = ((EditText) widget).getText();
		return squence == null ? null : squence.toString();
	}

	public String supportType() {
		return AmtEditTextView.class.getName();
	}
}
