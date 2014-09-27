package me.andpay.apos.tam.context.handler;

import me.andpay.apos.R;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;

/**
 * 交易提交
 * 
 * @author cpz
 *
 */
@HandlerStatus(status = TxnStatus.TXN_SUBMIT_WITH_PIN)
public class TxnSubmitWithPinHandler extends GenChangeStatusHander {

	@Override
	protected void changeUI(TxnControl txnControl) {

		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();

		activity.topTextView.setText(activity.getResources().getText(
				R.string.tam_txn_submit_top_str));

	}

	@Override
	protected void changeAction(TxnControl txnControl) {
		txnControl.submitTxn(txnControl.getCurrActivity());
	}
}
