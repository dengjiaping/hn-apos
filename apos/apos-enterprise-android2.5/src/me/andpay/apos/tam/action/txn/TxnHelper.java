package me.andpay.apos.tam.action.txn;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.txn.TxnExtAttrNames;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.ti.lnk.transport.wsock.client.light.http.Base64;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.tlv.TlvUtil;

public class TxnHelper {
	
	
	public static void setICCardInfo(AposICCardDataInfo aposICCardDataInfo,
			TxnRequest purRequest) {
		if (aposICCardDataInfo == null) {
			return;
		}
		Map<String, String> extAttrs = purRequest.getExtAttrs();
		if(extAttrs == null) {
			extAttrs  = new HashMap<String, String>();
			purRequest.setExtAttrs(extAttrs);
		}
		
		
		extAttrs.put(TxnExtAttrNames.IC_DATA_BASE64,genICCardInfoBase64(aposICCardDataInfo) );
		extAttrs.put(TxnExtAttrNames.CARD_SEQ_NO,aposICCardDataInfo.getCardSerialNumber());

	}
	
	
	public static String genICCardInfoBase64(AposICCardDataInfo aposICCardDataInfo) {
		String tlvString = TlvUtil.encodeTvl(aposICCardDataInfo);
//		Log.e(TxnHelper.class.getName(), "tlvString----->"+tlvString);

		return 	Base64.encode(HexUtils.hexString2Bytes(tlvString));
	}
	
	
	
}
