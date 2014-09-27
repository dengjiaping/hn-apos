package me.andpay.apos.cmview;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import me.andpay.timobileframework.util.StringConvertor;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class AmtEditTextView extends EditText implements WidgetValueHolder {

	private int hitColor;
	private String hitText;

	private boolean autoFontResize;

	public AmtEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.com_amtedit_view_attr);
		hitColor = typeArray.getColor(
				R.styleable.com_amtedit_view_attr_amtHitColor, 0x999999);
		hitText = typeArray
				.getString(R.styleable.com_amtedit_view_attr_amtHitText);
		setText(hitText);

	}

	@Override
	public void setText(CharSequence text, BufferType type) {

		if (hitText == null) {
			super.setText(text, type);
			return;
		}

		if (text.length() > 21) {
			super.setText(getEditableText(), type);
			return;
		}

		if (text == null || text.equals("") || text.equals(hitText)) {
			text = hitText;
		} else if (text.toString().indexOf("￥") < 0) {
			text = StringConvertor.convert2Currency(text.toString());
		} else {
			Editable textOld = getEditableText();

			if (textOld.length() == text.length() && textOld.equals(text)) {
				return;
			}

			if (text.length() == 1) {
				text = "￥0.0" + text;
				super.setText(text, type);
				return;
			}

			String[] textAmt = text.toString().split("\\.");
			if (textAmt.length < 2 || textAmt[1].length() == 2) {
				setColor(text);
				// Selection.setSelection(getEditableText(), text.length());
				super.setText(text, type);
				return;
			}

			NumberFormat format = NumberFormat
					.getCurrencyInstance(Locale.CHINA);
			Number numOld = null;
			try {
				numOld = format.parse(textOld.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (textOld.length() < text.length()) {
				String newChar = textAmt[1].substring(2);
				BigDecimal tempAmt = new BigDecimal(numOld.toString())
						.multiply(new BigDecimal("10")).add(
								new BigDecimal("0.01").multiply(new BigDecimal(
										newChar)));
				String textRe = format.format(tempAmt);
				text = textRe;
			} else {
				BigDecimal bigde = new BigDecimal(numOld.toString())
						.multiply(new BigDecimal("0.1"));
				bigde = bigde.setScale(2, BigDecimal.ROUND_DOWN);
				if (bigde.compareTo(new BigDecimal(0l)) == 0) {
					text = hitText;
				} else {
					String textRe = format.format(bigde);
					text = textRe;
				}

			}

			TextPaint mTextPaint = getPaint();
			float textWidth = mTextPaint.measureText("1");
			float with = getWidth();
			float textCount = with / textWidth;

			if (text.length() > textCount) {
				text = textOld;
			}
		}

		setColor(text);
		super.setText(text, type);
		this.setSelection(this.getText().toString().length());
	}

	public void clear() {
		setTextColor(hitColor);
		super.setText("");
	}

	private void setColor(CharSequence text) {
		if (text.equals(hitText)) {
			setTextColor(hitColor);
		} else {
			setTextColor(this.getResources().getColor(
					R.color.tqm_list_item_amount_col));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated me
		return super.onKeyDown(keyCode, event);
	}

	public Object getWidgetValue() {
		if (isBank()) {
			return "";
		}
		return getText().toString();
	}

	public boolean isBank() {
		if (hitText.equals(getText().toString())
				|| "0".equals(getText().toString())) {
			return true;
		}

		return false;
	}

}
