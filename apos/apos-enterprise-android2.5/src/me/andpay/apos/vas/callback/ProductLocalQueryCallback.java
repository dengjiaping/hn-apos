package me.andpay.apos.vas.callback;

import java.util.List;

import me.andpay.apos.dao.model.ProductInfo;

public interface ProductLocalQueryCallback {

	public void querySuccess(List<ProductInfo> productInfos);
}
