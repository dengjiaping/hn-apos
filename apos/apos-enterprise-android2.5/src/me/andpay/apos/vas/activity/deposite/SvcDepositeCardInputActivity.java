package me.andpay.apos.vas.activity.deposite;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CardNoEditText;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog.OnKeyboardListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.vas.event.CardNoTextEventController;
import me.andpay.apos.vas.event.SvcDepInputBtnClickController;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.inject.Inject;

@ContentView(R.layout.vas_svc_deposite_input_layout)
public class SvcDepositeCardInputActivity extends AposBaseActivity implements
		OnKeyboardListener {

	@InjectView(R.id.vas_cardNo_text)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = CardNoTextEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CardNoTextEventController.class) })
	public CardNoEditText cardNo;

	@InjectView(R.id.vas_submit_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SvcDepInputBtnClickController.class)
	public Button submitBtn;

	public SolfKeyBoardDialog solfKeyBoardDialog;

	// @InjectView(R.id.vas_content_scroll)
	// public ScrollView scrollView;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@Inject
	public SvcDepInputBtnClickController controller;

	public CommonDialog diCommonDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metric);

		solfKeyBoardDialog = SolfKeyBoardDialog.instance(this, cardNo
				.getRootView(), Float.valueOf((300 * metric.density))
				.intValue(), this);
		// solfKeyBoardDialog.setOnClickHideButtonListener(this);
		diCommonDialog = new CommonDialog(this, "处理中...");
		diCommonDialog.setCancelable(false);
		SvcDepositeContext dContext = new SvcDepositeContext();
		this.setFlowContextData(dContext);
		solfKeyBoardDialog.showKeyboard(cardNo);
	}

	public void sureClick() {
		controller.submit(this);
	}

	/*
	 * public void alertErrorMsg(String errorMsg) { PromptDialog dialog = new
	 * PromptDialog(this, "错误提示", errorMsg); dialog.show(); }
	 */

	/*
	 * public void onClickHide() { DisplayMetrics metric = new DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(metric);
	 * scrollView.layout(0, 0, metric.widthPixels, metric.heightPixels); }
	 */
}
