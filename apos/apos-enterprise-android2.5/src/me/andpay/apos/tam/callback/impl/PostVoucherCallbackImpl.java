package me.andpay.apos.tam.callback.impl;

import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.activity.PostVoucherActivity;
import me.andpay.apos.tam.callback.PostVoucherCallback;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.widget.Toast;

@CallBackHandler
public class PostVoucherCallbackImpl implements PostVoucherCallback {

	private PostVoucherActivity postActivity;

	public PostVoucherCallbackImpl(PostVoucherActivity postActivity) {
		this.postActivity = postActivity;
	}

	public void dealResponse() {

		postActivity.postDialog.cancel();

		Toast.makeText(
				postActivity.getApplicationContext(),
				ResourceUtil.getString(postActivity.getApplicationContext(),
						R.string.tam_post_voucher_success_str),
				Toast.LENGTH_SHORT).show();

		if (StringUtil.isBlank(postActivity.postVoucherContext.getReceiptNo())) {
		//	TiFlowControlImpl.instanceControl().nextSetup(postActivity,
			//		FlowConstants.FINISH);
			postActivity.txnControl.backHomePage(postActivity);

		} else {
			postActivity.sumbitPurchaseOrder();

		}
	}

	public void netWorkerror() {
		postActivity.postDialog.cancel();
		Toast toast = Toast.makeText(postActivity.getApplicationContext(),
				"网络异常", Toast.LENGTH_LONG);
		toast.show();
	}

}
