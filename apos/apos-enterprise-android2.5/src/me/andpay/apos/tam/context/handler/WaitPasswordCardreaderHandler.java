package me.andpay.apos.tam.context.handler;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import android.util.DisplayMetrics;
import android.view.View;

@HandlerStatus(status = TxnStatus.WAIT_PASSWORD_OUT)
public class WaitPasswordCardreaderHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {

		if (!CardReaderManager.isInput()) {
			txnControl.changeTxnStatus(TxnStatus.WAIT_CARDREADER,
					txnControl.getCurrActivity());
			return true;
		}
		return super.preAction(txnControl);
	}

	@Override
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		activity.topTextView.setText(R.string.tam_top_input_pass_str);

		APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
				.getResources(), CardReaderResourceSelector.selectGit(
				CardReaderManager.getCardReaderType(),
				CardReaderResourceSelector.INPUTPW));
		activity.gifView.setVisibility(View.VISIBLE);
	}

	@Override
	protected void changeAction(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();

		CardReaderManager.setCurrCallback(txnControl
				.getSwipCardReaderCallBack());
		CardReaderManager.startSwiper(AposCardReaderUtil.convertSwiperRequest(
				txnControl.getTxnContext(), activity.getAppConfig()));
	}
}
