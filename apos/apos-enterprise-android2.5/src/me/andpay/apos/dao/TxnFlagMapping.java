package me.andpay.apos.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.andpay.ac.consts.TxnFlags;
import me.andpay.ti.util.StringUtil;

/**
 * TxnFalg映射对象
 * 
 * @author tinyliu
 * 
 */
public class TxnFlagMapping {

	private static Map<String, String> statusMappings = null;

	static {
		statusMappings = new HashMap<String, String>();
		statusMappings.put(TxnFlags.APPROVING, PayTxnInfoStatus.STATUS_SUCCESS);
		statusMappings.put(TxnFlags.SUCCESS, PayTxnInfoStatus.STATUS_SUCCESS);
		statusMappings.put(TxnFlags.WAITING_SETTLE,
				PayTxnInfoStatus.STATUS_SUCCESS);
		statusMappings.put(TxnFlags.SAF, PayTxnInfoStatus.STATUS_SUCCESS);
		statusMappings.put(TxnFlags.VOIDED, PayTxnInfoStatus.STATUS_SUCCESS);
		statusMappings.put(TxnFlags.FAIL, PayTxnInfoStatus.STATUS_FAIL);
		statusMappings.put(TxnFlags.PENDING, PayTxnInfoStatus.STATUS_PENDING);

	}

	public static String getStatusByFlag(String txnFlag) {
		return statusMappings.get(txnFlag);
	}

	public static Set<String> getFlagsByStatus(String status) {
		Set<String> flags = new HashSet<String>();
		if (StringUtil.isEmpty(status)) {
			flags.addAll(statusMappings.keySet());
			return flags;
		}
		for (String flag : statusMappings.keySet()) {
			if (status.equalsIgnoreCase(statusMappings.get(flag))) {
				flags.add(flag);
			}
		}
		return flags;
	}

	public static Set<String> getSuccessFlags() {
		Set<String> flags = new HashSet<String>();
		flags.add(TxnFlags.SUCCESS);
		flags.add(TxnFlags.SAF);
		flags.add(TxnFlags.WAITING_SETTLE);
		flags.add(TxnFlags.APPROVING);

		return flags;
	}

	// public static Set<String> getFlagsByStatus(String status,
	// Boolean isRefundEnable) {
	// if (isRefundEnable == null) {
	// return getFlagsByStatus(status);
	//
	// }
	// Set<String> flags = new HashSet<String>();
	// if (isRefundEnable) {
	// flags.add(TxnFlags.SUCCESS);
	// flags.add(TxnFlags.SAF);
	// flags.add(TxnFlags.WAITING_SETTLE);
	// flags.add(TxnFlags.APPROVING);
	// } else {
	// flags.add(TxnFlags.VOIDED);
	// }
	// return flags;
	// }
}
