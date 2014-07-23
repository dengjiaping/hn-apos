package me.andpay.apos.vas.helper;

import java.util.List;

import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.apos.dao.model.PurchaseOrderInfo;

/**
 * 产品履约上下文转换接口
 * 
 * @author tinyliu
 * 
 * @param <T>
 */
public interface PdFulfillCtxConvert<T> {

	public T convert2Context(PurchaseOrderInfo info);

	public boolean isSupport(List<PurchaseOrderItem> products);
}
