package me.andpay.apos.vas.activity.deposite;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.callback.SvcDepositeCallbackImpl;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

@ContentView(R.layout.vas_svc_deposite_process_layout)
public class SvcDepositeAfterPayActivity extends AposBaseActivity {

	public CommonDialog progressDialog;

	@InjectView(R.id.vas_deposite_fail_layout)
	public RelativeLayout failLayout;

	@InjectView(R.id.vas_deposite_refresh_btn)
	public Button refreshBtn;

	@InjectView(R.id.vas_msg_content)
	public TextView errorMsgTv;

	@InjectView(R.id.vas_out_btn)
	public Button cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new CommonDialog(this, ResourceUtil.getString(this,
				me.andpay.apos.R.string.vas_svc_deposite_progress_str));
		hiddeErrorView();
		submitDeposite();
		refreshBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				hiddeErrorView();
				submitDeposite();
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (getCurrentFlowName().equals(FlowNames.VAS_SVC_DEPOSITE_FLOW)) {
					nextSetup(FlowConstants.FINISH);
				} else {
					previousSetup();
				}

			}
		});

	}

	public void submitDeposite() {
		progressDialog.show();
		EventRequest request = this.generateSubmitRequest(this);
		request.open(VasProvider.VAS_DOMAIN_SVC_DEPOSITE,
				VasProvider.VAS_ACTION_SVC_DEPOSITE, Pattern.async);
		request.callBack(new SvcDepositeCallbackImpl(this));
		request.getSubmitData().put("depositeContext",
				this.getFlowContextData(SvcDepositeContext.class));
		request.submit();

	}

	public void showErrorView(String errorMsg) {
		progressDialog.cancel();
		cancelBtn.setVisibility(View.VISIBLE);
		failLayout.setVisibility(View.VISIBLE);
		refreshBtn.setVisibility(View.VISIBLE);
		errorMsgTv.setText(errorMsg);
	}

	public void hiddeErrorView() {
		cancelBtn.setVisibility(View.GONE);
		failLayout.setVisibility(View.GONE);
		refreshBtn.setVisibility(View.GONE);
	}

	/**
	 * 监听返回键按钮点击事件，如果当前存在流程，则用流程控制器进行回退
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}

}
