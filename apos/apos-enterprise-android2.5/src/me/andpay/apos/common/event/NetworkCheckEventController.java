package me.andpay.apos.common.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.app.Activity;

/**
 * 在事件被响应之前检查网络设置
 * 
 * @author tinyliu
 * 
 */
public class NetworkCheckEventController extends AbstractEventController {

	@Override
	public Boolean preProcess(Activity refActivty) {
		if (!NetWorkUtil.isNetworkConnected(refActivty.getApplicationContext())) {
			String netErrorMsg = refActivty.getApplicationContext()
					.getResources().getString(R.string.com_net_check_error_str);
			PromptDialog dialog = new PromptDialog(
					refActivty.getApplicationContext(), null, netErrorMsg);
			dialog.show();
			return false;
		}
		return super.preProcess(refActivty);
	}

}
