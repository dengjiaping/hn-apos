package me.andpay.apos.vas.spcart;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814224041669682860L;

	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 产品编号
	 */
	private Long productId;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 单品编号
	 */
	private String skuNo;

	/**
	 * 数量
	 */
	private int unit;

	/**
	 * 价格名称
	 */
	private String priceName;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 属性
	 */
	private String attr;

	/**
	 * 产品购买排它标志
	 */
	private boolean exclusive;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

}
