package me.andpay.apos.ssm.action;

import java.math.BigDecimal;
import java.util.Map;

import me.andpay.ac.term.api.txn.TxnBatchItem;

public class SettleInfoUtil {
	public static int getItemCount(Map<String, TxnBatchItem> items,
			String... txnTypes) {
		int count = 0;
		for (String type : txnTypes) {
			TxnBatchItem item = items.get(type);
			if (item != null) {
				count += item.getCount();
			}
		}
		return count;
	}

	public static BigDecimal getItemTotal(Map<String, TxnBatchItem> items,
			String... txnTypes) {
		BigDecimal total = new BigDecimal(0);
		for (String type : txnTypes) {
			TxnBatchItem item = items.get(type);
			if (item != null) {
				total = total.add(item.getTotal());
			}
		}
		return total;
	}
}
