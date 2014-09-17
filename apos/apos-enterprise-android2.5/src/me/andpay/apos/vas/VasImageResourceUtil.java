package me.andpay.apos.vas;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.R;

public class VasImageResourceUtil {
	
	private static Map<String, Integer> productImg = new HashMap<String, Integer>();
	
	static {
		productImg.put(ShopProductTypes.PHYSICAL_SVC,
				R.drawable.com_icon_physical_card_img);
		productImg.put(ShopProductTypes.E_SVC, R.drawable.com_icon_virtual_card_img);
		productImg.put(ShopProductTypes.SVC_DEPOSIT, R.drawable.com_icon_swipe_recharge_img);
	}

	public static Integer getImageFromProductImage(String productType) {
		return productImg.get(productType);
	}
	
	
}
