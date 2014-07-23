package me.andpay.apos.vas.activity;

import java.math.BigDecimal;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.event.CashPaymentSuccessNextControl;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.vas_cashpay_success_layout)
public class CashPaymentSuccessActivity extends AposBaseActivity {

	@InjectView(R.id.vas_open_card_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CashPaymentSuccessNextControl.class)
	public Button openCard;

	@InjectView(R.id.vas_msg_content)
	public TextView msgContent;

	@InjectView(R.id.com_title_tv)
	public TextView topTile;
	
	public CommonDialog placeDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResumeProcess() {
		CashPaymentContext cashPaymentContext = TiFlowControlImpl
				.instanceControl()
				.getFlowContextData(CashPaymentContext.class);

		if (cashPaymentContext.getCashAmt() == null
				|| cashPaymentContext.getCashAmt()
						.compareTo(new BigDecimal(0)) == 0) {
			msgContent.setText("交易成功");
		} else {
			BigDecimal lastAmt = cashPaymentContext.getCashAmt().subtract(
					cashPaymentContext.getShoppingCart().getTotalAmt());
			msgContent.setText("交易成功，找零" + lastAmt.toString() + "元");
		}
		topTile.setText(StringConvertor.convert2Currency(cashPaymentContext.getShoppingCart().getTotalAmt()));
	}

	/**
	 * placeOrder
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
