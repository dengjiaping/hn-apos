package me.andpay.apos.tam.callback.impl;

import me.andpay.ac.term.api.pas.Coupon;
import me.andpay.ac.term.api.pas.CouponSubAcct;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.tam.activity.CouponDeailActivity;
import me.andpay.apos.tam.callback.GetCouponCallback;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;


@CallBackHandler
public class GetCouponCallbackImpl implements GetCouponCallback {
	
	
	private CouponDeailActivity activity;
	
	public GetCouponCallbackImpl(CouponDeailActivity activity) {
		this.activity = activity;
	}

	public void getCouponSuccess(Coupon coupon) {
		activity.showCoupon();
		
		PartyInfo partyInfo = (PartyInfo)activity.getAppContext().getAttribute(RuntimeAttrNames.PARTY_INFO);
		activity.partyNameText.setText(partyInfo.getPartyName());
		CouponSubAcct couponSubAcct = coupon.getSubAccts().get(0);
		activity.couponNameText.setText(coupon.getCouponName());
		if(coupon.getExpiryDate() == null) {
			activity.couponExpireText.setText("永久有效");
		}else {
			activity.couponExpireText.setText(StringUtil.format("yyyy-MM-dd", coupon.getExpiryDate()));
		}
		
		//抵扣劵
		if(couponSubAcct.getCouponType().equals("2")) {
			String amt = couponSubAcct.getAmt().toString();
			String[] amtArry = amt.split("\\.");
			if("00".equals(amtArry[1])) {
				amt = amtArry[0];
			}
			activity.couponDetailText.setText(amt);
			activity.couponDetail2Text.setText("元");
		}else if(couponSubAcct.getCouponType().equals("1")){
			
			String discount = couponSubAcct.getDiscount().toString();
			discount = discount.split("\\.")[1].replace("0", "");
			
			activity.couponDetailText.setText(discount);
			activity.couponDetail2Text.setText("折");
		}
		
		
	}

	public void getCouponFaild(String errorMsg) {
		
		final PromptDialog promptDialog = new PromptDialog(activity, "失败", errorMsg);
		promptDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				promptDialog.dismiss();
				TiFlowControlImpl.instanceControl().previousSetup(activity);
			}
		});
		promptDialog.setCancelable(false);
		promptDialog.show();
	}

	public void getCouponNetworkError(String errorMsg) {
		final OperationDialog dialog = new OperationDialog(activity,
				"提示", errorMsg);
		dialog.setCancelable(false);
		dialog.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				TiFlowControlImpl.instanceControl().previousSetup(activity);
			}
		});

		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				activity.queryCoupon();
			}
		});
		dialog.setCancelButtonName("返回");
		dialog.setSureButtonName("重试");
		dialog.setCancelable(false);
		dialog.show();
	}
	
	

}
