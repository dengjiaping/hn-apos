package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.vas.event.ProductPaymentModeBackControl;
import me.andpay.apos.vas.event.SelectPaymentControl;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 支付方式选择
 * 
 * @author cpz
 * 
 */

@ContentView(R.layout.vas_product_payment_mode_layout)
public class ProductPaymentModeActivity extends AposBaseActivity {

	@InjectView(R.id.vas_cash_payment_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectPaymentControl.class)
	public RelativeLayout cashPayLay;

	@InjectView(R.id.vas_card_payment_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectPaymentControl.class)
	public RelativeLayout cardPayLay;

	@InjectView(R.id.vas_title_tv)
	public TextView titleText;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ProductPaymentModeBackControl.class)
	public ImageView backBtn;

	@Inject
	public TxnControl txnControl;

	public static final String PAYMENT_METHOD_KEY = "PayMethod";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		titleText.setText(StringConvertor.convert2Currency(ShoppingCartCenter
				.getShoppingCart().getTotalAmt()));
	}
}
