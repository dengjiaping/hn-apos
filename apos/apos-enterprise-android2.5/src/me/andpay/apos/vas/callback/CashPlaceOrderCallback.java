package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.CashPaymentSuccessActivity;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class CashPlaceOrderCallback implements PurchaseOrderCallback {

	private CashPaymentSuccessActivity cashPaymentSuccessActivity;
	
	public CashPlaceOrderCallback(CashPaymentSuccessActivity cashPaymentSuccessActivity) {
		this.cashPaymentSuccessActivity = cashPaymentSuccessActivity;
	}

	public void networkError() {
		cashPaymentSuccessActivity.placeDialog.cancel();
		
		
		final PromptDialog dialog = new PromptDialog(cashPaymentSuccessActivity,
				"提示", "网络异常，请稍后重试,在网络恢复前请不要退出此页面。");
		dialog.setCancelable(false);
		

		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				//cashPaymentSuccessActivity.placeOrderSubmit();
			}
		});
		dialog.show();
		dialog.setButtonText("重 试");
	}

	public void placeOrderFailed(String errorMsg) {
		cashPaymentSuccessActivity.placeDialog.cancel();
	}

	public void placeOrderSuccess(PurchaseOrder purchaseOrder) {
	
		cashPaymentSuccessActivity.placeDialog.cancel();
		
		ProductSalesContext productSalesContext = TiFlowControlImpl.instanceControl().getFlowContextData(ProductSalesContext.class);
		productSalesContext.setPurchaseOrder(purchaseOrder);
		TiFlowControlImpl.instanceControl().nextSetup(cashPaymentSuccessActivity, FlowConstants.SUCCESS);
		
		ShoppingCartCenter.clearShoppingCard();
	}

}
