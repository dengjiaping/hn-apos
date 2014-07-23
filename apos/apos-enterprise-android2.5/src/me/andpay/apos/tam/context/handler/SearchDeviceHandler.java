package me.andpay.apos.tam.context.handler;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import android.util.DisplayMetrics;
import android.view.View;

@HandlerStatus(status = TxnStatus.SEARCH_DEVICE)
public class SearchDeviceHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {
		return super.preAction(txnControl);
	}

	@Override
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		activity.topTextView.setText("蓝牙匹配中...");

		APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
				.getResources(), CardReaderResourceSelector.selectGit(
				CardReaderManager.getCardReaderType(),
				CardReaderResourceSelector.SEARCH));

		activity.gifView.setVisibility(View.VISIBLE);

	}

	@Override
	protected void changeAction(TxnControl txnControl) {
		// TxnAcitivty activity = (TxnAcitivty)txnControl.getCurrActivity();
		// activity.cardReaderManager.startSwiper(txnControl.getTxnContext());
	}
}
