package me.andpay.apos.opm.callback.impl;

import me.andpay.ac.term.api.txn.order.InquiryOrderResponse;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.OpmProvider;
import me.andpay.apos.opm.activity.InputOrderNoActivity;
import me.andpay.apos.opm.callback.InquiryOrderCallback;
import me.andpay.apos.opm.form.OrderConverter;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class InquiryOrderCallbackImpl implements InquiryOrderCallback {

	private InputOrderNoActivity inputOrderNoActivity;

	public InquiryOrderCallbackImpl(InputOrderNoActivity inputOrderNoActivity) {
		this.inputOrderNoActivity = inputOrderNoActivity;
	}

	public void clear() {
		if (inputOrderNoActivity == null || inputOrderNoActivity.isFinishing()) {
			return;
		}

		inputOrderNoActivity.queryDialog.cancel();

	}

	public void querySuccess(InquiryOrderResponse inquiryOrderResponse) {

		clear();

		OrderInfo info = OrderConverter.convertRecord(inquiryOrderResponse
				.getOrderRecords().get(0));
		Intent orderDetailIntent = new Intent(
				OpmProvider.OPM_ORDERDETAIL_ACTIVITY);
		byte[] infoByte = JacksonSerializer.newPrettySerializer().serialize(
				info.getClass(), info);
		orderDetailIntent.putExtra("orderInfo", infoByte);
		inputOrderNoActivity.startActivity(orderDetailIntent);
	}

	public void networkError() {
		clear();
		final OperationDialog dialog = new OperationDialog(
				inputOrderNoActivity, "提示", "网络异常请重试！", true);

		dialog.setCancelButtonName("取消");
		dialog.setSureButtonName("重新查询");
		dialog.setCancelable(false);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				inputOrderNoActivity.queryOrder();
				dialog.dismiss();
			}
		});

		dialog.setCancelButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				inputOrderNoActivity.orderNoText.postDelayed(new Runnable() {

					public void run() {
						inputOrderNoActivity.showKeyborad();
					}
				}, 500);

			}
		});
		dialog.show();

	}

	public void queryFaild(String errorMsg) {
		clear();
		final PromptDialog dialog = new PromptDialog(inputOrderNoActivity,
				"查询失败", errorMsg);
		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				inputOrderNoActivity.orderNoText.postDelayed(new Runnable() {

					public void run() {
						inputOrderNoActivity.showKeyborad();
					}
				}, 500);
			}
		});
		dialog.show();
	}

}
