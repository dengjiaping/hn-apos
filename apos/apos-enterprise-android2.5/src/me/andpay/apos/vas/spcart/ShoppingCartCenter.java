package me.andpay.apos.vas.spcart;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车中心
 * 
 * @author cpz
 * 
 */
public class ShoppingCartCenter {

	private static ShoppingCart shoppingCart = new ShoppingCart();

	/**
	 * 清空购物车
	 */
	public static void clearShoppingCard() {
		shoppingCart = new ShoppingCart();
	}

	public static ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	/**
	 * 获取产品
	 * 
	 * @param proLong
	 * @return
	 */
	public static ProductItem getProduct(Long proLong) {
		return shoppingCart.getItemMap().get(proLong);
	}

	public static boolean addProduct(ProductItem productItem) {
		return addProduct(productItem, 1);
	}

	/**
	 * 添加产品
	 * 
	 * @param productItem
	 * @return 产品是否可添加到购物车
	 */
	public static boolean addProduct(ProductItem productItem, int count) {

		if (shoppingCart.getItemsList().size() == 0) {
			shoppingCart.setProductType(productItem.getProductType());
		} else {
			// 排他加入
			if (productItem.isExclusive()
					&& !shoppingCart.getProductIds().contains(
							productItem.getProductId())) {
				return false;
			}

		}

		ProductItem pitem = shoppingCart.getItemMap().get(
				productItem.getProductId());
		if (pitem == null) {
			productItem.setUnit(count);
			shoppingCart.putItem(productItem);
		} else {
			pitem.setUnit(pitem.getUnit() + count);
		}

		shoppingCart.setTotalAmt(shoppingCart.getTotalAmt().add(
				productItem.getPrice().multiply(new BigDecimal(count))));
		shoppingCart.setTotalProduct(shoppingCart.getTotalProduct() + count);

		return true;
	}

	/**
	 * 添加产品集合
	 * 
	 * @param shoppingCart
	 * @param productItems
	 */
	public static void addProduct(List<ProductItem> productItems) {
		for (ProductItem productItem : productItems) {
			addProduct(productItem);
		}
	}

	/**
	 * 删除产品
	 * 
	 * @param shoppingCart
	 * @param productId
	 */
	public static void deleteProduct(Long productId) {
		ProductItem productItem = shoppingCart.deleteItem(productId);
		shoppingCart.setTotalProduct(shoppingCart.getTotalProduct()
				- productItem.getUnit());
		shoppingCart.setTotalAmt(shoppingCart.getTotalAmt().subtract(
				productItem.getPrice().multiply(
						new BigDecimal(productItem.getUnit()))));
	}

	/**
	 * 按数量减少产品
	 * 
	 * @param shoppingCart
	 * @param productId
	 * @param count
	 */
	public static ProductItem subProduct(Long productId, int count) {
		ProductItem pitem = shoppingCart.getItemMap().get(productId);
		if (pitem != null) {
			shoppingCart
					.setTotalProduct(shoppingCart.getTotalProduct() - count);
			shoppingCart.setTotalAmt(shoppingCart.getTotalAmt().subtract(
					pitem.getPrice().multiply(new BigDecimal(count))));

			// int putil = pitem.getUnit() - count;
			pitem.setUnit(pitem.getUnit() - count);

			return pitem;
		}

		return null;

	}

	/**
	 * 减少产品
	 * 
	 * @param shoppingCart
	 * @param productId
	 */
	public static ProductItem subProduct(Long productId) {
		return subProduct(productId, 1);
	}

	public static boolean isEmpty() {
		if (shoppingCart.getItemsList().isEmpty()) {
			return true;
		}

		return false;
	}

}
