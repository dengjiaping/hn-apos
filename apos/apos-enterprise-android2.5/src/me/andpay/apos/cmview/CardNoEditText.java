package me.andpay.apos.cmview;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 银行卡卡号输入控件，每隔4位自动加入空格
 * 
 * @author tinyliu
 * 
 */
public class CardNoEditText extends EditText implements WidgetValueHolder {

	private Integer maxSize;

	public CardNoEditText(Context context) {
		this(context, null);
	}

	public CardNoEditText(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.editTextStyle);
	}

	public CardNoEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				me.andpay.apos.R.styleable.com_card_text_arrt);
		maxSize = typeArray.getInteger(
				me.andpay.apos.R.styleable.com_card_text_arrt_maxSize, 24);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (text == null || StringUtil.isEmpty(text.toString())) {
			super.setText(text, type);
			return;
		}

		if (text.length() > maxSize) {
			super.setText(text.subSequence(0, maxSize));
			return;
		}

		StringBuffer cards = new StringBuffer();
		String textTrim = text.toString().replace(" ", "");
		int length = textTrim.length();
		int start = 0;
		int end = 4;
		if (length < 5) {
			cards = cards.append(textTrim);
		} else {
			while (start < length) {
				cards.append(textTrim.subSequence(start, end)).append(" ");
				start += 4;
				end += 4;
				if (end > length) {
					end = length;
				}
			}
		}

		super.setText(cards.toString().trim(), type);
		setSelection(cards.toString().trim().length());

	}

	public Object getWidgetValue() {
		return this.getText().toString().replaceAll(" ", "");
	}

	// public void setOrignalStr(String orignalStr) {
	// this.orignalStr = orignalStr;
	// }

}
