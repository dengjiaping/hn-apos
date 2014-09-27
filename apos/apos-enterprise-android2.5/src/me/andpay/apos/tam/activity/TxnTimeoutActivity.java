package me.andpay.apos.tam.activity;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.action.TxnAction;
import me.andpay.apos.tam.callback.impl.RetrieveTxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.TxnTimeoutEventControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.SleepUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.BeanUtils;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 交易超时处理
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.tam_txn_timeout_layout)
public class TxnTimeoutActivity extends AposBaseActivity {

	private static final String TAG = TxnTimeoutActivity.class.getName();

	@InjectView(R.id.com_title_tv)
	public TextView topTitle;

	// @InjectView(R.id.com_out_btn)
	// @EventDelegate(type = DelegateType.eventController, isNeedFormBean =
	// false, delegateClass = OnClickListener.class, toEventController =
	// TxnTimeoutEventControl.class)
	// public Button outButton;

	@InjectView(R.id.com_msg_content)
	public TextView msgContent;

	@InjectView(R.id.com_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = TxnTimeoutEventControl.class)
	public Button retryBtn;

	public CommonDialog retryDialog;

	@InjectView(R.id.tam_success_img)
	public ImageView iconImge;

	private String message;
	private String title;
	private String buttonName;
	private String outButtonName;

	public String exceptionStatus;

	public TxnContext txnContext;

	private int retryCount = 1;
	private long startTime = 0;
	/**
	 * 是否是恢复的交易
	 */
	private boolean isRecover;
	@Inject
	private TxnControl txnControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		txnContext = TiFlowControlImpl.instanceControl().getFlowContextData(
				TxnContext.class);

		message = ResourceUtil.getIntentStr(getIntent(), "message");
		title = ResourceUtil.getIntentStr(getIntent(), "title");

		buttonName = ResourceUtil.getIntentStr(getIntent(), "buttonName");
		outButtonName = ResourceUtil.getIntentStr(getIntent(), "outButtonName");
		exceptionStatus = ResourceUtil.getIntentStr(getIntent(),
				"exceptionStatus");
		isRecover = ResourceUtil.getIntentBoolean(getIntent(), "isRecover");

		if (!StringUtil.isBlank(message)) {
			msgContent.setText(message);
		}
		if (!StringUtil.isBlank(title)) {
			topTitle.setText(title);
		}
		if (!StringUtil.isBlank(buttonName)) {
			retryBtn.setText(buttonName);
		}

		if (isRecover) {
			retryBtn.setVisibility(View.VISIBLE);
			iconImge.setVisibility(View.VISIBLE);
		} else {
			retryBtn.setVisibility(View.GONE);
			iconImge.setVisibility(View.GONE);
			startRetryTxn("网络不稳定，");
		}

	}

	public void startRetryTxn(String dialogMsg) {
		this.retryDialog = new CommonDialog(this, dialogMsg + "正在查询交易结果("
				+ retryCount + ")...");
		this.retryDialog.show();
		retrySubmit(dialogMsg);
	}

	public void retrySubmit(final String dialogMsg) {
		Log.e(TAG, "retryCount =" + retryCount);
		SleepUtil.sleep(3000);
		this.retryDialog.setMsg(dialogMsg + "正在查询交易结果(" + retryCount + ")...");
		retryCount++;
		startTime = System.currentTimeMillis();

		EventRequest request = this.generateSubmitRequest(this);
		request.open(TxnAction.DOMAIN_NAME, TxnAction.RETRIEVE_TXN,
				Pattern.async);
		RetrieveTxnCallbackImpl callbackImpl = new RetrieveTxnCallbackImpl(this);
		callbackImpl.txnControl = txnControl;
		request.callBack(callbackImpl);

		Map submitData = new HashMap();
		TxnForm txnForm = BeanUtils.copyProperties(TxnForm.class, txnContext);
		txnForm.setResponse(txnContext.getTxnActionResponse());
		submitData.put("txnForm", txnForm);
		Log.e(TAG, "submit retry");
		request.submit(submitData);
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
