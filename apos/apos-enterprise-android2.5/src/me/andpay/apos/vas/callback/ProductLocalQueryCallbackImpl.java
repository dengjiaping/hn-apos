package me.andpay.apos.vas.callback;

import java.util.List;

import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.vas.activity.ProductSalesActivity;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class ProductLocalQueryCallbackImpl implements ProductLocalQueryCallback {

	private ProductSalesActivity productSalesActivity;

	public ProductLocalQueryCallbackImpl(
			ProductSalesActivity productSalesActivity) {
		this.productSalesActivity = productSalesActivity;
	}

	public void querySuccess(List<ProductInfo> productInfos) {

		if(productSalesActivity.allProductInfos == null || productSalesActivity.allProductInfos.isEmpty()) {
			productSalesActivity.allProductInfos = productInfos;
		}
	
		productSalesActivity.showList(productInfos);
	}

}
