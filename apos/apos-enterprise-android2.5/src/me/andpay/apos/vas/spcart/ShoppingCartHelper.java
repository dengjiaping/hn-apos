package me.andpay.apos.vas.spcart;

import java.math.BigDecimal;
import java.util.List;

import me.andpay.ac.term.api.shop.ProductPrice;
import me.andpay.apos.dao.model.ProductInfo;

public class ShoppingCartHelper {

	
	public static ProductItem productInfoConvertItem(ProductInfo productInfo) {
		ProductItem iProductItem = new ProductItem();
		iProductItem.setProductType(productInfo.getProductType());
		iProductItem.setAttr(iProductItem.getAttr());
		List<ProductPrice> prices = productInfo.getPrices();
		if (prices != null && prices.size() > 0) {
			iProductItem.setPrice(prices.get(0).getPrice());
			iProductItem.setPriceName(prices.get(0).getPriceName());
		} else {
			iProductItem.setPrice(new BigDecimal("0"));
		}
		iProductItem.setProductId(productInfo.getProductId());
		iProductItem.setUnit(1);
		iProductItem.setSkuNo(productInfo.getSkuNo());
		
		iProductItem.setProductName(productInfo.getName());
		iProductItem.setExclusive(productInfo.getExclusive());
		return iProductItem;
	}
	
	public static String getProductUnit(int unit){
		if(unit > 99) {
			return "99+";
		}
		return Integer.valueOf(unit).toString();
	}

}
