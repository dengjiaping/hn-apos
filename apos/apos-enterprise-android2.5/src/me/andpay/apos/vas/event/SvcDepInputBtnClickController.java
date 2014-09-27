package me.andpay.apos.vas.event;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.deposite.SvcDepositeCardInputActivity;
import me.andpay.apos.vas.callback.SvcValidateCallbackImp;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 提交卡号验证
 * 
 * @author tinyliu
 *
 */
public class SvcDepInputBtnClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		this.submit((SvcDepositeCardInputActivity) activity);
	}

	public void submit(SvcDepositeCardInputActivity activity) {

		// String cardNo = "9600000700000057";
		String cardNo = (String) activity.cardNo.getWidgetValue();
		// String repeatCardNo = (String) activity.cardNo.getWidgetValue();
		if (StringUtil.isEmpty(cardNo)) {
			activity.alertErrorMsg(ResourceUtil.getString(activity,
					R.string.vas_user_opencard_validate_null_str));
			return;
		}
		EventRequest request = this.generateSubmitRequest(activity);
		request.callBack(new SvcValidateCallbackImp(activity));
		request.open(VasProvider.VAS_DOMAIN_SVC_DEPOSITE,
				VasProvider.VAS_ACTION_SVC_VALIDATE, Pattern.async);
		request.getSubmitData().put("cardNo", cardNo);
		request.submit();
		activity.diCommonDialog.show();
	}

}
