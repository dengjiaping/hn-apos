package me.andpay.apos.tam;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.CardAssoces;
import me.andpay.apos.R;
import me.andpay.ti.util.StringUtil;

public class CardOrgImageMap {
	
	public static Map<String,Integer> cardMap = new HashMap<String,Integer>();
	
	static {
		cardMap.put(CardAssoces.CHINA_UNION_PAY, R.drawable.com_icon_unionpay_img);
		cardMap.put(CardAssoces.VISA, R.drawable.com_icon_visa_img);
		cardMap.put(CardAssoces.MASTER_CARD, R.drawable.com_icon_mastercard_img);
		cardMap.put(CardAssoces.DINERS_CLUB, R.drawable.com_icon_dinersclub_img);
		cardMap.put(CardAssoces.AMERICAN_EXPRESS, R.drawable.com_icon_americaexpress_img);
		cardMap.put(CardAssoces.PRIVATED_CARD, R.drawable.com_icon_andpaycard_img);

	}
	
	public static int getId(String orgId)  {
		if(StringUtil.isBlank(orgId) || cardMap.get(orgId.toUpperCase()) == null) {
			return R.drawable.com_icon_unionpay_img;
		}
		return cardMap.get(orgId.toUpperCase());
	}

}
