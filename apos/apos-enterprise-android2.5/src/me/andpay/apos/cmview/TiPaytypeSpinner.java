package me.andpay.apos.cmview;

import android.content.Context;
import android.util.AttributeSet;

public class TiPaytypeSpinner extends TiSpinner{

	public TiPaytypeSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/*private void initInfo() {

		itemIds.put(R.id.com_txntypes_all_layout, R.id.com_txntypes_all_str);
		itemIds.put(R.id.com_txntypes_purchase_layout,
				R.id.com_txntypes_purchase_str);
		itemIds.put(R.id.com_txntypes_refund_layout,
				R.id.com_txntypes_refund_str);
		itemIds.put(R.id.com_txntypes_void_layout, R.id.com_txntypes_void_str);
		itemImgs.put(R.id.com_txntypes_all_layout, R.id.com_txntypes_all_img);
		itemImgs.put(R.id.com_txntypes_purchase_layout,
				R.id.com_txntypes_purchase_img);
		itemImgs.put(R.id.com_txntypes_refund_layout,
				R.id.com_txntypes_refund_img);
		itemImgs.put(R.id.com_txntypes_void_layout, R.id.com_txntypes_void_img);
		itemValues.put(R.id.com_txntypes_all_layout, null);
		itemValues.put(R.id.com_txntypes_purchase_layout, TxnTypes.PURCHASE);
		itemValues.put(R.id.com_txntypes_refund_layout, TxnTypes.REFUND);
		itemValues.put(R.id.com_txntypes_void_layout, TxnTypes.VOID_PURCHASE);
		itemStrs.put(R.id.com_txntypes_all_layout,
				R.string.tqm_txn_types_all_str);
		itemStrs.put(R.id.com_txntypes_purchase_layout,
				R.string.tqm_txn_types_purchase_str);
		itemStrs.put(R.id.com_txntypes_refund_layout,
				R.string.tqm_txn_types_refund_str);
		itemStrs.put(R.id.com_txntypes_void_layout,
				R.string.tqm_txn_types_void_str);
		for (Integer itemId : itemIds.keySet()) {
			dialog.findViewById(itemId).setOnClickListener(
					new TxnTypesCheckedChangeListener());
		}
	}*/

}
