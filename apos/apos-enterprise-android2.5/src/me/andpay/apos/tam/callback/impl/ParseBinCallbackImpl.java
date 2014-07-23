package me.andpay.apos.tam.callback.impl;

import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.callback.ParseBinCallback;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

/**
 * 卡号解析服务
 * 
 * @author cpz
 * 
 */
@CallBackHandler
public class ParseBinCallbackImpl implements ParseBinCallback {

	public TxnAcitivty txnAcitivty;
	
	public CardReaderCallback cardReaderCallback;

	public ParseBinCallbackImpl(TxnAcitivty txnAcitivty, CardReaderCallback cardReaderCallback) {
		this.txnAcitivty = txnAcitivty;
		this.cardReaderCallback = cardReaderCallback;
	}

	public void dealSuccess(ParseBinResponse parseResponse) {
		
		if(txnAcitivty == null || txnAcitivty.isFinishing()) {
			return;
		}

		txnAcitivty.txnControl.getTxnContext().getCardInfo()
				.setMaskedPAN(TxnUtil.hidePan(parseResponse.getCardNo()));
		txnAcitivty.txnControl.getTxnContext().setParseBinResp(parseResponse);
		// 等待密码输入
		txnAcitivty.txnControl.changeTxnStatus(TxnStatus.WAIT_PASSWORD,
				txnAcitivty.txnControl.getCurrActivity());

	}

	public void dealFailed(String msg) {
		cardReaderCallback.onDecodeError(msg);
	}

}
