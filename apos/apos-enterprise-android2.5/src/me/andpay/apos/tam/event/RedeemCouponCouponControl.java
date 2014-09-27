package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.CouponDeailActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class RedeemCouponCouponControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		CouponDeailActivity aCouponDeailActivity = (CouponDeailActivity) activity;
		aCouponDeailActivity.redeemCoupon();
	}

}
