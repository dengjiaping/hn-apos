package me.andpay.apos.cmview.tispinner;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class TiNewSpinner extends TextView implements OnClickListener,
		OnItemClickListener, WidgetValueHolder {

	private Dialog dialog = null;

	private ListView listView;

	private TiSpinnerItem selectItem;

	private TiSpinnerAdapter siAdapter;

	private Context context;

	private OnSpinnerItemClickListener onSpinnerItemClickListener;

	public TiNewSpinner(Context context) {
		this(context, null);
	}

	public TiNewSpinner(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public TiNewSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		this.context = context;
		this.setOnClickListener(this);
		dialog = new Dialog(context, R.style.Theme_dialog_style);
		dialog.setContentView(R.layout.com_sponner_dialog_layout);
		listView = (ListView) dialog.findViewById(R.id.com_spinner_dialog_list);

	}

	public void setAdadter(TiSpinnerAdapter siAdapter) {
		this.siAdapter = siAdapter;
		listView.setAdapter(siAdapter);
		listView.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		dialog.show();
		InputMethodManager im = ((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(this.getWindowToken(), 0);
		siAdapter.notifyDataSetChanged();
	}

	public Object getWidgetValue() {
		return selectItem.getValue();
	}

	public void selectItem(int index) {
		if (selectItem != null) {
			selectItem.setSelectFlag(false);
		}
		selectItem = siAdapter.getTiSpinnerItemems().get(index);
		selectItem.setSelectFlag(true);
		setText(selectItem.getName());
	}

	public interface OnSpinnerItemClickListener {
		public void onItemClick(View arg1, TiSpinnerItem selItem);
	}

	public void onItemClick(AdapterView<?> listView, View view, int indext,
			long arg3) {
		selectItem(indext);
		final View _view = view;
		if (onSpinnerItemClickListener != null) {
			view.post(new Runnable() {
				public void run() {
					onSpinnerItemClickListener.onItemClick(_view, selectItem);
				}
			});

		}
		dialog.cancel();
	}

	public void setOnSpinnerItemClickListener(
			OnSpinnerItemClickListener onSpinnerItemClickListener) {
		this.onSpinnerItemClickListener = onSpinnerItemClickListener;
	}

}
