package me.andpay.apos.cmview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TiSpinner extends EditText implements OnClickListener,
		OnFocusChangeListener, WidgetValueHolder {

	private Dialog dialog = null;

	private String value = null;

	private Integer textId = null;

	private Integer selectedItemId = -1;

	private Integer defaultLayoutId = R.layout.com_spinner_layout;

	protected List<ImageView> images = new ArrayList<ImageView>();

	protected List<TextView> texts = new ArrayList<TextView>();

	protected List<Pair<String, String>> values = null;

	protected Map<Integer, Integer> itemIds = new HashMap<Integer, Integer>();

	protected Map<Integer, Integer> itemImgs = new HashMap<Integer, Integer>();

	protected Map<Integer, String> itemValues = new HashMap<Integer, String>();

	protected Map<Integer, Integer> itemStrs = new HashMap<Integer, Integer>();

	public TiSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnClickListener(this);
		this.setOnFocusChangeListener(this);
		dialog = new Dialog(context, R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void setLayoutAndInit(int layoutId, TiSpinnerInit tiSpinnerInit) {
		dialog.setContentView(layoutId);
		tiSpinnerInit.initData(itemIds, itemImgs, itemValues, itemStrs);

		for (Integer itemId : itemIds.keySet()) {
			dialog.findViewById(itemId).setOnClickListener(
					new TxnTypesCheckedChangeListener());
		}
	}

	public void setSpinnerValues(List<Pair<String, String>> values) {
		dialog.setContentView(defaultLayoutId);
		this.values = values;
		LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.com_root_view);
		for (int i = 0; i < values.size(); i++) {
			layout.addView(createSolidImage());
			layout.addView(createItemView(values.get(i), i));
		}
	}

	private View createSolidImage() {
		ImageView img = new ImageView(this.getContext());
		img.setImageDrawable(this.getContext().getResources()
				.getDrawable(R.drawable.com_solid_line_img));
		img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		img.setScaleType(ScaleType.FIT_XY);
		return img;
	}

	private View createItemView(Pair<String, String> value, final int position) {

		final View convertView = LayoutInflater.from(this.getContext()).inflate(
				R.layout.com_idtype_spinner_item_layout, null);
		TextView text = (TextView) convertView.findViewById(R.id.com_type_name_text);
		ImageView view = (ImageView) convertView.findViewById(R.id.com_id_type_img);
		text.setText(value.first);
		convertView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showItemSelect(position);
				dialog.cancel();
			}
		});
		texts.add(text);
		images.add(view);
		return convertView;
	}

	public void onClick(View v) {
		dialog.show();
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			dialog.show();
		}

	}

	class TxnTypesCheckedChangeListener implements OnClickListener {

		public void onClick(View v) {
			showSelect(v.getId());
		}

	}

	public void showItemSelect(int position) {
		for (int i = 0; i < texts.size(); i++) {
			TextView text = (TextView) texts.get(i);
			ImageView view = (ImageView) images.get(i);
			if (i == position) {
				text.setTextColor(getResources().getColor(R.color.com_bule_color));
				view.setVisibility(View.VISIBLE);
				setText(values.get(i).first);
				this.selectedItemId = position;
			} else {
				text.setTextColor(getResources().getColor(R.color.com_title_normal_col));
				view.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void showSelect(int viewId) {
		for (Integer itemId : itemIds.keySet()) {
			TextView text = (TextView) dialog.findViewById(itemIds.get(itemId));
			ImageView view = (ImageView) dialog.findViewById(itemImgs.get(itemId));
			if (itemId.intValue() == viewId) {
				text.setTextColor(getResources().getColor(R.color.com_bule_color));
				view.setVisibility(View.VISIBLE);
				value = itemValues.get(itemId);
				textId = itemStrs.get(itemId);
			} else {
				text.setTextColor(getResources().getColor(R.color.com_title_normal_col));
				view.setVisibility(View.INVISIBLE);
			}

		}

		if (textId != null) {
			setText(textId);
			dialog.cancel();
		}

	}

	public void clear() {
		setText("");
		textId = null;
		value = null;
		showSelect(0);
	}

	public Object getWidgetValue() {
		return value;
	}

	public Integer getSelectedItemId() {
		return this.selectedItemId;
	}

	public Map<Integer, String> getItemValues() {
		return itemValues;
	}

	public interface TiSpinnerInit {
		public void initData(Map<Integer, Integer> itemIds,
				Map<Integer, Integer> itemImgs, Map<Integer, String> itemValues,
				Map<Integer, Integer> itemStrs);
	}

}
