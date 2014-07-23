package me.andpay.apos.vas.event;

import java.math.BigDecimal;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.activity.deposite.SvcDepositePriceSelectActivity;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.View;

/**
 * 完成金额选择事件
 * 
 * @author tinyliu
 * 
 */
public class SvcDepPriceBtnClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		this.nextSetup((SvcDepositePriceSelectActivity) activity, view);
	}

	public void nextSetup(SvcDepositePriceSelectActivity activity, View view) {
		SvcDepositeContext dContext = activity
				.getFlowContextData(SvcDepositeContext.class);
		BigDecimal depositeAmt = null;
		if (!dContext.isHasCtrlPrice()) {
			String amount = (String) activity.amtEditText.getWidgetValue();
			if (!StringUtil.isEmpty(amount)) {
				depositeAmt = StringConvertor.parseToBigDecimal(amount);
			}

		} else {
			if (activity.priceSp.getSelectedItemId() >= 0) {
				depositeAmt = dContext.getSelectPrice(activity.priceSp
						.getSelectedItemId());
			}
		}
		if (depositeAmt == null) {
			activity.alertErrorMsg(ResourceUtil.getString(activity,
					R.string.vas_svc_deposite_input_fail_str));
			return;
		}

		dContext.setDepositeAmount(depositeAmt);
		ProductItem item = new ProductItem();
		item.setUnit(1);
		dContext.setPaymentMethod(activity.cashButton.getId() == view.getId() ? PaymentMethods.CASH
				: PaymentMethods.SWIPING);
		item.setProductName(ResourceUtil.getString(activity,
				R.string.vas_svc_deposite_product_name_str));
		item.setProductType(ShopProductTypes.SVC_DEPOSIT);
		item.setPrice(dContext.getDepositeAmount());
		item.setAttr(dContext.convertCardInfoToString());
		ShoppingCartCenter.clearShoppingCard();
		ShoppingCartCenter.addProduct(item);
		activity.setFlowContextData(ShoppingCartCenter.getShoppingCart());
		activity.nextSetup(activity.cashButton.getId() == view.getId() ? me.andpay.apos.vas.flow.FlowConstants.ND_CASH_PAYMENT
				: me.andpay.apos.vas.flow.FlowConstants.ND_CARD_PAYMENT);
	}
}
