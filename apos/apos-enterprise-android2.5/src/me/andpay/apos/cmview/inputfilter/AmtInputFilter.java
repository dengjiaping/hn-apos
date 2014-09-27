package me.andpay.apos.cmview.inputfilter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import me.andpay.ti.util.StringUtil;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class AmtInputFilter implements InputFilter {

	public EditText editText;

	public AmtInputFilter(EditText editText) {
		this.editText = editText;
	}

	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {

		if (source.length() > 1) {
			return source;
		}

		try {
			NumberFormat format = NumberFormat
					.getCurrencyInstance(Locale.CHINA);
			String souStr = null;
			// 删除健
			if (StringUtil.isBlank(source.toString())) {
				if (StringUtil.isBlank(dest.toString())) {
					return dest;
				}
				Number number = format.parse(dest.toString());
				Double destD = number.doubleValue() * 100;
				souStr = String.valueOf(destD.intValue()).substring(0,
						destD.toString().length() - 1);

			} else {
				if (StringUtil.isBlank(dest.toString())) {
					return format
							.format(Double.valueOf(source.toString()) * 0.01);
				}
				Number number = format.parse(dest.toString());
				Double destD = number.doubleValue() * 100;
				souStr = String.valueOf(destD.intValue()) + source;
			}
			String ss = format.format(Double.valueOf(souStr) * 0.01);
			editText.setText(ss);
			return "";

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

}
