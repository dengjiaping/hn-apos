package me.andpay.apos.tam.event;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.apos.tam.activity.TxnFaildActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

/**
 * 交易失败 重新刷卡事件
 * 
 * @author cpz
 * 
 */
public class TxnFaildEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	public void onClick(Activity activity, FormBean formBean, View view) {

		TxnFaildActivity faildActivity = (TxnFaildActivity) activity;
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);

		if (faildActivity.retryBtn.getId() == view.getId()) {
			if (faildActivity.isStartNewFlow()) {
				OrderPayContext newOrderPayContext = TiFlowControlImpl
						.instanceControl()
						.getFlowContextData(OrderPayContext.class)
						.newInstance();
				newOrderPayContext.setNeedAutoLogin(false);
				TiFlowControlImpl.instanceControl().startFlow(faildActivity,
						FlowNames.MOPM_MERCHANT_ORDER_PAY_FLOW);
				TiFlowControlImpl.instanceControl().setFlowContextData(
						newOrderPayContext);
				faildActivity.setStartNewFlow(false);
			} else {
				if (TxnTypes.PURCHASE.equals(txnContext.getTxnType())) {
					txnControl.retrySwiperInFlow(activity);
					return;
				} else {
					// 设置已完成一笔交易
					TiFlowControlImpl.instanceControl().previousSetup(activity);
					return;

				}
			}

		}

		if (faildActivity.outButton.getId() == view.getId()) {

			if (TxnTypes.PURCHASE.equals(txnContext.getTxnType())) {
				faildActivity.getAppContext().setAttribute(
						RuntimeAttrNames.NEXT_TXN, RuntimeAttrNames.NEXT_TXN);
				txnControl.backHomePage(faildActivity);
				return;
			} else {
				txnControl.getTxnContext().setBackTagName(TabNames.REFUND_PAGE);
				txnControl.backHomePage(faildActivity);
				return;

			}

		}

	}
}
