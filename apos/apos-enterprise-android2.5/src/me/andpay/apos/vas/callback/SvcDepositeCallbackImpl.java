package me.andpay.apos.vas.callback;

import java.math.BigDecimal;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.deposite.SvcDepositeAfterPayActivity;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class SvcDepositeCallbackImpl implements SvcDepositeCallback{
	
	private SvcDepositeAfterPayActivity activity = null;
	
	public SvcDepositeCallbackImpl(SvcDepositeAfterPayActivity activity) {
		this.activity = activity;
	}

	public void depositeSucc(BigDecimal balance) {
		activity.progressDialog.cancel();
		activity.getFlowContextData(SvcDepositeContext.class).setBalance(balance);
		activity.nextSetup(FlowConstants.SUCCESS);
		
	}

	public void depositeFail(String errorMsg) {
		activity.showErrorView(errorMsg);
	}

}
