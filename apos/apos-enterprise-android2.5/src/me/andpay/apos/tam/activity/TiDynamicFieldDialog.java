package me.andpay.apos.tam.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.common.util.DynamicFieldHelper;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TiDynamicFieldDialog implements OnClickListener {

	private DynamicFieldHelper helper;

	private Dialog dialog = null;

	private DynamicDialogDelegate delegate;

	private String txnType;

	public TiDynamicFieldDialog(Context context, DynamicFieldHelper helper, String txnType) {
		this.helper = helper;
		dialog = new Dialog(context, R.style.dynamicDialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.com_dynamic_input_dialog_layout);
		dialog.findViewById(R.id.com_dialog_sure_layout).setOnClickListener(this);
		dialog.findViewById(R.id.com_dialog_cancel_layout).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						dismiss();
					}
				});
		this.setCancelable(false);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		this.txnType = txnType;
	}

	/**
	 * 设置动态字段
	 * 
	 * @param fieldValues
	 */
	public void setFieldValue(Map<String, String> fieldValues) {
		String orderid = null;
		String memo = null;
		if (fieldValues != null && !fieldValues.isEmpty()) {
			orderid = fieldValues.get(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO);
			memo = fieldValues.get(FlexFieldDefine.FIELD_NAME_MEMO);
		}
		setDynamicField(R.id.com_prompt_dialog_orderid_tv, R.id.solid_img, orderid,
				FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO);
		setDynamicField(R.id.com_prompt_dialog_memo_tv, R.id.solid1_img, memo,
				FlexFieldDefine.FIELD_NAME_MEMO);
		setSureColor();
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public Dialog getDialog() {
		return this.dialog;
	}

	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}

	public void setDelegate(DynamicDialogDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * 设置动态字段
	 * 
	 * @param textId
	 * @param imageId
	 * @param values
	 * @param fieldDefine
	 */
	private void setDynamicField(int textId, int imageId, String values,
			String fieldDefine) {
		TextView tc = (TextView) dialog.findViewById(textId);
		ImageView img = (ImageView) dialog.findViewById(imageId);
		helper.setViewShowStatus(fieldDefine, txnType, tc);
		img.setVisibility(tc.getVisibility());
		if (tc.getVisibility() == View.VISIBLE) {
			helper.setTextViewHide(tc, fieldDefine, txnType);
			tc.setText(values);
			/*
			 * tc.addTextChangedListener(new TextWatcher() {
			 * 
			 * public void onTextChanged(CharSequence s, int start, int before,
			 * int count) { }
			 * 
			 * public void beforeTextChanged(CharSequence s, int start, int
			 * count, int after) {
			 * 
			 * }
			 * 
			 * public void afterTextChanged(Editable s) {
			 * 
			 * setSureColor(); } });
			 */
		}
	}

	public interface DynamicDialogDelegate {
		public void sureButtonClick(Map<String, String> values);
	}

	private void setSureColor() {
		// String orderid = ((TextView) dialog
		// .findViewById(R.id.com_prompt_dialog_orderid_tv)).getText().toString();
		// String memo = ((TextView)
		// dialog.findViewById(R.id.com_prompt_dialog_memo_tv))
		// .getText().toString();
		/*
		 * if (!StringUtil.isEmpty(orderid) || !StringUtil.isEmpty(memo)) {
		 * ((TextView)
		 * dialog.findViewById(R.id.com_dialog_sure_tv)).setTextColor(this
		 * .getDialog().getContext().getResources()
		 * .getColor(R.color.com_title_normal_col)); } else { ((TextView)
		 * dialog.findViewById(R.id.com_dialog_sure_tv)).setTextColor(this
		 * .getDialog().getContext().getResources()
		 * .getColor(R.color.com_button_top_col)); }
		 */
	}

	public void onClick(View v) {
		String orderid = ((TextView) dialog
				.findViewById(R.id.com_prompt_dialog_orderid_tv)).getText().toString();
		String memo = ((TextView) dialog.findViewById(R.id.com_prompt_dialog_memo_tv))
				.getText().toString();
		Map<String, String> values = new HashMap<String, String>();
		values.put(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO, orderid);
		values.put(FlexFieldDefine.FIELD_NAME_MEMO, memo);
		if (delegate != null)
			delegate.sureButtonClick(values);
		this.dismiss();
	}

}
