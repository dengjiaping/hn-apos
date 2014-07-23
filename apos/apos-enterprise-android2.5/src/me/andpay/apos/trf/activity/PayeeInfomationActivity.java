package me.andpay.apos.trf.activity;

import me.andpay.apos.R;
import me.andpay.apos.trf.event.AmtChangedEventControl;
import me.andpay.apos.trf.event.AmtTouchEventControl;
import me.andpay.apos.trf.event.ChangeFocusAndShowSliderControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@ContentView(R.layout.trf_payee_info_layout)
public class PayeeInfomationActivity extends TiActivity {

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ChangeFocusAndShowSliderControl.class)
	public ImageView showSilder;

	@InjectView(R.id.trf_payee_amount_editText_layout)
	public RelativeLayout amtViewLayout;

	@InjectView(R.id.trf_payee_amount_editText)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = AmtTouchEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtTouchEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = AmtChangedEventControl.class) })
	public EditText amtEditTextView;

	@InjectView(R.id.trf_payee_info_card_edit)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtTouchEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = AmtChangedEventControl.class) })
	public EditText cardEditTextView;

	@InjectView(R.id.trf_next_step_button_btn)
	public Button nextStepButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
