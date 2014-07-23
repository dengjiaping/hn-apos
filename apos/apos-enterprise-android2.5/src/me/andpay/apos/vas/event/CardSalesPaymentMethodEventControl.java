package me.andpay.apos.vas.event;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.vas.activity.SalesCardMainActivity;
import me.andpay.apos.vas.flow.FlowConstants;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class CardSalesPaymentMethodEventControl extends AbstractEventController {

	@Inject
	private LocationService locationService;

	public void onClick(Activity activity, FormBean formBean, View view) {

		SalesCardMainActivity salesActivity = (SalesCardMainActivity) activity;

		// 初始化

		ProductSalesContext productSalesContext = TiFlowControlImpl
				.instanceControl()
				.getFlowContextData(ProductSalesContext.class);

		if (ShoppingCartCenter.getShoppingCart().getTotalProduct() <= 0) {

			PromptDialog daDialog = new PromptDialog(salesActivity, "提示",
					"您无法购买数量为0的"
							+ ShoppingCartCenter.getShoppingCart()
									.getItemsList().get(0).getProductName()+"。");
			daDialog.show();
			return;
		}

		if (salesActivity.cashPayLay.getId() == view.getId()) {
			productSalesContext.setPaymeneMethed(PaymentMethods.CASH);
			TiFlowControlImpl.instanceControl().nextSetup(salesActivity,
					FlowConstants.ND_CASH_PAYMENT);
			return;
		}

		if (salesActivity.cardPayLay.getId() == view.getId()) {
			TiFlowControlImpl.instanceControl().nextSetup(salesActivity,
					FlowConstants.ND_CARD_PAYMENT);
			productSalesContext.setPaymeneMethed(PaymentMethods.SWIPING);
			return;
		}

	}

}
