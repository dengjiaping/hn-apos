package me.andpay.apos.cmview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.android.WidgetValueHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TiTxnTypesSpinner extends Spinner implements WidgetValueHolder {

	Map<Integer, String> txnTypes = new HashMap<Integer, String>();

	private String selectValue = null;

	private String selectText = null;

	public TiTxnTypesSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		List<String> txnTypesDesc = new ArrayList<String>();
		txnTypesDesc.add(context.getResources().getString(
				R.string.tqm_txn_types_all_str));
		txnTypesDesc.add(context.getResources().getString(
				R.string.tqm_txn_types_purchase_str));
		txnTypesDesc.add(context.getResources().getString(
				R.string.tqm_txn_types_refund_str));
		txnTypesDesc.add(context.getResources().getString(
				R.string.tqm_txn_types_void_str));
		txnTypes.put(1, TxnTypes.PURCHASE);
		txnTypes.put(2, TxnTypes.REFUND);
		txnTypes.put(3, TxnTypes.VOID);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, txnTypesDesc);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(adapter);
		this.setOnItemSelectedListener(new OnItemClickListener());
	}

	private void setValue(String value) {
		this.selectValue = value;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public String getSelectText() {
		return selectText;
	}

	private void setSelectText(String selectText) {
		this.selectText = selectText;
	}

	class OnItemClickListener implements Spinner.OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String text = (String) getAdapter().getItem(position);
			setSelectText(text);
			setValue(txnTypes.get(position));

		}

		public void onNothingSelected(AdapterView<?> parent) {
			setSelectText(null);
			setValue(null);
		}

	}

	public Object getWidgetValue() {
		return selectValue;
	}

}
