package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.GetSvcDepositCtrlsResponse;
import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.activity.deposite.SvcDepositeCardInputActivity;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class SvcValidateCallbackImp implements SvcValidateCallback {

	private SvcDepositeCardInputActivity activity;

	public SvcValidateCallbackImp(SvcDepositeCardInputActivity activity) {
		this.activity = activity;
	}

	public void validateSucc(GetSvcDepositCtrlsResponse response) {
		activity.diCommonDialog.cancel();
		SvcDepositeContext dContext = activity
				.getFlowContextData(SvcDepositeContext.class);
		dContext.setCardNo(response.getCardNo());
		dContext.setDepositCtrls(activity.getApplication(), response.getDepositCtrls());
		dContext.setCardType(response.getCardType());
		dContext.setCardName(response.getCardName());
		dContext.setBalance(response.getBalance());
		activity.nextSetup(FlowConstants.SUCCESS);
	}

	public void validateFailed(String errorMsg) {
		if (StringUtil.isEmpty(errorMsg)) {
			errorMsg = ResourceUtil.getString(activity,
					R.string.vas_user_opencard_validate_fail_str);
		}
		activity.diCommonDialog.cancel();
		activity.alertErrorMsg(errorMsg);
		activity.cardNo.requestFocus();

	}

}
