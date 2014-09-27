package me.andpay.apos.vas.flow;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.common.flow.FlowNames;

/**
 * 产品销售流程选择
 * 
 * @author cpz
 *
 */
public class ProductSalesFlowFactory {

	public static Map<String, String> flowMaps = new HashMap<String, String>();

	public static Map<String, String> fulFillFlowMaps = new HashMap<String, String>();

	static {
		flowMaps.put(ShopProductTypes.E_SVC, FlowNames.VAS_PRODUCT_SALES_1_FLOW);
		flowMaps.put(ShopProductTypes.PHYSICAL_SVC,
				FlowNames.VAS_PRODUCT_SALES_1_FLOW);
		fulFillFlowMaps.put(ShopProductTypes.E_SVC,
				FlowNames.VAS_OPEN_CARD_FLOW);
		fulFillFlowMaps.put(ShopProductTypes.PHYSICAL_SVC,
				FlowNames.VAS_OPEN_CARD_FLOW);
	}

	public static String getFlow(String productType) {
		return flowMaps.get(productType);
	}

	public static String getFulfillFlow(String productType) {
		return fulFillFlowMaps.get(productType);
	}

}
