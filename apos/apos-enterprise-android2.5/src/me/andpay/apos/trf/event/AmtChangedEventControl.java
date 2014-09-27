package me.andpay.apos.trf.event;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import me.andpay.apos.R;
import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.text.Editable;

public class AmtChangedEventControl extends AbstractEventController {
	private final NumberFormat format = NumberFormat
			.getCurrencyInstance(Locale.CHINA);
	private String oldText;
	private String newText;
	private boolean needChange = true;

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		if (!needChange)
			return;
		PayeeInfomationActivity payeeActivity = (PayeeInfomationActivity) activity;
		newText = s.toString();
		String outputText;
		if (newText.length() > oldText.length()) {
			if (oldText.length() == 0) {
				BigDecimal tempAmt = new BigDecimal(newText);
				tempAmt = tempAmt.divide(new BigDecimal(100));
				outputText = StringConvertor.convert2Currency(tempAmt);
			} else {
				BigDecimal tempAmt = new BigDecimal(
						getBigDecimalString(newText));
				tempAmt = tempAmt.multiply(new BigDecimal(10));
				outputText = StringConvertor.convert2Currency(tempAmt);
			}
		} else {
			BigDecimal tempAmt = new BigDecimal(getBigDecimalString(newText));
			tempAmt = tempAmt.divide(new BigDecimal(10));
			if (tempAmt.compareTo(new BigDecimal(0.01)) == -1) {
				payeeActivity.amtEditTextView.setText("");
				payeeActivity.amtEditTextView.setHint(R.string.com_amt_str);
				return;
			} else {
				outputText = StringConvertor.convert2Currency(tempAmt);
			}
		}
		payeeActivity.amtEditTextView.setText(outputText);
		payeeActivity.amtEditTextView.setSelection(outputText.length());
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {
		oldText = s.toString();
		if (oldText.length() != 0 && newText.equals(oldText)) {
			needChange = false;
		} else {
			needChange = true;
		}
	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

	private String getBigDecimalString(String inputString) {
		try {
			Number num = format.parse(inputString);
			return String.valueOf(num);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}
}
