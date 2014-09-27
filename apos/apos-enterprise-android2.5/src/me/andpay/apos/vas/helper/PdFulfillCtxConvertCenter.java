package me.andpay.apos.vas.helper;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.dao.model.PurchaseOrderInfo;

@SuppressWarnings("rawtypes")
public class PdFulfillCtxConvertCenter {

	private static List<PdFulfillCtxConvert> converts = new ArrayList<PdFulfillCtxConvert>();

	static {
		converts.add(new PCFulfillCtxConvert());
	}

	@SuppressWarnings("unchecked")
	public static Object convert(PurchaseOrderInfo info) {
		for (PdFulfillCtxConvert convert : converts) {
			if (convert.isSupport(info.getItems())) {
				return convert.convert2Context(info);
			}
		}
		return null;
	}
}
