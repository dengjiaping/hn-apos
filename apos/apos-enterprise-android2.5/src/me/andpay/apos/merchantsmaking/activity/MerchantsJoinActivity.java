package me.andpay.apos.merchantsmaking.activity;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantsmaking.event.CouponsEventController;
import me.andpay.apos.merchantsmaking.event.MerchantsJoinEventController;
import me.andpay.apos.merchantsmaking.flow.FlowContants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 商户加盟
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.merchants_join_layout)
public class MerchantsJoinActivity extends AposBaseActivity {
	/**
	 * 返回
	 */
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.mmaking_join_back)
	private ImageView back;

	/* 积分商户 */
	@InjectView(R.id.merchants_join_integral)
	private RadioButton integralMc;

	/* 优惠券商户 */
	@InjectView(R.id.merchants_join_coupons)
	private RadioButton couponsMc;

	/* 商户名称 */
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = MerchantsJoinEventController.class)
	@InjectView(R.id.merchants_join_name)
	public EditText name;

	/* 商户类别 */
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = MerchantsJoinEventController.class)
	@InjectView(R.id.merchants_join_type)
	public EditText typeMc;

	/* 联系方式 */
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = MerchantsJoinEventController.class)
	@InjectView(R.id.merchants_join_phonenumber)
	public EditText phoneNumber;

	/* 联系地址 */
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = MerchantsJoinEventController.class)
	@InjectView(R.id.merchants_join_adress)
	public EditText adress;

	/* 简介 */
	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = MerchantsJoinEventController.class)
	@InjectView(R.id.merchants_join_introduce)
	public EditText introduce;

	/* 提交 */
	@EventDelegate(type = DelegateType.method, toMethod = "commit", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_join_commit)
	public Button commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		integralMc.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				couponsMc.setChecked(!isChecked);

			}
		});
		couponsMc.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				integralMc.setChecked(!isChecked);

			}
		});
	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 提交
	 * 
	 * @param view
	 */
	public void commit(View view) {
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowContants.MCCOMMIT_DETAIL);

	}
}
