package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.TiTimeButton;
import me.andpay.apos.cmview.TiTimeButton.OnTimeoutListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.action.OpenCardAction;
import me.andpay.apos.vas.callback.SendEcardCallbackImp;
import me.andpay.apos.vas.event.OpenCardSuccessControl;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.vas_opencard_success_layout)
public class OpenCardSuccessActivity extends AposBaseActivity {

	@InjectView(R.id.vas_sure_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = OpenCardSuccessControl.class)
	public Button sureBtn;

	@InjectView(R.id.vas_title_tv)
	public TextView topTextView;

	@InjectView(R.id.vas_msg_content)
	public TextView msgContent;

	@InjectView(R.id.vas_event_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = OpenCardSuccessControl.class)
	public TiTimeButton resendBtn;

	public CommonDialog commonDialog;

	private OpenCardContext openCardContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		openCardContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(OpenCardContext.class);

		if (StringUtil.isBlank(openCardContext.getPhoneNo())) {
			resendBtn.setVisibility(View.GONE);
		} else {
			resendBtn.setVisibility(View.VISIBLE);
			resendBtn.setEnabled(false);
			resendBtn.setOnTimeoutListener(new OnTimeoutListener() {
				public void onTimeout() {
					resendBtn.setEnabled(true);
					resendBtn.setText(R.string.vas_resend_str);
				}
			});
			resendBtn.startTimer(20);
		}
	};

	@Override
	protected void onResumeProcess() {
		topTextView.setText(StringConvertor.convert2Currency(openCardContext
				.getCardSalesAmt()));
		msgContent.setText("开卡成功");
	}

	public void sendSvcEcard() {

		OpenCardContext openCardContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(OpenCardContext.class);

		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("orderId", openCardContext.getOrderId());
		request.open(OpenCardAction.DOMAIN_NAME, OpenCardAction.SEND_SVC_ECARD,
				Pattern.async);
		commonDialog = new CommonDialog(this, "处理中...");
		commonDialog.show();
		request.callBack(new SendEcardCallbackImp(this));
		request.submit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
