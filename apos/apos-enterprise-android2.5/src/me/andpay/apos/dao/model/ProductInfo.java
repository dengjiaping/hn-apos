package me.andpay.apos.dao.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import me.andpay.ac.term.api.shop.ProductPrice;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;
import me.andpay.timobileframework.sqlite.convert.BigDecimalConverter;
import me.andpay.timobileframework.sqlite.convert.DateConverter;
import me.andpay.timobileframework.util.PinyinUtil;

@TableName(name = "ProductInfo", version = 1)
public class ProductInfo {
	/**
	 * 产品主键
	 */
	@ID
	@Column
	private Integer idProductInfo;
	/**
	 * 产品编号
	 */
	@Column
	private Long productId;

	/**
	 * 商户参与者编号
	 */
	@Column
	private String merchPartyId;

	/**
	 * 产品类型
	 */
	@Column
	private String productType;

	/**
	 * 单品编号
	 */
	@Column
	private String skuNo;


	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 名称pinyin
	 */
	@Column
	private String namePinyin;

	/**
	 * 描述
	 */
	@Column
	private String description;

	/**
	 * 属性
	 */
	@Column
	private String attr;

	/**
	 * 是否支持购物车
	 */
	@Column
	private Boolean shopCartFlag;

	/**
	 * 状态
	 */
	@Column
	private String status;

	/**
	 * 关联参考编号
	 */
	@Column
	private String autoRefNo;

	/**
	 * 价格集
	 */
	@Column
	private List<ProductPrice> prices;
	
	
	
	/**
	 * 产品购买排它标志
	 */
	@Column
	private Boolean exclusive;

	/**
	 * 本地数据库更新时间
	 */
	@Column(dataConverter = DateConverter.class)
	private Date updataTime;
	
	/**
	 * 价格
	 */
	@Column(dataConverter = BigDecimalConverter.class)
	private BigDecimal price;

	
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getIdProductInfo() {
		return idProductInfo;
	}

	public void setIdProductInfo(Integer idProductInfo) {
		this.idProductInfo = idProductInfo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getMerchPartyId() {
		return merchPartyId;
	}

	public void setMerchPartyId(String merchPartyId) {
		this.merchPartyId = merchPartyId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		namePinyin = PinyinUtil.chineseToPinyin(name);
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Boolean getShopCartFlag() {
		return shopCartFlag;
	}

	public void setShopCartFlag(Boolean shopCartFlag) {
		this.shopCartFlag = shopCartFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAutoRefNo() {
		return autoRefNo;
	}

	public void setAutoRefNo(String autoRefNo) {
		this.autoRefNo = autoRefNo;
	}

	public List<ProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ProductPrice> prices) {
		this.prices = prices;
	}

	public Date getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(Date updataTime) {
		this.updataTime = updataTime;
	}

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	public Boolean getExclusive() {
		return exclusive;
	}

	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}

}
