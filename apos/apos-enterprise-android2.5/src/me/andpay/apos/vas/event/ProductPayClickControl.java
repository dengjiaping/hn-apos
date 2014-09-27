package me.andpay.apos.vas.event;

import me.andpay.apos.vas.flow.ProductSalesFlowFactory;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 启动销售流程
 * 
 * @author cpz
 * 
 */
public class ProductPayClickControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		if (ShoppingCartCenter.isEmpty()) {
			return;
		}

		TiFlowControlImpl.instanceControl().startFlow(
				activity,
				ProductSalesFlowFactory.getFlow(ShoppingCartCenter
						.getShoppingCart().getProductType()));
		ProductSalesContext productSalesContext = new ProductSalesContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(
				productSalesContext);
		productSalesContext.setShoppingCart(ShoppingCartCenter
				.getShoppingCart());
	}
}
