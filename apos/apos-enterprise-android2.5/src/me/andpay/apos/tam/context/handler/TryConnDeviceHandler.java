package me.andpay.apos.tam.context.handler;

import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;

@HandlerStatus(status = TxnStatus.TRY_CONN_DEVICE)
public class TryConnDeviceHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {

		return super.preAction(txnControl);
	}

	@Override
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();

		activity.topTextView.setText("蓝牙初始化,请稍后...");

	}

	@Override
	protected void changeAction(TxnControl txnControl) {
		CardReaderManager.setCurrCallback(txnControl
				.getSwipCardReaderCallBack());
		CardReaderManager.startSwiper(AposCardReaderUtil.convertSwiperRequest(
				txnControl.getTxnContext(), txnControl.getCurrActivity()
						.getAppConfig()));
	}
}
