package me.andpay.apos.tqrm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.constant.QrScanType;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ShowScanCouponControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
	
		Map<String, String> intentData = new HashMap<String,String>();
		intentData.put("scanType", QrScanType.ST_COUPON);
		TiFlowControlImpl.instanceControl().startFlow(activity, FlowNames.TQRM_COUPON_FLOW,intentData);
	}

}
