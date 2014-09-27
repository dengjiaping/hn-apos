package me.andpay.apos.cmview;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class TiRadioButton extends RadioButton implements WidgetValueHolder {

	private String value = null;

	public TiRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.custom);
		value = typedArray.getString(R.styleable.custom_value);
	}

	public Object getWidgetValue() {
		return value;
	}

}
