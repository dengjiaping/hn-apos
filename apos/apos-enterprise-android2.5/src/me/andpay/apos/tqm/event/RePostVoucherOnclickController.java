package me.andpay.apos.tqm.event;

import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.tqm.activity.TxnDetailActivity;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class RePostVoucherOnclickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		PostVoucherContext postVoucherContext = new PostVoucherContext();
		postVoucherContext.setRePostFlag(true);
		postVoucherContext.setHasNewTxnButton(false);
		postVoucherContext.setTxnId(((TxnDetailActivity) refActivty).txnInfo
				.getTxnId());
		TiFlowControlImpl.instanceControl().startFlow(refActivty,
				FlowNames.COM_RESEND_VOUCHER_FLOW);
		TiFlowControlImpl.instanceControl().setFlowContextData(
				postVoucherContext);
	}
}
