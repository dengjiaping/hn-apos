package me.andpay.apos.vas.activity;

import java.util.Date;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.vas.action.PurchaseOrderAction;
import me.andpay.apos.vas.callback.CashPaymentCallbackImpl;
import me.andpay.apos.vas.event.CashPaymentControl;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.form.PurchaseOrderForm;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 现金收款支付
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.vas_cash_payment_layout)
public class CashPaymentActivity extends AposBaseActivity implements
		me.andpay.apos.cmview.SolfKeyBoardDialog.OnKeyboardListener {

	@InjectView(R.id.com_root_view)
	public View rootView;

	@InjectView(R.id.vas_amt_text)
	public AmtEditTextView amtEditText;

	@InjectView(R.id.vas_pay_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CashPaymentControl.class)
	public Button payButton;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CashPaymentControl.class)
	public ImageView bacImageView;

	@InjectView(R.id.vas_top_title_tv)
	public TextView topTitle;

	public CommonDialog placeDialog;

	public SolfKeyBoardDialog solfKeyBoard;

	public CashPaymentContext cashPaymentContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashPaymentContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CashPaymentContext.class);
		cashPaymentContext.setReceiptNo(TxnUtil.getReceipNo(getAppConfig()));
	}

	@Override
	protected void onResumeProcess() {
		if (cashPaymentContext.isInputAmtFlag()) {
			topTitle.setText(StringConvertor
					.convert2Currency(cashPaymentContext.getShoppingCart()
							.getTotalAmt()));
			DisplayMetrics metric = new DisplayMetrics();
			Display display = getWindowManager().getDefaultDisplay();
			display.getMetrics(metric);
			solfKeyBoard = SolfKeyBoardDialog.instance(this, rootView,
					metric.heightPixels
							- Float.valueOf(330 * metric.density).intValue(),
					this);
			solfKeyBoard.getHintImgeBtn().setVisibility(View.GONE);
			solfKeyBoard.showKeyboard(amtEditText);
		} else {
			topTitle.setText(cashPaymentContext.getShoppingCart()
					.getItemsList().get(0).getProductName());
			amtEditText.setEnabled(false);
			amtEditText.setText(StringConvertor
					.convert2Currency(cashPaymentContext.getShoppingCart()
							.getTotalAmt()));
		}

	}

	public void sureClick() {
		cashPaySubmit();
	}

	/**
	 * 现金支付
	 */
	public void cashPaySubmit() {

		PurchaseOrderForm form = new PurchaseOrderForm();
		form.setPaymeneMethed(PaymentMethods.CASH);
		form.setShoppingCart(cashPaymentContext.getShoppingCart());
		form.setPurchaseStatus(PurchaseOrder.STATUS_PAID);
		form.setReceiptNo(cashPaymentContext.getReceiptNo());
		cashPaymentContext.setOrderTime(new Date());
		form.setOrderTime(cashPaymentContext.getOrderTime());
		EventRequest request = this.generateSubmitRequest(this);
		request.open(PurchaseOrderAction.DOMAIN_NAME,
				PurchaseOrderAction.PLACEORDER, Pattern.async);
		this.placeDialog = new CommonDialog(this, "处理中...");
		this.placeDialog.show();
		request.callBack(new CashPaymentCallbackImpl(this));
		request.getSubmitData().put("PurchaseOrderForm", form);
		request.submit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			TiFlowControlImpl.instanceControl().previousSetup(this);
			return true;
		}

		return true;

	}
}
