//package me.andpay.apos.cmview;
//
//import java.util.Map;
//
//import me.andpay.apos.R;
//import me.andpay.apos.cmview.TiSpinner.TiSpinnerInit;
//import me.andpay.apos.cmview.TiSpinner.TxnTypesCheckedChangeListener;
//import me.andpay.timobileframework.cache.HashMap;
//import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
//import android.app.Dialog;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class TiNewSpinner extends EditText implements OnClickListener,
//		OnFocusChangeListener, WidgetValueHolder {
//
//	private Dialog dialog = null;
//
//	private String value = null;
//
//	private Integer textId = null;
//
//
//	public TiNewSpinner(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.setOnClickListener(this);
//		this.setOnFocusChangeListener(this);
//		dialog = new Dialog(context, R.style.Theme_dialog_style);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	}
//
//	public void setLayoutAndInit(int layoutId, TiSpinnerInit tiSpinnerInit) {
//		dialog.setContentView(layoutId);
////		tiSpinnerInit.initData(itemIds, itemImgs, itemValues, itemStrs);
////
////		for (Integer itemId : itemIds.keySet()) {
////			dialog.findViewById(itemId).setOnClickListener(
////					new TxnTypesCheckedChangeListener());
////		}
//	}
//
//	public void onClick(View v) {
//		dialog.show();
//	}
//
//	public void onFocusChange(View v, boolean hasFocus) {
//		if (hasFocus) {
//			dialog.show();
//		}
//
//	}
//
//	class TxnTypesCheckedChangeListener implements OnClickListener {
//
//		public void onClick(View v) {
//			showSelect(v.getId());
//		}
//
//	}
//
//	public void showSelect(int viewId) {
//		for (Integer itemId : itemIds.keySet()) {
//			TextView text = (TextView) dialog.findViewById(itemIds.get(itemId));
//			ImageView view = (ImageView) dialog.findViewById(itemImgs
//					.get(itemId));
//			if (itemId.intValue() == viewId) {
//				text.setTextColor(getResources().getColor(
//						R.color.com_bule_color));
//				view.setVisibility(View.VISIBLE);
//				value = itemValues.get(itemId);
//				textId = itemStrs.get(itemId);
//			} else {
//				text.setTextColor(getResources().getColor(
//						R.color.com_title_normal_col));
//				view.setVisibility(View.INVISIBLE);
//			}
//
//		}
//
//		if (textId != null) {
//			setText(textId);
//			dialog.cancel();
//		}
//
//	}
//
//	public void clear() {
//		setText("");
//		textId = null;
//		value = null;
//		showSelect(0);
//	}
//
//	public Object getWidgetValue() {
//		return value;
//	}
//
//
//
//
//}
