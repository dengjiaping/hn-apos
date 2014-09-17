package me.andpay.apos.vas.activity;

import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.SolfKeyBoardView;
import me.andpay.apos.cmview.SolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.cmview.TiDatePickerView;
import me.andpay.apos.cmview.TiSpinner;
import me.andpay.apos.cmview.TiSpinner.TiSpinnerInit;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.vas.event.AmtEventControl;
import me.andpay.apos.vas.event.PurQueryCleanController;
import me.andpay.apos.vas.event.PurSureBtnClickController;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@ContentView(R.layout.vas_purchaseorder_cond_layout)
public class PurchaseQueryCondActivity extends AposBaseActivity implements OnKeyboardListener {
	private SolfKeyBoardView solfKeyBoard;

	@InjectView(R.id.com_softkeyborad_layout)
	private RelativeLayout footLayout;

	@InjectView(R.id.vas_po_list_condition_paymethod_sp)
	public TiSpinner payMethodSp;
	// private RelativeLayout txnTypeLayout;

	@InjectView(R.id.vas_po_list_condition_sure_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PurSureBtnClickController.class, isNeedFormBean = false)
	Button sureButton;

	@InjectView(R.id.vas_po_list_condition_cancel_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	Button cancelButton;

	@InjectView(R.id.vas_po_list_condition_amount_ev)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtEventControl.class) })
	public AmtEditTextView amtEditText;

	@InjectView(R.id.vas_po_list_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@InjectView(R.id.vas_po_list_condition_date_begin_ev)
	public TiDatePickerView startDateView;

	@InjectView(R.id.vas_po_list_condition_date_end_ev)
	public TiDatePickerView endPickerView;

	// @InjectView(R.id.vas_po_list_condition_paytype_sp)
	// public TiSpinner txnTypeSpinner;

	@InjectView(R.id.vas_clear_cond_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PurQueryCleanController.class)
	public Button clearBtn;

	@InjectView(R.id.vas_hascondition_image)
	public ImageView hasCondImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initPaymethodSpinner();

		solfKeyBoard = SolfKeyBoardView.instance(getApplicationContext(), footLayout,
				this);
		QueryPurchaseOrderInfoCond form = getControl().getFlowContextData(
				QueryPurchaseOrderInfoCond.class);
		getControl().getFlowContext().remove(QueryPurchaseOrderInfoCond.class.getName());
		if (form != null) {

			if (form.isHasViewCond()) {
				hasCondImg.setVisibility(View.VISIBLE);
			} else {
				hasCondImg.setVisibility(View.GONE);
			}
			if (form.getMinOrderTime() != null)
				startDateView.setText(DateUtil.format("yyyy/MM/dd",
						form.getMinOrderTime()));
			if (form.getMaxOrderTime() != null)
				endPickerView.setText(DateUtil.format("yyyy/MM/dd",
						form.getMaxOrderTime()));
			if (form.getSalesAmt() != null) {
				amtEditText.setText(StringConvertor.format(form.getSalesAmt()));
			} else {
				amtEditText.setText("");
			}
			if (StringUtil.isNotBlank(form.getPaymentMethod())) {
				Map<Integer, String> itemValues = payMethodSp.getItemValues();
				for (Integer valueKey : itemValues.keySet()) {
					if (form.getPaymentMethod().equals(itemValues.get(valueKey))) {
						payMethodSp.showSelect(valueKey);
					}
				}
			}
		}

	}

	private void initPaymethodSpinner() {
		payMethodSp.setLayoutAndInit(R.layout.vas_paymethod_spinner_layout,
				new TiSpinnerInit() {

					public void initData(Map<Integer, Integer> itemIds,
							Map<Integer, Integer> itemImgs,
							Map<Integer, String> itemValues,
							Map<Integer, Integer> itemStrs) {
						itemIds.put(R.id.vas_paymethod_all_layout,
								R.id.vas_paymethod_all_str);
						itemIds.put(R.id.vas_paymethod_cash_layout,
								R.id.vas_paymethod_cash_str);
						itemIds.put(R.id.vas_paymethod_swipe_layout,
								R.id.vas_paymethod_swipe_str);

						itemImgs.put(R.id.vas_paymethod_all_layout,
								R.id.vas_paymethod_all_img);
						itemImgs.put(R.id.vas_paymethod_cash_layout,
								R.id.vas_paymethod_cash_img);
						itemImgs.put(R.id.vas_paymethod_swipe_layout,
								R.id.vas_paymethod_swipe_img);

						itemValues.put(R.id.vas_paymethod_all_layout, null);
						itemValues.put(R.id.vas_paymethod_cash_layout,
								PaymentMethods.CASH);
						itemValues.put(R.id.vas_paymethod_swipe_layout,
								PaymentMethods.SWIPING);

						itemStrs.put(R.id.vas_paymethod_all_layout,
								R.string.vas_po_pay_all_str);
						itemStrs.put(R.id.vas_paymethod_cash_layout,
								R.string.vas_po_pay_cash_str);
						itemStrs.put(R.id.vas_paymethod_swipe_layout,
								R.string.vas_po_pay_swiper_str);
					}
				});
	}

	public void sureClick() {
		solfKeyBoard.hideKeyboard();
	}

	public SolfKeyBoardView getSolfKeyBoard() {
		return solfKeyBoard;
	}

	public RelativeLayout getFootLayout() {
		return footLayout;
	}

	public AmtEditTextView getAmtEditText() {
		return amtEditText;
	}

	public TiDatePickerView getStartDateView() {
		return startDateView;
	}

	public TiDatePickerView getEndPickerView() {
		return endPickerView;
	}

}
