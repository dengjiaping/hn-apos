package me.andpay.apos.tam.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tam.activity.CouponDeailActivity;
import me.andpay.apos.tam.callback.RedeemCouponCaillback;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class RedeemCouponCaillbackImpl implements RedeemCouponCaillback {

	private CouponDeailActivity activity;

	public RedeemCouponCaillbackImpl(CouponDeailActivity activity) {
		this.activity = activity;
	}

	public void redeemSuccess(int count) {
		activity.redeemDialog.cancel();
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("message", "您的优惠劵还可兑换" + count + "次");
		intentData.put("buttonName", "查看兑换列表");
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS, intentData);
	}

	public void redeemFail(String errorMsg) {
		final PromptDialog dialog = new PromptDialog(activity, "提示", errorMsg);
		dialog.setCancelable(false);
		dialog.setButtonText("重新扫描");
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				TiFlowControlImpl.instanceControl().previousSetup(activity);
			}
		});
		dialog.show();

	}

	public void redeemNetwork(String errorMsg) {
		final OperationDialog dialog = new OperationDialog(activity, "提示",
				errorMsg);
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
				activity.redeemCoupon();
			}
		});
		dialog.setCancelButtonName("返回");
		dialog.setSureButtonName("重试");
		dialog.show();

	}

}
