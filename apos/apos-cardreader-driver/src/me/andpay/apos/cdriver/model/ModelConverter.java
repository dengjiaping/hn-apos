package me.andpay.apos.cdriver.model;

import me.andpay.timobileframework.util.BeanUtils;
import me.andpay.timobileframework.util.StringConvertor;

import com.NLmpos.API.Hefu.ICCardDataInfo;

/**
 * 对象转换器
 * 
 * @author cpz
 * 
 */
public class ModelConverter {

	public static AposICCardDataInfo covertAposIcCardDataInfo(
			ICCardDataInfo icCardDataInfo) {
		
		
		AposICCardDataInfo aposICCardDataInfo = BeanUtils.copyProperties(AposICCardDataInfo.class, icCardDataInfo);
	
		String amtOhter = aposICCardDataInfo.getAmtOther();
		if(amtOhter == null||amtOhter.equals("00")) {
			aposICCardDataInfo.setAmtOther("000000000000");

		}else {
//			amtOhter = amtOhter.substring(0,amtOhter.length()-2);
			amtOhter =  StringConvertor.format("000000000000", Long.valueOf(amtOhter) % 1000000000000l);
			aposICCardDataInfo.setAmtOther(amtOhter);
		}
		
		String txnAmtOrAuthAmt = aposICCardDataInfo.getTxnAmtOrAuthAmt();
		if(txnAmtOrAuthAmt==null || txnAmtOrAuthAmt.equals("00")) {
			aposICCardDataInfo.setTxnAmtOrAuthAmt("000000000000");

		}else {
//			txnAmtOrAuthAmt = txnAmtOrAuthAmt.substring(0,txnAmtOrAuthAmt.length()-2);
			txnAmtOrAuthAmt =  StringConvertor.format("000000000000", Long.valueOf(txnAmtOrAuthAmt) % 1000000000000l);
			aposICCardDataInfo.setTxnAmtOrAuthAmt(txnAmtOrAuthAmt);
		}
		
		return aposICCardDataInfo;
	}

	public static ICCardDataInfo covertIcCardDataInfo(
			AposICCardDataInfo aposICCardDataInfo) {
		ICCardDataInfo icCardDataInfo = BeanUtils.copyProperties(ICCardDataInfo.class, aposICCardDataInfo);
		if(aposICCardDataInfo.getAmtOther() != null) {
			icCardDataInfo.setAmtOther(Long.valueOf(aposICCardDataInfo.getAmtOther()).toString());
		}else {
			icCardDataInfo.setAmtOther("0");
		}
		
		if(aposICCardDataInfo.getTxnAmtOrAuthAmt() != null) {
			icCardDataInfo.setTxnAmtOrAuthAmt(Long.valueOf(aposICCardDataInfo.getTxnAmtOrAuthAmt()).toString());
		}else {
			icCardDataInfo.setTxnAmtOrAuthAmt("0");
		}
		
		return icCardDataInfo;
	}
}
