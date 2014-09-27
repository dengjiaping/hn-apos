package me.andpay.apos.vas.activity;

import com.google.inject.Inject;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.vas.event.CardSalesEventControl;
import me.andpay.apos.vas.event.CardSalesPaymentMethodEventControl;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 售卡主页
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.vas_sales_card_layout)
public class SalesCardMainActivity extends AposBaseActivity {

	/**
	 * 减少产品数量
	 */
	@InjectView(R.id.vas_sub_product_imgview)
	@EventDelegate(type = DelegateType.eventController, isPassFastClick = true, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CardSalesEventControl.class)
	public ImageView subProductImgView;

	/**
	 * 添加产品数量
	 */
	@InjectView(R.id.vas_add_product_imgview)
	@EventDelegate(type = DelegateType.eventController, isPassFastClick = true, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CardSalesEventControl.class)
	public ImageView addProductImagView;

	@InjectView(R.id.vas_product_quantity_text)
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CardSalesEventControl.class)
	public EditText productQuantityText;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(type = DelegateType.eventController, delegateClass = OnClickListener.class, toEventController = CardSalesEventControl.class)
	public ImageView backImage;

	@InjectView(R.id.vas_title_tv)
	public TextView vasTopTexView;

	@InjectView(R.id.vas_product_price_text)
	public TextView priceTextView;

	@InjectView(R.id.vas_cash_payment_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CardSalesPaymentMethodEventControl.class)
	public RelativeLayout cashPayLay;

	@InjectView(R.id.vas_card_payment_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CardSalesPaymentMethodEventControl.class)
	public RelativeLayout cardPayLay;

	@Inject
	private LocationService locationService;

	private ProductSalesContext productSalesContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		locationService.requestLocation();
		productSalesContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(ProductSalesContext.class);

		// 售卡购物车只有一种产品
		ProductItem productItem = productSalesContext.getShoppingCart()
				.getItemsList().get(0);
		productQuantityText.setText(Integer.valueOf(productItem.getUnit())
				.toString());
		productQuantityText
				.setSelection(productQuantityText.getText().length());
		vasTopTexView.setText(productItem.getProductName());
		priceTextView.setText(StringConvertor
				.convert2Currency(ShoppingCartCenter.getShoppingCart()
						.getTotalAmt()));

		productQuantityText
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationService.unRegisterLocation();
		ShoppingCartCenter.clearShoppingCard();
	}

	/*
	 * @Override protected void onResume() { super.onResume();
	 * 
	 * }
	 */
}
