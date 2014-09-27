package me.andpay.apos.tqm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.apos.tqm.activity.TxnDetailActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.View;

public class FillSignController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		TxnDetailActivity txnDetailActivity = (TxnDetailActivity) refActivty;
		PayTxnInfo payTxnInfo = txnDetailActivity.getTxnInfo();

		TiFlowControlImpl.instanceControl().startFlow(refActivty,
				FlowNames.COM_SIGN_FLOW);

		SignContext signContext = new SignContext();
		signContext.setAmtTextColor(refActivty.getResources().getColor(
				R.color.tqm_list_item_amount_col));
		signContext.setShowAmt(StringConvertor.convert2Currency(payTxnInfo
				.getSalesAmt()));
		signContext.setTermTraceNo(payTxnInfo.getTermTraceNo());
		signContext.setTermTxnTime(payTxnInfo.getTermTxnTime());
		signContext.setShowBackBtn(true);
		signContext.setIdUnderType(txnDetailActivity.txnInfo.getSignPic());

		TiFlowControlImpl.instanceControl().setFlowContextData(signContext);

	}

}
