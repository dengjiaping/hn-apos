package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.widget.TextView;

/**
 * 现金支付页面
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.vas_cash_payment_layout)
public class ProductCashPayActivity extends AposBaseActivity {

	@InjectView(R.id.vas_title_tv)
	public TextView titleText;
	
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		titleText.setText(StringConvertor.convert2Currency(ShoppingCartCenter.getShoppingCart().getTotalAmt()));
	};
}
