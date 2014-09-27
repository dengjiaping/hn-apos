package me.andpay.apos.common.util;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ti.util.StringUtil;

public class ItemConvertUtil {

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";

	public static String appendItemId(String sourceItemIds, String newItemId) {

		if (StringUtil.isBlank(sourceItemIds)) {
			return newItemId;
		}

		return sourceItemIds + SEPARATOR + newItemId;
	}

	public static List<String> getFileItemsUrl(String url,
			String sourceItemIds, String itemType) {

		List<String> imteList = new ArrayList<String>();

		if (StringUtil.isBlank(sourceItemIds)) {
			return imteList;
		}

		String[] items = sourceItemIds.split(SEPARATOR);
		for (int i = 0; i < items.length; i++) {
			imteList.add(url + "?id=" + items[i] + "&type=" + itemType);
		}
		return imteList;

	}
}
