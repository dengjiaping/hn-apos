package me.andpay.apos.tam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.tam.action.CouponAction;
import me.andpay.apos.tam.callback.impl.GetCouponCallbackImpl;
import me.andpay.apos.tam.callback.impl.RedeemCouponCaillbackImpl;
import me.andpay.apos.tam.event.CancelCouponControl;
import me.andpay.apos.tam.event.RedeemCouponCouponControl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.tam_coupon_detail_layout)
public class CouponDeailActivity extends AposBaseActivity {

	@InjectView(R.id.com_processing_layout)
	public LinearLayout progess;

	@InjectView(R.id.tam_coupan_lay)
	public RelativeLayout couponLayout;
	
	
	@InjectView(R.id.tam_merchant_name_tv)
	public TextView partyNameText;
	
	@InjectView(R.id.tam_coupon_detail_tv)
	public TextView couponDetailText;
	@InjectView(R.id.tam_coupon_detail2_tv)
	public TextView couponDetail2Text;
	@InjectView(R.id.tam_coupon_name_tv)
	public TextView couponNameText;
	
	@InjectView(R.id.tam_coupon_expire_tv)
	public TextView couponExpireText;


	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CancelCouponControl.class)
	@InjectView(R.id.tam_cancel_coupon_btn)
	public Button cancelCoupon;
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = RedeemCouponCouponControl.class)
	@InjectView(R.id.tam_redeem_coupon_btn)
	public Button redeemCoupon;
	
	
	public CommonDialog redeemDialog;
	
	@Inject
	public LocationService locationService;

	private String couponInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		locationService.requestLocation();
		couponInfo = (String) getIntent().getExtras().get("couponInfo");
		queryCoupon();
	}

	public void queryCoupon() {
		EventRequest request = this.generateSubmitRequest(this);
		request.open(CouponAction.DOMAIN_NAME, CouponAction.GET_COUPON,
				Pattern.async);
		request.callBack(new GetCouponCallbackImpl(this));
		request.getSubmitData().put("couponInfo", couponInfo);
		request.submit();
		showProgress();
	}

	public void redeemCoupon() {
		EventRequest request = this.generateSubmitRequest(this);
		request.open(CouponAction.DOMAIN_NAME, CouponAction.REDEEM_COUPON,
				Pattern.async);
		request.callBack(new RedeemCouponCaillbackImpl(this));
		request.getSubmitData().put("couponInfo", couponInfo);
		redeemDialog = new CommonDialog(this, "处理中...");
		redeemDialog.setCancelable(false);
		redeemDialog.show();		
		request.submit();
	}

	public void showProgress() {
		progess.setVisibility(View.VISIBLE);
		couponLayout.setVisibility(View.GONE);
	}

	public void showCoupon() {
		progess.setVisibility(View.GONE);
		couponLayout.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationService.unRegisterLocation();
	}

}
