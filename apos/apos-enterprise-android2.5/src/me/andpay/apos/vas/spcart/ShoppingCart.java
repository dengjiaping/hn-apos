package me.andpay.apos.vas.spcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * 
 * @author cpz
 * 
 */
public class ShoppingCart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6218377745460465998L;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmt = new BigDecimal("0");

	/**
	 * 总产品数
	 */
	private int totalProduct = 0;
	
	/**
	 * 购物车产品类型
	 */
	private String productType;
	/**
	 * 购物车集合
	 */
	private List<ProductItem> itemsList = new ArrayList<ProductItem>();
	private Map<Long,ProductItem> itemMap = new HashMap<Long, ProductItem>();
	private List<Long> productIds = new ArrayList<Long>();

	
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public List<ProductItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ProductItem> itemsList) {
		this.itemsList = itemsList;
	}


	public Map<Long, ProductItem> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, ProductItem> itemMap) {
		this.itemMap = itemMap;
	}

	public void putItem(ProductItem item) {
		itemsList.add(item);
		itemMap.put(item.getProductId(), item);
		productIds.add(item.getProductId());
	}
	
	public ProductItem deleteItem(Long productId) {
		ProductItem productItem = itemMap.remove(productId);
		productIds.remove(productId);
		itemsList.remove(productItem);
		return productItem;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	
}
