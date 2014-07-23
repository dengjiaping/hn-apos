package me.andpay.apos.cmview;

import java.util.Calendar;

import me.andpay.apos.R;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 日期选择控件
 * 
 * @author tinyliu
 * 
 */
public class TiDatePickerView extends EditText implements OnClickListener,
		OnFocusChangeListener, WidgetValueHolder {

	private DatePicker picker;

	public static final String split = "/";

	private Dialog dialog;

	public TiDatePickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnClickListener(this);
		this.setInputType(InputType.TYPE_CLASS_NUMBER);
		this.setOnFocusChangeListener(this);
		dialog = new Dialog(this.getContext(), R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.com_time_select_layout);
		picker = (DatePicker) dialog
				.findViewById(R.id.com_time_select_date_picker);
		dialog.findViewById(R.id.com_time_select_sure_btn).setOnClickListener(
				new SureOnClick());
		dialog.findViewById(R.id.com_time_select_cancel_btn)
				.setOnClickListener(new CancelOnClick());
		setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				EditText editText = (EditText) v;
				editText.setInputType(InputType.TYPE_NULL); // 关闭软键盘
				return false;
			}
		});
		

	}

	class SureOnClick implements OnClickListener {

		public void onClick(View v) {
			setText(picker.getYear() + split + (picker.getMonth() + 1) + split
					+ picker.getDayOfMonth());
			dialog.dismiss();

		}

	}

	class CancelOnClick implements OnClickListener {

		public void onClick(View v) {
			dialog.dismiss();

		}

	}

	public Object getWidgetValue() {
		return this.getText().toString();
	}

	public void onClick(View v) {
		// InputMethodManager imm =
		// ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
		// imm.hideSoftInputFromWindow(this.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

		showDateDialog();

	}

	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			showDateDialog();
		}
	}

	public DatePicker getPicker() {
		return picker;
	}

	public void showDateDialog() {

		int year = 0;
		int month = 0;
		int dayOfMonth = 0;

		String dateStr = getText().toString();
		if (StringUtil.isBlank(dateStr)) {
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		} else {
			String[] dates = dateStr.split(split);
			year = Integer.valueOf(dates[0]);
			month = Integer.valueOf(dates[1]) - 1;
			dayOfMonth = Integer.valueOf(dates[2]);

		}
		picker.updateDate(year+1, month, dayOfMonth);
		picker.updateDate(year, month, dayOfMonth);
		dialog.show();
	}

}
