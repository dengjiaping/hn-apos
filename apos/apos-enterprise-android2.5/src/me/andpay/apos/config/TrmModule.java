package me.andpay.apos.config;

import me.andpay.apos.trm.event.AmtEventControl;
import me.andpay.apos.trm.event.NoCardBtnClickController;
import me.andpay.apos.trm.event.RefundAmtEventControl;
import me.andpay.apos.trm.event.RefundBatchTxnRefreshController;
import me.andpay.apos.trm.event.RefundButtonClickController;
import me.andpay.apos.trm.event.RefundCodBtClickController;
import me.andpay.apos.trm.event.RefundTxnItemClickController;
import me.andpay.timobileframework.config.TiMobileModule;

public class TrmModule extends TiMobileModule {

	@Override
	protected void configure() {
		bindEventController(AmtEventControl.class);
		bindEventController(NoCardBtnClickController.class);
		bindEventController(RefundBatchTxnRefreshController.class);
		bindEventController(RefundButtonClickController.class);
		bindEventController(RefundCodBtClickController.class);
		bindEventController(RefundTxnItemClickController.class);
		bindEventController(RefundAmtEventControl.class);
	}

}
