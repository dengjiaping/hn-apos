package me.andpay.apos.cmview;

import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class TiRadioGroup extends RadioGroup implements WidgetValueHolder {

	private int NOT_CHECK_RADIO = -1;

	public TiRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Object getWidgetValue() {
		int checkedId = this.getCheckedRadioButtonId();
		if (checkedId == NOT_CHECK_RADIO) {
			return null;
		}
		return ((TiRadioButton) this.findViewById(checkedId)).getWidgetValue();
	}

}
