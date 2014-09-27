package me.andpay.apos.vas.event;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.vas.activity.ProductSalesActivity;
import me.andpay.apos.vas.activity.adapter.ProductListAdapter;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.apos.vas.spcart.ShoppingCartHelper;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

public class ProductItemClickControl extends AbstractEventController {

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {

		final ProductSalesActivity productSalesActivity = (ProductSalesActivity) refActivty;
		ProductListAdapter adapter = productSalesActivity
				.getProductListAdapter();

		if (position >= adapter.getCount()) {
			return;
		}

		final ProductInfo productInfo = (ProductInfo) adapter.getItem(position);
		boolean isShopCart = ShoppingCartCenter.addProduct(ShoppingCartHelper
				.productInfoConvertItem(productInfo));
		if (!isShopCart) {
			final OperationDialog operationDialog = new OperationDialog(
					refActivty, "选择产品+" + productInfo.getName(),
					"您无法单独购买此产品，如果你选择加入，原来选择的产品将会清空。");

			operationDialog.setSureButtonName("加入");
			operationDialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					operationDialog.dismiss();
					ShoppingCartCenter.clearShoppingCard();
					ShoppingCartCenter.addProduct(ShoppingCartHelper
							.productInfoConvertItem(productInfo));
					freshShow(productSalesActivity);
					// 特殊产品流程
					specialFlow(ShoppingCartCenter.getShoppingCart()
							.getProductType(), productSalesActivity);
				}
			});

			operationDialog.show();
			return;
		}

		freshShow(productSalesActivity);

		// 特殊产品流程
		specialFlow(ShoppingCartCenter.getShoppingCart().getProductType(),
				productSalesActivity);

	}

	private void specialFlow(String productType,
			ProductSalesActivity productSalesActivity) {
		if (ShopProductTypes.E_SVC.equals(productType)
				|| ShopProductTypes.PHYSICAL_SVC.equals(productType)) {
			TiFlowControlImpl.instanceControl().startFlow(productSalesActivity,
					FlowNames.VAS_PRODUCT_SALES_CARD_FLOW);
			ProductSalesContext productSalesContext = new ProductSalesContext();
			TiFlowControlImpl.instanceControl().setFlowContextData(
					productSalesContext);
			productSalesContext.setShoppingCart(ShoppingCartCenter
					.getShoppingCart());
			return;
		}
	}

	private void freshShow(ProductSalesActivity productSalesActivity) {
		// ShoppingCart shoppingCart = ShoppingCartCenter.getShoppingCart();

		// productSalesActivity.totalPrice.setText(StringConvertor
		// .convert2Currency(shoppingCart.getTotalAmt()));
		// productSalesActivity.productTotal.setText(ShoppingCartHelper
		// .getProductUnit(shoppingCart.getTotalProduct()));
		// productSalesActivity.productTotalLay.setVisibility(View.VISIBLE);

		// if (shoppingCart.getTotalProduct() > 0) {
		// productSalesActivity.nextImView.setEnabled(true);
		// } else {
		// productSalesActivity.nextImView.setEnabled(false);
		// }
		//
		// if (shoppingCart.getItemsList().isEmpty()) {
		// productSalesActivity.nextImView.setEnabled(false);
		// } else {
		// productSalesActivity.nextImView.setEnabled(true);
		//
		// }
	}

}
