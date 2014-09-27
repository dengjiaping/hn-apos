package me.andpay.apos.tam.context.handler;

import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import android.view.View;

/**
 * 等待刷卡
 * 
 * @author cpz
 * 
 */
@HandlerStatus(status = TxnStatus.WAIT_SWIPER)
public class WaitSwiperHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {

		if (!CardReaderManager.isInput()) {
			txnControl.changeTxnStatus(TxnStatus.WAIT_CARDREADER,
					txnControl.getCurrActivity());
			return true;
		}

		if (txnControl.getTxnContext().getPinErrorCount() > 0
				&& txnControl.getTxnContext().getPinErrorCount() < 3) {
			txnControl.changeTxnStatus(TxnStatus.WAIT_PASSWORD_OUT,
					txnControl.getCurrActivity());
			return true;
		}

		return super.preAction(txnControl);
	}

	@Override
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		activity.topTextView.setText("设备检测中...");

		APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
				.getResources(), CardReaderResourceSelector.selectGit(
				CardReaderManager.getCardReaderType(),
				CardReaderResourceSelector.SWIPER));

		activity.gifView.setVisibility(View.VISIBLE);

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
