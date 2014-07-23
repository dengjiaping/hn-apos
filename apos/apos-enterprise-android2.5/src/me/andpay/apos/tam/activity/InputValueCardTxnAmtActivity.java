package me.andpay.apos.tam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog.OnKeyboardListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.InputValueCardTxnAmtEventControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.google.inject.Inject;

@ContentView(R.layout.tam_input_valuecard_txn_amt_layout)
public class InputValueCardTxnAmtActivity extends AposBaseActivity implements
		OnKeyboardListener {

	public SolfKeyBoardDialog solfKeyBoard;

	@InjectView(R.id.tam_amt_txnview)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = InputValueCardTxnAmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = InputValueCardTxnAmtEventControl.class) })
	public AmtEditTextView amtEditText;

	@InjectView(R.id.com_wrap_input_lay)
	public RelativeLayout rootView;

	@Inject
	private TxnControl txnControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics metric = new DisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(metric);
		solfKeyBoard = SolfKeyBoardDialog.instance(this, rootView,
				metric.heightPixels
						- Float.valueOf(330 * metric.density).intValue(), this);
		solfKeyBoard.getHintImgeBtn().setVisibility(View.GONE);
		amtEditText.requestFocus();

	}

	public void sureClick() {

		String txnAmt = amtEditText.getText().toString();
		String couponResult = (String) getIntent().getExtras().get(
				"couponResult");

//		CardValueTxnHelper.sendTxn(txnAmt, couponResult, txnControl, this);

	}

}
