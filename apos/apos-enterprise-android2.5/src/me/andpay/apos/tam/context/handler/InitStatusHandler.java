package me.andpay.apos.tam.context.handler;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.BitMapUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

/**
 * 初始化状态
 * 
 * @author cpz
 * 
 */
@HandlerStatus(status = TxnStatus.INIT)
public class InitStatusHandler extends GenChangeStatusHander {

	@Override
	protected boolean preAction(TxnControl txnControl) {

		return super.preAction(txnControl);

	}

	/**
	 * 处理状态变更
	 * 
	 * @param txnControl
	 */
	@Override
	public void changeUI(TxnControl txnControl) {

		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();

		if (txnControl.getTxnContext().getTxnType()
				.equals(TxnTypes.INQUIRY_BALANCE)) {
			activity.queryShowLayout.setVisibility(View.VISIBLE);
			activity.txnBottomImage.setVisibility(View.GONE);
			activity.queryLayout.setVisibility(View.GONE);
		}
		else {
			activity.purLayout.setVisibility(View.VISIBLE);
			activity.txnContentLay.setVisibility(View.VISIBLE);
			activity.txnBottomImage.setVisibility(View.VISIBLE);
			if(txnControl.getTxnContext().getTxnType().equals(TxnType.MPOS_TOPUP)){
				activity.topupShowLayout.setVisibility(View.VISIBLE);
			}else if(txnControl.getTxnContext().getTxnType().equals(TxnType.MPOS_PAYCOST_ELE)){
				activity.payCostShowLayout.setVisibility(View.VISIBLE);
				activity.payCostxt.setText(activity.getResources().getString(R.string.tam_pay_ele_tips_str));
			}else if(txnControl.getTxnContext().getTxnType().equals(TxnType.MPOS_PAYCOST_WATER)){
				activity.payCostShowLayout.setVisibility(View.VISIBLE);
				activity.payCostxt.setText(activity.getResources().getString(R.string.tam_pay_water_tips_str));
			}else if(txnControl.getTxnContext().getTxnType().equals(TxnType.MPOS_PAY_CREDIT_CARD)){
				activity.payCostShowLayout.setVisibility(View.VISIBLE);
				activity.payCostxt.setText(activity.getResources().getString(R.string.tam_pay_crite_card_tips_str));
			}else if(txnControl.getTxnContext().getTxnType().equals(TxnType.MPOS_TRANSFER_ACCOUNT)){
				activity.payCostShowLayout.setVisibility(View.VISIBLE);
				activity.payCostxt.setText(activity.getResources().getString(R.string.tam_transfer_account_tips_str));
			}
		}

		TxnContext txnContext = txnControl.getTxnContext();

		if (txnContext.getTxnType().equals(TxnTypes.PURCHASE)||txnContext.getTxnType().equals(TxnType.MPOS_TOPUP)) {
			activity.amtTxnView.setTextColor(activity.getResources().getColor(
					R.color.tqm_list_item_amount_col));
		} else {
			activity.amtTxnView.setTextColor(activity.getResources().getColor(
					R.color.com_title_red_color));
		}

		activity.amtTxnView.setText(txnContext.getAmtFomat());
		CardReaderManager
				.initCardReader(activity.getApplicationContext(),
						AposCardReaderUtil.genAposSwiperContext(activity
								.getAppConfig()));

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
	}

	@Override
	protected void changeAction(TxnControl txnControl) {
		TxnAcitivty activity = (TxnAcitivty) txnControl.getCurrActivity();

		if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_BLUETOOTH) {
			txnControl.changeTxnStatus(TxnStatus.TRY_CONN_DEVICE, activity);
		} else {
			if (CardReaderManager.isInput()) {
				txnControl.changeTxnStatus(TxnStatus.WAIT_SWIPER, activity);
			} else {
				txnControl.changeTxnStatus(TxnStatus.WAIT_CARDREADER, activity);
			}
		}

	}

}
