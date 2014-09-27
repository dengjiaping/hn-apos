package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.vas.event.VasMainEventControl;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@ContentView(R.layout.vas_main_layout)
@TIFLowSignTask
public class VasMainActivity extends AposBaseActivity {

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@InjectView(R.id.vas_sales_product_layout)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = VasMainEventControl.class)
	public RelativeLayout salesLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void startQueryFlow(View view) {
		getControl().startFlow(this, FlowNames.VAS_PUR_QUERY_FLOW);

	}

	public void startSvcDepositeFlow(View view) {
		getControl().startFlow(this, FlowNames.VAS_SVC_DEPOSITE_FLOW);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
