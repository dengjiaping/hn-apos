package me.andpay.apos.vas.helper;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.R;

/**
 * 产品资源管理器
 * @author cpz
 *
 */
public class ProductResouceHelper {
	
	
	public static Map<String,Integer> productImages = new HashMap<String, Integer>();
	static {
		productImages.put(ShopProductTypes.PHYSICAL_SVC, R.drawable.com_icon_physical_card_img);
		productImages.put(ShopProductTypes.E_SVC, R.drawable.com_icon_virtual_card_img);
	}
	
	/**
	 * 获取产品类型资源图片
	 * @param type
	 * @return
	 */
	public static int getProductImages(String type) {
		return productImages.get(type);
	}
	
	
}
