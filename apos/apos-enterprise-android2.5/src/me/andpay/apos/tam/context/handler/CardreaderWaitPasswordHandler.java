package me.andpay.apos.tam.context.handler;

import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;

@HandlerStatus(status = TxnStatus.CARD_READER_WAIT_PASSWORD)
public class CardreaderWaitPasswordHandler extends GenChangeStatusHander {

	/**
	 * 处理界面改变
	 * 
	 * @param txnControl
	 */
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty txnAcitivty = (TxnAcitivty) txnControl.getCurrActivity();
		txnAcitivty.topTextView.setText("请输入密码");
		
		CardReaderManager.setCurrCallback(txnControl.getSwipCardReaderCallBack());
		CardReaderManager.startSwiper(AposCardReaderUtil.convertSwiperRequest(
				txnControl.getTxnContext(), txnAcitivty.getAppConfig()));
	}

	/**
	 * 
	 * 服务提交和资源变更
	 */
	protected void changeAction(TxnControl txnControl) {

	}
}
