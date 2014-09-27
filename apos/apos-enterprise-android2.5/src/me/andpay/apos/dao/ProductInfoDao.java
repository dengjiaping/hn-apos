package me.andpay.apos.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import me.andpay.ac.term.api.shop.Product;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.dao.model.QueryProductInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import me.andpay.timobileframework.util.BeanUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ProductInfoDao extends
		GenSqLiteDao<ProductInfo, QueryProductInfoCond, Integer> {

	private final static String TB_NAME = "ProductInfo";

	private final static String DELETE_WHERE = "merchPartyId=?";

	public ProductInfoDao(Context context, String name, CursorFactory factory,
			int version, Class<? extends ProductInfo> t) {
		super(context, name, factory, version, t);

	}

	/**
	 * 新增产品数据
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		ProductInfo info = new ProductInfo();
		BeanUtils.copyProperties(product, info);
		info.setExclusive(product.isExclusive());
		info.setUpdataTime(new Date());
		this.insert(info);
	}

	/**
	 * 更新产品数据
	 * 
	 * @param product
	 * @param pInfo
	 */
	public void updateProduct(Product product, ProductInfo pInfo) {
		BeanUtils.copyProperties(product, pInfo);
		pInfo.setUpdataTime(new Date());
		pInfo.setExclusive(product.isExclusive());
		this.update(pInfo);
	}

	/**
	 * 删除产品数据
	 * 
	 * @param product
	 */
	public void deleteProduct(Product product) {
		QueryProductInfoCond cond = new QueryProductInfoCond();
		cond.setProductId(product.getProductId());
		List<ProductInfo> productInfo = this.query(cond, 0, -1);
		if (productInfo != null && !productInfo.isEmpty()) {
			this.delete(productInfo.get(0).getIdProductInfo());
		}

	}

	/**
	 * 获取商户有效商品信息
	 * 
	 * @param partyId
	 * @return
	 */
	@SuppressLint("UseValueOf")
	public ProductStatisticsInfo getPartyValiProductStatisticsInfo(
			String partyId) {
		ProductStatisticsInfo info = new ProductStatisticsInfo();
		QueryProductInfoCond cond = new QueryProductInfoCond();
		cond.setMerchPartyId(partyId);
		cond.setStatus(Product.STATUS_NORMAL);
		List<ProductInfo> products = this.query(cond, 0, -1);
		if (products == null || products.isEmpty()) {
			return info;
		}
		info.setCount(new Long(products.size()));
		BigDecimal total = new BigDecimal(0);
		for (ProductInfo product : products) {
			total = total.add(product.getPrice());
		}
		info.setTotal(total);
		return info;
	}

	/**
	 * 删除商户所有产品信息
	 * 
	 * @param partyId
	 */
	public void deleteAllProductByPartyId(String partyId) {
		this.getWritableDatabase().delete(TB_NAME, DELETE_WHERE,
				new String[] { partyId });
	}

	/**
	 * 产品统计信息
	 * 
	 * @author tinyliu
	 * 
	 */
	public class ProductStatisticsInfo {

		private BigDecimal total = new BigDecimal(0);

		private Long count = 0L;

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public Long getCount() {
			return count;
		}

		public void setCount(Long count) {
			this.count = count;
		}

	}
}
