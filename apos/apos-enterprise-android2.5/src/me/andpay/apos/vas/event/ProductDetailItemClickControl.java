package me.andpay.apos.vas.event;

import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.ProductDetailListActivity;
import me.andpay.apos.vas.activity.adapter.ProductDetailListAdapter;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class ProductDetailItemClickControl extends AbstractEventController {

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {

		ProductDetailListActivity productDetailListActivity = (ProductDetailListActivity) refActivty;
		ProductDetailListAdapter adapter = productDetailListActivity
				.getProductDetailListAdapter();
		if (position >= adapter.getCount()) {
			return;
		}
		
		ProductItem productItem = ShoppingCartCenter.getShoppingCart().getItemsList().get(position);

		Intent intent = new Intent();
		intent.setAction(VasProvider.VAS_PRODUCT_EDIT_ACTIVITY);
		intent.putExtra("productId", productItem.getProductId());
		refActivty.startActivity(intent);
	}

}
