package me.andpay.apos.merchantservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 商户信息
 * 
 * @author Administrator
 *
 */

@ContentView(R.layout.merchants_information)
public class MerchantsInformationActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "base", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_base)
	private TextView base;

	@EventDelegate(type = DelegateType.method, toMethod = "settlement", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_settlement)
	private TextView settlement;

	@InjectView(R.id.merchants_information_content)
	private LinearLayout content;

	private View baseView;
	private View settlementView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseView = ShowUtil.LoadXmlView(this, R.layout.ms_base_information);
		settlementView = ShowUtil.LoadXmlView(this,
				R.layout.ms_settlement_information);
		base(null);
	}

	@SuppressLint("NewApi")
	public void base(View view) {
		base.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		base.setTextColor(getResources().getColor(android.R.color.white));
		settlement.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		settlement.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		content.removeAllViews();
		content.addView(baseView);

	}

	@SuppressLint("NewApi")
	public void settlement(View view) {
		settlement.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		settlement.setTextColor(getResources().getColor(android.R.color.white));
		base.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		base.setTextColor(getResources().getColor(android.R.color.darker_gray));

		content.removeAllViews();
		content.addView(settlementView);

	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

}
