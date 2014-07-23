package me.andpay.apos.common.activity;

import me.andpay.apos.R;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;


@ContentView(R.layout.com_show_qr_result_layout)
public class ShowQrResultActivity extends AposBaseActivity{
	
	@InjectView(R.id.com_qr_result_tv)
	private TextView qrTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String couponInfo = (String) getIntent().getExtras().get("couponInfo");
		qrTextView.setText(couponInfo);
	}
	
	
	/**
	 * 监听返回键按钮点击事件，如果当前存在流程，则用流程控制器进行回退
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return false;
		}

		return super.onKeyDown(keyCode, event);

	}
}
