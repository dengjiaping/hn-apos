package me.andpay.apos.vas.activity.deposite;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.vas_svc_deposite_success_layout)
public class SvcDepositeSuccActivity extends AposBaseActivity {

	@InjectView(R.id.vas_msg_content)
	private TextView balanceView;

	@InjectView(R.id.vas_event_btn)
	private Button finishButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SvcDepositeContext dContext = this
				.getFlowContextData(SvcDepositeContext.class);
		balanceView.setText(balanceView.getText()
				+ dContext.getBalance().setScale(2).toPlainString());
		finishButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				nextSetup(me.andpay.apos.common.flow.FlowConstants.SUCCESS);
			}
		});
	}

	/**
	 * 监听返回键按钮点击事件，如果当前存在流程，则用流程控制器进行回退
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}
}
