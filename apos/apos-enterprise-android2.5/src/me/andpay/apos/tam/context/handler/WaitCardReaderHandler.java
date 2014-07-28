package me.andpay.apos.tam.context.handler;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import android.view.View;

/**
 * 等待卡号处理器
 * 
 * @author cpz
 * 
 */

@HandlerStatus(status = TxnStatus.WAIT_CARDREADER)
public class WaitCardReaderHandler extends GenChangeStatusHander {

	@Override
	protected void changeUI(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
	
		
		APOSGifUtil.startGif(activity.gifDrawable, activity.gifView, activity
				.getResources(), CardReaderResourceSelector.selectGit(
				CardReaderManager.getCardReaderType(),
				CardReaderResourceSelector.CONNECT));
		activity.gifView.setVisibility(View.VISIBLE);

		activity.topTextView.setText(R.string.tam_top_connet_cardreader_str);
	}

}
