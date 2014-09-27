package me.andpay.apos.cmview;

import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import me.andpay.timobileframework.util.StringConvertor;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class TiTxnIdEditText extends EditText implements WidgetValueHolder {

	public TiTxnIdEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s == null || s.equals("") || s.length() == 12
						|| s.length() == 0) {
					return;
				}

				Long strLong = Long.valueOf(s.toString());
				s = StringConvertor.format("000000000000",
						strLong % 1000000000000l);
				if ("000000000000".equals(s)) {
					setText("");
					return;
				}

				setText(s);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (text == null) {
			return;
		}
		super.setText(text, type);
		Selection.setSelection(getEditableText(), text.length());
	}

	public Object getWidgetValue() {
		// TODO Auto-generated method stub
		return getText().toString();
	}

}
