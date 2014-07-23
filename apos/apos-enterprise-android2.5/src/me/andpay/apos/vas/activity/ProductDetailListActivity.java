package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.activity.adapter.ProductDetailListAdapter;
import me.andpay.apos.vas.event.ProductCommonBackControl;
import me.andpay.apos.vas.event.ProductDetailItemClickControl;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@ContentView(R.layout.vas_product_detail_list_layout)
public class ProductDetailListActivity extends AposBaseActivity {

	public ProductDetailListAdapter productDetailListAdapter;

	@InjectView(R.id.vas_product_list)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = ProductDetailItemClickControl.class)
	public ListView productList;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ProductCommonBackControl.class)
	public ImageView backBtn;

	@InjectView(R.id.vas_total_price_text)
	public TextView priceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResumeProcess() {
		if (ShoppingCartCenter.getShoppingCart().getTotalProduct() == 0) {
			this.finish();
			return;
		}

		if (productDetailListAdapter == null) {
			productDetailListAdapter = new ProductDetailListAdapter(this);

			View footView = LayoutInflater.from(this).inflate(
					R.layout.vas_product_list_foot_view, null);
			productList.addFooterView(footView);
			footView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ShoppingCartCenter.clearShoppingCard();
					finish();
				}
			});

			productList.setAdapter(productDetailListAdapter);
		}

		productDetailListAdapter.notifyDataSetChanged();

		priceView.setText(StringConvertor.convert2Currency(ShoppingCartCenter
				.getShoppingCart().getTotalAmt()));

	}

	public ProductDetailListAdapter getProductDetailListAdapter() {
		return productDetailListAdapter;
	}

}
