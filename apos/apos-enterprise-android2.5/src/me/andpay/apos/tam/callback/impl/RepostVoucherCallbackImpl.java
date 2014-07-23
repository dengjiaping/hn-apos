package me.andpay.apos.tam.callback.impl;

import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.activity.RepostVoucherActivity;
import me.andpay.apos.tam.callback.PostVoucherCallback;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.widget.Toast;

@CallBackHandler
public class RepostVoucherCallbackImpl implements PostVoucherCallback {

	private RepostVoucherActivity repostActivity;

	public RepostVoucherCallbackImpl(RepostVoucherActivity repostActivity) {
		this.repostActivity = repostActivity;
	}

	public void dealResponse() {

		repostActivity.postDialog.cancel();

		Toast.makeText(
				repostActivity.getApplicationContext(),
				ResourceUtil.getString(repostActivity.getApplicationContext(),
						R.string.tam_post_voucher_success_str),
				Toast.LENGTH_SHORT).show();

		if (StringUtil
				.isBlank(repostActivity.postVoucherContext.getReceiptNo())) {
			TiFlowControlImpl.instanceControl().nextSetup(repostActivity,
					FlowConstants.GOHOME);
		} else {
			repostActivity.txnControl.backHomePage(repostActivity);
			// postActivity.sumbitPurchaseOrder();
		}
	}

	public void netWorkerror() {
		repostActivity.postDialog.cancel();
		Toast toast = Toast.makeText(repostActivity.getApplicationContext(),
				"网络异常", Toast.LENGTH_LONG);
		toast.show();
	}

}
