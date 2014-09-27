package me.andpay.apos.merchantsmaking.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.apos.merchantsmaking.flow.FlowNames;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

@ContentView(R.layout.merchants_making)
public class MerchantsMakingActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_making_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "coupons", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_making_coupons_layout)
	private View coupons;

	public void back(View view) {
		HomePageActivity hp = (HomePageActivity) this.getParent();
		hp.showSlider();
		// TiFlowControlImpl.instanceControl().previousSetup(this);

	}

	public void coupons(View view) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.MMAKING_COUPONS);

	}

}
