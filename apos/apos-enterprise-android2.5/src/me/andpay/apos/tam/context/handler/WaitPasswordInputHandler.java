package me.andpay.apos.tam.context.handler;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.tam.CardOrgImageMap;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.BitMapUtil;
import android.graphics.drawable.BitmapDrawable;
import android.text.Selection;
import android.view.View;

@HandlerStatus(status = TxnStatus.WAIT_PASSWORD)
public class WaitPasswordInputHandler extends GenChangeStatusHander {

	/**
	 * 前置处理器
	 */
	@Override
	protected boolean preAction(TxnControl txnControl) {

		/**
		 * 如果是外部输入pin跳过键盘密码输入
		 */
		if (CardReaderManager.getInputType() == AposSwiperContext.INPUT_CARD_READER) {
			txnControl.changeTxnStatus(TxnStatus.TXN_SUBMIT_WITH_PIN,
					txnControl.getCurrActivity());
			return true;
		}

		return false;
	}

	@Override
	protected void changeUI(TxnControl txnControl) {

		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();
		if (activity.isFinishing()) {
			return;
		}
		TxnContext txnContext = txnControl.getTxnContext();

		if (txnContext.isNeedPin()) {
			activity.pwdTextView.setText("");
			activity.solfKeyBoard.showKeyboard(activity.pwdTextView);
			if (txnControl.getTxnContext().getTxnType()
					.equals(TxnTypes.INQUIRY_BALANCE)) {
				activity.queryLayout.setVisibility(View.VISIBLE);
				activity.cardQueryImageView.setImageResource(CardOrgImageMap
						.getId(txnContext.getParseBinResp().getCardAssoc()));
				activity.cardQueryTextView.setText(txnContext.getCardInfo()
						.getMaskedPAN());
				activity.issuerQueryTextView.setText(txnContext
						.getParseBinResp().getCardIssuerName());
				activity.queryShowLayout.setVisibility(View.GONE);
				activity.purLayout.setVisibility(View.GONE);

			} else {
				activity.purLayout.setVisibility(View.VISIBLE);
				activity.cardLay.setVisibility(View.VISIBLE);
				activity.cardImage.setImageResource(CardOrgImageMap
						.getId(txnContext.getParseBinResp().getCardAssoc()));
				activity.cardTextView.setText(txnContext.getCardInfo()
						.getMaskedPAN());
				activity.amtImageView.setVisibility(View.VISIBLE);
				activity.issuerText.setText(txnContext.getParseBinResp()
						.getCardIssuerName());
			}
		} else {
			txnControl.changeTxnStatus(TxnStatus.TXN_SUBMIT_WITH_PIN,
					txnControl.getCurrActivity());
			return;
		}
		activity.pwdTextHint.setVisibility(View.VISIBLE);
		activity.solfKeyBoard.getSure_btn().setEnabled(true);
		activity.solfKeyBoard.getSure_btn().setBackgroundDrawable(
				activity.getResources().getDrawable(
						R.drawable.com_keyboard_button_blue_img));
		activity.solfKeyBoard.setSureButtonFlag(false);
		activity.txnLay.setBackgroundColor(activity.getResources().getColor(
				R.color.com_password_bg_color));
		activity.pwdTextView.setVisibility(View.VISIBLE);
		activity.txnBottomImage.setVisibility(View.VISIBLE);
		activity.txnContentLay.setVisibility(View.VISIBLE);
		activity.topTextView.setText(R.string.tam_top_input_pass_str);
		CardReaderManager.setDefaultCallBack();
		CardReaderManager.stopSwiper();

		activity.txnContentLay.setVisibility(View.VISIBLE);
		activity.txnBottomImage.setVisibility(View.VISIBLE);
		// 设置图片文件
		// 设置图片文件
		if (StringUtil.isNotBlank(txnControl.getTxnContext().getGoodsFileURL())) {

			if (activity.goodsMap == null) {
				activity.goodsMap = BitMapUtil.getBitmap(txnControl
						.getTxnContext().getGoodsFileURL(), 5);

				activity.goodsImg.setBackgroundDrawable(new BitmapDrawable(
						activity.getResources(), activity.goodsMap));
				activity.goodsLay.setVisibility(View.VISIBLE);
			}

		} else {
			activity.goodsLay.setVisibility(View.GONE);
		}
		activity.amtTxnView.setText(txnContext.getAmtFomat());

		activity.pwdTextHint.requestFocus();
		Selection.setSelection(activity.pwdTextHint.getEditableText(), 0);

	}

	@Override
	protected void changeAction(TxnControl txnControl) {

	}

}
