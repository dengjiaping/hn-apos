package me.andpay.apos.merchantsmaking.activity;

import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.tools.MathUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.even.CardPayTextWatcherEventControl;
import me.andpay.apos.merchantsmaking.event.CouponsEventController;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 优惠券
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.mmaking_coupons)
public class CouponsActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.mmaking_coupons_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CouponsEventController.class)
	@InjectView(R.id.mmaking_coupons_card_number)
	public EditText cardNumber;

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CouponsEventController.class)
	@InjectView(R.id.mmaking_coupons_phone_number)
	public EditText phoneNumber;

	@EventDelegate(type = DelegateType.method, toMethod = "couponsSure", delegateClass = OnClickListener.class)
	@InjectView(R.id.mmaking_coupons_sure)
	public Button couponsSure;

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);

	}

	public void couponsSure(View view) {
		String phoneStr = phoneNumber.getText().toString();

		boolean isMobile = MathUtil.isMobileNumber(phoneStr);
		if (!isMobile) {

			ShowUtil.showShortToast(this, "非手机号");
		} else {
			ShowUtil.showShortToast(this, "领取");
		}
	}
}
