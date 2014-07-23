package me.andpay.apos.vas.event;

import me.andpay.apos.vas.activity.ProductEditActivity;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class ProductEditWatcherEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		ProductEditActivity productEditActivity = (ProductEditActivity) activity;

		String quantityStr = productEditActivity.productQuantityText.getText()
				.toString();
		productEditActivity.productQuantityText.setSelection(quantityStr
				.length());

		ProductItem productItem = ShoppingCartCenter
				.getProduct(productEditActivity.productId);

		Integer productQuantity = Integer.valueOf(quantityStr)
				- productItem.getUnit();
		if (productQuantity == 0) {
			return;
		}

		if (productQuantity > 0) {

			ShoppingCartCenter.addProduct(productItem, productQuantity);

		} else {

			ShoppingCartCenter.subProduct(productEditActivity.productId,
					-productQuantity);

		}

	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
