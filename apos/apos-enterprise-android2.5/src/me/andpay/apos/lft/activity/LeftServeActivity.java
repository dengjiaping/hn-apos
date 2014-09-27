package me.andpay.apos.lft.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.apos.lft.flow.FlowNames;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

@ContentView(R.layout.lft_serve_home_layout)
public class LeftServeActivity extends AposBaseActivity {
	@InjectView(R.id.lft_serve_home_show_silder_btn)
	@EventDelegate(type = DelegateType.method, toMethod = "backMenu", delegateClass = OnClickListener.class)
	private ImageView menu;// 返回菜单按钮

	@InjectView(R.id.lft_serve_home_phone_topup_layout)
	@EventDelegate(type = DelegateType.method, toMethod = "topup", delegateClass = OnClickListener.class)
	private View topup;// 手机充值

	@InjectView(R.id.lft_serve_home_pay_electricity_cost_layout)
	@EventDelegate(type = DelegateType.method, toMethod = "eleCost", delegateClass = OnClickListener.class)
	private View payEleCost;// 缴电费

	@InjectView(R.id.lft_serve_home_pay_water_cost_layout)
	@EventDelegate(type = DelegateType.method, toMethod = "wteCost", delegateClass = OnClickListener.class)
	private View payWtCose;// 缴水费

	@InjectView(R.id.lft_serve_home_card_refund_layout)
	@EventDelegate(type = DelegateType.method, toMethod = "cartPay", delegateClass = OnClickListener.class)
	private View cardPay;// 信用卡还款

	@InjectView(R.id.lft_serve_home_transfer_accounts_layout)
	@EventDelegate(type = DelegateType.method, toMethod = "transferCount", delegateClass = OnClickListener.class)
	private View transferCount;// 转账

	/*
	 * ========================================================事件触发==============
	 * =================================================
	 */
	/**
	 * 回到滑动菜单
	 */
	public void backMenu(View v) {
		HomePageActivity hp = (HomePageActivity) this.getParent();
		hp.showSlider();
	}

	/**
	 * 充值
	 * 
	 * @param v
	 */
	public void topup(View v) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.LFT_TOP_UP);
	}

	/**
	 * 缴纳电费
	 * 
	 * @param v
	 */
	public void eleCost(View v) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.LFT_PAY_ELECTRICITY);
	}

	/**
	 * 缴纳水费
	 * 
	 * @param v
	 */
	public void wteCost(View v) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.LFT_PAY_WATER);
	}

	/**
	 * 信用卡还款
	 * 
	 * @param v
	 */
	public void cartPay(View v) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.LFT_CREDIT_CARD_PAYMENTS);
	}

	/**
	 * 启动转账流程
	 * 
	 * @param v
	 */
	public void transferCount(View v) {
		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.LFT_TRANSFER_ACCOUNTS);
	}
}
