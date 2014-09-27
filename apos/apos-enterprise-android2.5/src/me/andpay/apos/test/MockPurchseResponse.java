package me.andpay.apos.test;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.CurrencyCodes;
import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.PurchaseResponse;
import me.andpay.ac.term.api.txn.TxnExtAttrNames;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.tlv.TlvUtil;

public class MockPurchseResponse extends PurchaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MockPurchseResponse(TxnForm txnForm) {

		setTermTraceNo(txnForm.getTermTraceNo());
		setTermTxnTime(StringUtil.parseToDate("yyyyMMddHHmmss",
				txnForm.getTermTxnTime()));
		setAuthAmt(txnForm.getSalesAmt());
		setTxnId(txnForm.getTermTraceNo() + txnForm.getTermTraceNo());
		setAuthCode(txnForm.getTermTraceNo());
		setCardName("招商银行");
		setIssuerName("招商银行");
		setAuthCur(CurrencyCodes.CNY);
		setRespCode(ResponseCodes.SUCCESS);
		setSalesAmt(txnForm.getSalesAmt());
		setRespMessage("交易成功");
		setTxnFlag(TxnFlags.SUCCESS);
		setExtTraceNo("123141222");
		setSalesCur(CurrencyCodes.CNY);
		setSignTxnFlag(true);
		setTxnType(TxnTypes.PURCHASE);
		setTxnTypeMessage("消费");
		setEncCardNo("4693801138029348");
		setICCardInfo(txnForm.getAposICCardDataInfo());
	}

	private void setICCardInfo(AposICCardDataInfo aposICCardDataInfo) {
		if (aposICCardDataInfo == null) {
			return;
		}
		Map<String, String> extAttrs = this.getExtAttrs();
		if (extAttrs == null) {
			extAttrs = new HashMap<String, String>();
			this.setExtAttrs(extAttrs);
		}

		String tlvString = TlvUtil.encodeTvl(aposICCardDataInfo);

		extAttrs.put(TxnExtAttrNames.IC_DATA_BASE64, tlvString);

	}

}
