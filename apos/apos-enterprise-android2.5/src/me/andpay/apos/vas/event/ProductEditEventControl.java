package me.andpay.apos.vas.event;

import me.andpay.apos.vas.activity.ProductEditActivity;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.apos.vas.spcart.ShoppingCartHelper;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ProductEditEventControl  extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		ProductEditActivity editActivity = (ProductEditActivity)activity;
		android.util.Log.e(this.getClass().getName(),"product unit into");

		if(view.getId() == editActivity.addProductImagView.getId()) {
			ProductItem productItem = ShoppingCartCenter.getProduct(editActivity.productId);
			ShoppingCartCenter.addProduct(productItem);
			android.util.Log.e(this.getClass().getName(),"product unit"+ShoppingCartHelper.getProductUnit(productItem.getUnit()));
			editActivity.productQuantityText.setText(Integer.valueOf(productItem.getUnit()).toString());
			return;
		}
		
		if(view.getId() == editActivity.subProductImgView.getId()) {
			ProductItem productItem =  ShoppingCartCenter.getProduct(editActivity.productId);
			//不能删除为0
			if(productItem.getUnit() == 1) {
				return;
			}
			ShoppingCartCenter.subProduct(editActivity.productId);
			editActivity.productQuantityText.setText(Integer.valueOf(productItem.getUnit()).toString());
			return;
		}
		
		if(view.getId() == editActivity.deleteProduct.getId()) {
			ShoppingCartCenter.deleteProduct(editActivity.productId);
			activity.finish();
			return;
		}
	}
}
