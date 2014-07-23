package me.andpay.apos.vas.event;

import java.math.BigDecimal;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.vas.activity.CashPaymentActivity;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.View;

public class CashPaymentControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		CashPaymentActivity cashPaymentActivity = (CashPaymentActivity) activity;

		if (view.getId() == cashPaymentActivity.payButton.getId()) {

			BigDecimal inputAmt = StringConvertor
					.parseToBigDecimal(cashPaymentActivity.amtEditText
							.getText().toString());

			if (inputAmt.compareTo(new BigDecimal("0")) == 0
					|| inputAmt.compareTo(ShoppingCartCenter.getShoppingCart()
							.getTotalAmt()) >= 0) {
				cashPaymentActivity.cashPaySubmit();
				return;
			} else if (inputAmt.compareTo(ShoppingCartCenter.getShoppingCart()
					.getTotalAmt()) < 0) {
				PromptDialog promptDialog = new PromptDialog(activity, "提示",
						"您收取的现金必须大于购买商品的价格");
				promptDialog.show();
				return;
			}
		}

		if (view.getId() == cashPaymentActivity.bacImageView.getId()) {
			TiFlowControlImpl.instanceControl().previousSetup(
					cashPaymentActivity);
			return;
		}

	}

}
