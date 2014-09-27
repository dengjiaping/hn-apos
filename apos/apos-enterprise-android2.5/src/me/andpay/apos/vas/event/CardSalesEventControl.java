package me.andpay.apos.vas.event;

import me.andpay.apos.vas.activity.SalesCardMainActivity;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.apos.vas.spcart.ShoppingCartHelper;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.text.Editable;
import android.view.View;

public class CardSalesEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		SalesCardMainActivity salesActivity = (SalesCardMainActivity) activity;
		android.util.Log.e(this.getClass().getName(), "product unit into");

		ProductItem productItem = ShoppingCartCenter.getShoppingCart()
				.getItemsList().get(0);

		if (view.getId() == salesActivity.addProductImagView.getId()) {

			ShoppingCartCenter.addProduct(productItem);
			android.util.Log.e(this.getClass().getName(), "product unit"
					+ ShoppingCartHelper.getProductUnit(productItem.getUnit()));
			freshShow(salesActivity, productItem);

			return;
		}

		if (view.getId() == salesActivity.subProductImgView.getId()) {

			// 不能删除为0
			if (productItem.getUnit() == 1) {
				return;
			}
			ShoppingCartCenter.subProduct(productItem.getProductId());
			freshShow(salesActivity, productItem);

			return;
		}

		if (view.getId() == salesActivity.backImage.getId()) {

			ShoppingCartCenter.clearShoppingCard();
			TiFlowControlImpl.instanceControl().previousSetup(salesActivity);
			return;
		}

	}

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		SalesCardMainActivity salesCardMainActivity = (SalesCardMainActivity) activity;
		ProductItem productItem = ShoppingCartCenter.getShoppingCart()
				.getItemsList().get(0);

		if (StringUtil.isBlank(s.toString())) {
			ShoppingCartCenter.subProduct(productItem.getProductId(),
					productItem.getUnit());
			freshShow(salesCardMainActivity, productItem);
			return;
		}

		if (!StringConvertor.isNumber(s.toString()) || s.equals("0")
				|| s.equals("00")) {
			ShoppingCartCenter.subProduct(productItem.getProductId(),
					productItem.getUnit());
			freshShow(salesCardMainActivity, productItem);
			return;
		}

		String quantityStr = salesCardMainActivity.productQuantityText
				.getText().toString();
		salesCardMainActivity.productQuantityText.setSelection(quantityStr
				.length());

		Integer productQuantity = Integer.valueOf(quantityStr)
				- productItem.getUnit();
		if (productQuantity == 0) {
			return;
		}
		if (productQuantity > 0) {
			ShoppingCartCenter.addProduct(productItem, productQuantity);
		} else {
			ShoppingCartCenter.subProduct(productItem.getProductId(),
					-productQuantity);
		}

		freshShow(salesCardMainActivity, productItem);

	}

	private void freshShow(SalesCardMainActivity salesCardMainActivity,
			ProductItem productItem) {
		salesCardMainActivity.productQuantityText.setText(Integer.valueOf(
				productItem.getUnit()).toString());
		salesCardMainActivity.priceTextView.setText(StringConvertor
				.convert2Currency(ShoppingCartCenter.getShoppingCart()
						.getTotalAmt()));
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
