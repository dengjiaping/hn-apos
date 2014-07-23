package me.andpay.apos.config;

import me.andpay.apos.tqm.action.QueryRemoteTxnAction;
import me.andpay.apos.tqm.action.QueryTxnAction;
import me.andpay.apos.tqm.event.ClearConditionController;
import me.andpay.apos.tqm.event.MapLayoutOnclickController;
import me.andpay.apos.tqm.event.QueryBatchStatusButtonClickController;
import me.andpay.apos.tqm.event.QueryBatchTxnItemClickController;
import me.andpay.apos.tqm.event.QueryBatchTxnRefreshController;
import me.andpay.apos.tqm.event.QueryCodButtonClickController;
import me.andpay.apos.tqm.event.RePostVoucherOnclickController;
import me.andpay.apos.tqm.event.TxnQueryConditionCancelClickController;
import me.andpay.apos.tqm.event.TxnQueryConditionSureClickController;
import me.andpay.timobileframework.config.TiMobileModule;

public class TqmModule extends TiMobileModule {

	@Override
	protected void configure() {
		bindAction(QueryTxnAction.class);
		bindAction(QueryRemoteTxnAction.class);
		bindEventController(QueryBatchTxnRefreshController.class);
		bindEventController(MapLayoutOnclickController.class);
		bindEventController(QueryBatchStatusButtonClickController.class);
		bindEventController(QueryBatchTxnItemClickController.class);
		bindEventController(QueryCodButtonClickController.class);
		bindEventController(TxnQueryConditionSureClickController.class);
		bindEventController(TxnQueryConditionCancelClickController.class);
		bindEventController(ClearConditionController.class);
		bindEventController(RePostVoucherOnclickController.class);
	}

}
