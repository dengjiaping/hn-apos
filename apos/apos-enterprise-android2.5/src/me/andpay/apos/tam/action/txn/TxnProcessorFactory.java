package me.andpay.apos.tam.action.txn;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import roboguice.RoboGuice;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class TxnProcessorFactory {

	public static Map<String, Class<? extends TxnProcessor>> txnProcessorsClass = new HashMap<String, Class<? extends TxnProcessor>>();

	public static Map<String, TxnProcessor> txnProcessors = new HashMap<String, TxnProcessor>();

	@Inject
	public Application application;

	public static void registerProcessor(String txnType,
			Class<? extends TxnProcessor> txnProcessorClass) {
		txnProcessorsClass.put(txnType, txnProcessorClass);
	}

	public TxnProcessor getProcessor(String txnType) {

		Injector injector = RoboGuice.getBaseApplicationInjector(application);

		// 撤销设置为退货
		if (TxnTypes.isVoidTxnType(txnType)) {
			txnType = TxnTypes.REFUND;
		}

		TxnProcessor txnProcessor = txnProcessors.get(txnType);
		if (txnProcessor == null) {

			Class<? extends TxnProcessor> txnClass = txnProcessorsClass
					.get(txnType);
			if (txnClass == null) {
				return null;
			}

			txnProcessor = injector.getInstance(txnClass);
			txnProcessors.put(txnType, txnProcessor);
		}

		return txnProcessor;
	}
}
