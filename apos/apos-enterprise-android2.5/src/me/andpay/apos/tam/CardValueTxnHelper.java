package me.andpay.apos.tam;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.callback.impl.ValueCardTxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class CardValueTxnHelper {

	public static void sendTxn(String txnAmt, String couponResult,
			TxnControl txnControl, String goodFileUrl, Activity activity) {
		TxnContext txnContext = txnControl.init();
		txnControl.setTxnCallback(new ValueCardTxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		// 可回主页
		txnContext.setBackTagName(TabNames.TXN_PAGE);
		txnContext.setAmtFomat(txnAmt);
		txnContext.setTxnType(TxnTypes.PURCHASE);

		txnContext.setExtType(ExtTypes.EXT_TYPE_VALUE_CARD_TXN);

		CardInfo cardInfo = new CardInfo();
		cardInfo.setEncTracks(couponResult);
		String cardNo = couponResult.split("=")[0];
		cardInfo.setMaskedPAN(TxnUtil.hidePan(cardNo));
		txnContext.setCardInfo(cardInfo);

		ParseBinResponse parseBinResponse = new ParseBinResponse();
		parseBinResponse.setCardNo(cardNo);
		parseBinResponse.setCardAssoc("PC");
		txnContext.setParseBinResp(parseBinResponse);
		if (goodFileUrl != null) {
			txnContext.setGoodsFileURL(goodFileUrl);
			txnContext.setGoodsUpload(true);
		}
		txnContext.setSignUplaod(true);
		txnContext.setHasNewTxnButton(true);
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);

	}
}
