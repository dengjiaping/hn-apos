package me.andpay.apos.tam.context.handler;

import me.andpay.apos.tam.CardOrgImageMap;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.flow.model.TxnContext;

@HandlerStatus(status = TxnStatus.TXN_TIME_OUT_DOQUERY)
public class TimeoutDoQueryHandler extends GenChangeStatusHander {

	@Override
	protected void changeUI(TxnControl txnControl) {

		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		TxnContext txnContext = txnControl.getTxnContext();

		// if(txnControl.isReverseFlag()) {
		// activity.backButton.setVisibility(View.GONE);
		// txnControl.setReverseFlag(false);
		// }

		// activity.timeTextView.setVisibility(View.VISIBLE);
		// activity.cardLay.setVisibility(View.VISIBLE);
		// activity.timeoutImg.setVisibility(View.VISIBLE);
		// activity.trySubmit.setVisibility(View.VISIBLE);
		// activity.topTextView.setText("交易超时了！");
		//
		activity.cardImage.setImageResource(CardOrgImageMap.getId(txnContext
				.getParseBinResp().getCardAssoc()));
		activity.cardTextView.setText(txnContext.getCardInfo().getMaskedPAN());
		activity.amtTxnView.setText(txnContext.getAmtFomat());
		// 获取超时交易
		// activity.timeoutProcess();

	}

	@Override
	protected void changeAction(TxnControl txnControl) {

	}

}
