package me.andpay.apos.opm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.activity.OrderDetailActivity;
import me.andpay.apos.opm.helper.OrderPayHelper;
import me.andpay.apos.tam.action.txn.cloud.CloudPosUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OrderDetailPayController  extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		OrderDetailActivity activity = (OrderDetailActivity)refActivty;
		//订单支付不支持云pos
		if(CloudPosUtil.isCloudPosCardReader(activity)) {
			PromptDialog dialog = new PromptDialog(activity, null, ResourceUtil.getString(
					activity, R.string.tam_cloud_txntype_error_str));
			dialog.show();
			return;
		}
		OrderInfo orderInfo= activity.orderInfo;
		OrderPayHelper.sendTxn(refActivty, orderInfo);
		
	}
}
