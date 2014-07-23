package me.andpay.apos.vas.activity.deposite;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.apos.cmview.TiSpinner;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.vas.event.CardNoTextEventController;
import me.andpay.apos.vas.event.SvcDepPriceBtnClickController;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@ContentView(R.layout.vas_svc_deposite_select_layout)
public class SvcDepositePriceSelectActivity extends AposBaseActivity implements
		me.andpay.apos.cmview.SolfKeyBoardDialog.OnKeyboardListener {

	@InjectView(R.id.com_root_view)
	public View rootView;

	@InjectView(R.id.vas_deposite_cardNo_tv)
	public TextView cardNoTv;

	@InjectView(R.id.vas_deposite_cardName_tv)
	public TextView cardNameTv;

	@InjectView(R.id.vas_deposite_balance_tv)
	public TextView balanceTv;

	@InjectView(R.id.vas_deposite_price_sp)
	public TiSpinner priceSp;

	@InjectView(R.id.vas_amt_text)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = CardNoTextEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CardNoTextEventController.class) })
	public AmtEditTextView amtEditText;

	@InjectView(R.id.vas_cash_payment_lay)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SvcDepPriceBtnClickController.class)
	public RelativeLayout cashButton;

	@InjectView(R.id.vas_card_payment_lay)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SvcDepPriceBtnClickController.class)
	public RelativeLayout cardButton;

	@InjectView(R.id.vas_amt_input_lay)
	public RelativeLayout amtInputLay;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView bacImageView;

	public SolfKeyBoardDialog solfKeyBoardDialog;

	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confingAmtPriceView();
		setCardInfo();
	}

	private void setCardInfo() {
		SvcDepositeContext dContext = this.getFlowContextData(SvcDepositeContext.class);
		this.cardNameTv.setText(dContext.getCardName());
		this.cardNoTv.setText(dContext.getCardNo());
		this.balanceTv.setText(StringConvertor.format(dContext.getBalance()));
	}

	private void confingAmtPriceView() {
		SvcDepositeContext dContext = this.getFlowContextData(SvcDepositeContext.class);
		amtEditText.clearFocus();
		priceSp.clearFocus();
		amtInputLay.setVisibility(dContext.isHasCtrlPrice() ? View.GONE : View.VISIBLE);
		priceSp.setVisibility(dContext.isHasCtrlPrice() ? View.VISIBLE : View.GONE);
		if (dContext.isHasCtrlPrice()) {
			List<Pair<String, String>> values = new ArrayList<Pair<String, String>>();
			for (String str : dContext.getCtrlAmtDesc()) {
				Pair<String, String> value = new Pair<String, String>(str, "");
				values.add(value);
			}
			priceSp.setSpinnerValues(values);
		//	priceSp.clear();
		} else {
			DisplayMetrics metric = new DisplayMetrics();
			Display display = getWindowManager().getDefaultDisplay();
			display.getMetrics(metric);
			solfKeyBoardDialog = SolfKeyBoardDialog.instance(this, cardButton,
					metric.heightPixels - Float.valueOf(350 * metric.density).intValue(),
					this);
			solfKeyBoardDialog.getHintImgeBtn().setVisibility(View.VISIBLE);
			solfKeyBoardDialog.showKeyboard(amtEditText);
		}
	}

	public void sureClick() {
		solfKeyBoardDialog.hideKeyboard();
	}
}
