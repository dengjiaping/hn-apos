package me.andpay.apos.tam.context.handler;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.flow.model.TxnContext;
import android.util.DisplayMetrics;
import android.view.View;

@HandlerStatus(status = TxnStatus.SHOW_WAIT_SWIPER)
public class ShowWaitSwiperHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {

		return super.preAction(txnControl);
	}

	@Override
	protected void changeUI(TxnControl txnControl) {

		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		TxnContext txnContext = txnControl.getTxnContext();
		if (txnContext.isNeedPin() && txnContext.getPinErrorCount() < 3
				&& txnContext.getPinErrorCount() > 0) {

			activity.topTextView.setText(R.string.tam_top_input_pass_str);

			APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
					.getResources(), CardReaderResourceSelector.selectGit(
					CardReaderManager.getCardReaderType(),
					CardReaderResourceSelector.INPUTPW));
		} else {
			DisplayMetrics metric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);


			if (ExtTypes.EXT_TYPE_TXN_GET.equals(txnControl.getTxnContext()
					.getExtType())) {


				APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
						.getResources(), CardReaderResourceSelector.selectGit(
						CardReaderManager.getCardReaderType(),
						CardReaderResourceSelector.GETTXN));
				activity.topTextView.setText("交易获取中...");

			} else {

				APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
						.getResources(), CardReaderResourceSelector.selectGit(
						CardReaderManager.getCardReaderType(),
						CardReaderResourceSelector.SWIPER));

				activity.topTextView.setText("请刷卡或插入IC卡");

			}

		}

		activity.gifView.setVisibility(View.VISIBLE);

	}

	@Override
	protected void changeAction(TxnControl txnControl) {

	}
}
