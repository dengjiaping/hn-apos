package me.andpay.apos.tam.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.QrScanType;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.View;

public class ShowQRScanViewControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		PurchaseFirstActivity purchaseFirstActivity = (PurchaseFirstActivity) activity;

		if (!purchaseFirstActivity.checkGpsEnable()) {
			return;
		}
		
		String amt = purchaseFirstActivity.amtEditText.getText().toString();

		// 超额照片限制
//		if (purchaseFirstActivity.amtLimit(StringConvertor.parseToBigDecimal(amt))) {
//			return;
//		}
//		
		
		if (amt.length() < 0
				|| amt.toString()
						.equals(activity.getResources().getString(
								R.string.com_amt_str))) {
			purchaseFirstActivity.showDialog(activity.getResources().getString(
					R.string.tam_input_txnamt_str));
			return;
		}

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("txnAmt", purchaseFirstActivity.amtEditText.getText()
				.toString());
		intentData.put("goodFileUrl", purchaseFirstActivity.goodFileUrl);
		intentData.put("scanType", QrScanType.ST_ECARD);
		// 进入电子卡消费流程
		TiFlowControlImpl.instanceControl().startFlow(purchaseFirstActivity,
				FlowNames.TAM_ECARD_TXN_FLOW, intentData);
	}

}
