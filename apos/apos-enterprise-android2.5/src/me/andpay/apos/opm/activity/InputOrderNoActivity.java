package me.andpay.apos.opm.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.slider.simonvt.menudrawer.MenuDrawer;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.activity.MenuChangeEvent;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.opm.action.QueryOrderAction;
import me.andpay.apos.opm.callback.impl.InquiryOrderCallbackImpl;
import me.andpay.apos.opm.event.OrderNoTextEventControl;
import me.andpay.apos.opm.event.QueryOrderEventControl;
import me.andpay.apos.opm.event.ShowOrderNoHelpEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.opm_input_order_no_layout)
public class InputOrderNoActivity extends AposBaseActivity implements
		ValueContainer, MenuChangeEvent {

	@InjectView(R.id.opm_query_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = QueryOrderEventControl.class)
	public Button queryBtn;

	@InjectView(R.id.opm_order_no_edit)
	@EventDelegateArray({ @EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = OrderNoTextEventControl.class) })
	public EditText orderNoText;

	public CommonDialog queryDialog;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@InjectView(R.id.opm_help_text_view)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowOrderNoHelpEventController.class)
	public TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResumeProcess() {
		fleshView();
		orderNoText.performClick();

		orderNoText.postDelayed(new Runnable() {
			public void run() {
				showKeyborad();
			}
		}, 1000);

	}

	private void fleshView() {
		Object flag = getAppContext().getAttribute(RuntimeAttrNames.NEXT_TXN);
		if (flag != null && flag.toString().equals(RuntimeAttrNames.NEXT_TXN)) {
			getAppContext().removeAttribute(RuntimeAttrNames.NEXT_TXN);
			orderNoText.setText("");
			queryBtn.setEnabled(false);
		}
	}

	public void queryOrder() {
		EventRequest eventRequest = generateSubmitRequest(this);
		eventRequest.open(QueryOrderAction.QUERY_ORDER_ACTION,
				QueryOrderAction.INQUIRY_QUERY_ORDER, Pattern.async);
		Map<String, String> submitData = new HashMap<String, String>();
		submitData.put("orderNo", orderNoText.getText().toString());
		eventRequest.callBack(new InquiryOrderCallbackImpl(this));
		eventRequest.submit(submitData);

		queryDialog = new CommonDialog(this, "订单查询中...");
		hideKeyborad();
		queryDialog.show();

	}

	public void onMenuStateChange(int oldState, int newState) {
		if (newState == MenuDrawer.STATE_OPENING) {
			hideKeyborad();
		} else if (newState == MenuDrawer.STATE_CLOSED) {
			showKeyborad();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onPause() {
		super.onPause();
		hideKeyborad();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showKeyborad() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(orderNoText, InputMethodManager.SHOW_FORCED);
	}

	public void hideKeyborad() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(orderNoText.getWindowToken(), 0); // 强制隐藏键盘
	}
}
