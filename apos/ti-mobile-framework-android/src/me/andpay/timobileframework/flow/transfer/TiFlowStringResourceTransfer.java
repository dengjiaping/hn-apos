package me.andpay.timobileframework.flow.transfer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import android.app.Activity;

/**
 * 用于转换String Resource
 * 
 * @author tinyliu
 * 
 */
public class TiFlowStringResourceTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity, Map<String, String> data,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext) {
		Map<String, String> transferData = new HashMap<String, String>();
		for (String key : data.keySet()) {
			int resId = activity.getResources().getIdentifier(
					activity.getPackageName() + ":string/" + data.get(key), null, null);
			if (resId == 0) {
				continue;
			}
			String transferValue = activity.getResources().getString(resId);
			if (StringUtil.isEmpty(transferValue)) {
				transferValue = data.get(key);
			}

			transferData.put(key, transferValue);
		}
		return transferData;
	}

}
