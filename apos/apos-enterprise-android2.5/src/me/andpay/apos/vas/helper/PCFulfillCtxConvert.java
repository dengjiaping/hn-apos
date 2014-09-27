package me.andpay.apos.vas.helper;

import java.util.List;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.flow.model.OpenCardContext;

/**
 * 预付费卡履约上下文转换
 * 
 * @author tinyliu
 *
 */
public class PCFulfillCtxConvert implements
		PdFulfillCtxConvert<OpenCardContext> {

	public OpenCardContext convert2Context(PurchaseOrderInfo info) {
		OpenCardContext openCardContext = new OpenCardContext();
		openCardContext.setOrderId(info.getOrderId());
		openCardContext.setProductType(info.getItems().get(0).getProductType());
		openCardContext.setCardSalesAmt(info.getSalesAmt());
		openCardContext.setCardQuantity(info.getItems().get(0).getUnit());
		return openCardContext;
	}

	public boolean isSupport(List<PurchaseOrderItem> products) {
		PurchaseOrderItem item = products.get(0);
		if (ShopProductTypes.E_SVC.equals(item.getProductType())
				|| ShopProductTypes.PHYSICAL_SVC.equals(item.getProductType())) {
			return true;
		}
		return false;
	}

}
