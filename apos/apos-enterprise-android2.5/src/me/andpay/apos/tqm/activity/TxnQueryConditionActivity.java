package me.andpay.apos.tqm.activity;

import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.CardNoEditText;
import me.andpay.apos.cmview.SolfKeyBoardView;
import me.andpay.apos.cmview.SolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.cmview.TiDatePickerView;
import me.andpay.apos.cmview.TiSpinner;
import me.andpay.apos.cmview.TiSpinner.TiSpinnerInit;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.event.AmtEventControl;
import me.andpay.apos.tqm.event.CardNoEventController;
import me.andpay.apos.tqm.event.ClearConditionController;
import me.andpay.apos.tqm.event.TxnQueryConditionCancelClickController;
import me.andpay.apos.tqm.event.TxnQueryConditionSureClickController;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@ContentView(R.layout.tqm_txn_list_condition_layout)
@FormBind(formBean = QueryConditionForm.class)
public class TxnQueryConditionActivity extends AposBaseActivity implements ValueContainer,
		OnKeyboardListener {

	private SolfKeyBoardView solfKeyBoard;

	@InjectView(R.id.com_softkeyborad_layout)
	private RelativeLayout footLayout;

	@InjectView(R.id.tqm_txn_list_txntype_layout)
	private RelativeLayout txnTypeLayout;

	@InjectView(R.id.tqm_txn_list_condition_sure_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = TxnQueryConditionSureClickController.class, isNeedFormBean = true)
	Button sureButton;

	@InjectView(R.id.tqm_txn_list_condition_cancel_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = TxnQueryConditionCancelClickController.class)
	Button cancelButton;

	@InjectView(R.id.tqm_txn_list_condition_amount_ev)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtEventControl.class) })
	public AmtEditTextView amtEditText;

	@InjectView(R.id.tqm_txn_list_condition_cardno_tv)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = CardNoEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CardNoEventController.class) })
	private CardNoEditText cardNoEv;

	@InjectView(R.id.tqm_txn_list_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	@InjectView(R.id.tqm_txn_list_condition_date_begin_ev)
	public TiDatePickerView startDateView;

	@InjectView(R.id.tqm_txn_list_condition_date_end_ev)
	public TiDatePickerView endPickerView;

	@InjectView(R.id.tqm_txn_list_condition_cardno_tv)
	public CardNoEditText cardEdit;

	@InjectView(R.id.tqm_txn_list_condition_refno_ev)
	public EditText txnIdEdit;
	@InjectView(R.id.tqm_txn_list_condition_txntype_sp)
	public TiSpinner txnTypeSpinner;

	@InjectView(R.id.tqm_txn_list_condition_orderid_ev)
	public EditText orderditText;

	@InjectView(R.id.tqm_clear_cond_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ClearConditionController.class)
	public Button clearBtn;

	@InjectView(R.id.tqm_hascondition_image)
	public ImageView hasCondImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTxnTypeSpinner();
		boolean isTxnTypeHide = this.getIntent().getBooleanExtra(
				TqmProvider.TQM_CONST_HIDE_TXNTYPE, false);
		// 判断是否需要隐藏交易类型选项
		if (isTxnTypeHide) {
			txnTypeLayout.setVisibility(View.GONE);
		}
		solfKeyBoard = SolfKeyBoardView.instance(getApplicationContext(), footLayout,
				this);
		if (getIntent() == null || getIntent().getExtras() == null) {
			return;
		}
		byte[] conditionBytes = getIntent().getExtras().getByteArray(
				TqmProvider.TQM_QUERY_COND_FORM);
		if (conditionBytes != null && conditionBytes.length > 0) {
			QueryConditionForm form = JacksonSerializer.newPrettySerializer()
					.deserialize(QueryConditionForm.class, conditionBytes);

			if (form.isHasViewCond()) {
				hasCondImg.setVisibility(View.VISIBLE);
			} else {
				hasCondImg.setVisibility(View.GONE);
			}

			startDateView.setText(form.getBeginDate());
			endPickerView.setText(form.getEndDate());
			if (StringUtil.isNotBlank(form.getAmount())) {
				amtEditText.setText(form.getAmount());
			} else {
				amtEditText.setText("");
			}
			cardEdit.setText(form.getCardno());
			orderditText.setText(form.getOrderno());
			txnIdEdit.setText(form.getTxnId());
			// txnTypeSpinner.setSelection(index)
			if (StringUtil.isNotBlank(form.getTxnType())) {
				Map<Integer, String> itemValues = txnTypeSpinner.getItemValues();
				for (Integer valueKey : itemValues.keySet()) {
					if (form.getTxnType().equals(itemValues.get(valueKey))) {
						txnTypeSpinner.showSelect(valueKey);
					}
				}
			}

		}

	}

	private void initTxnTypeSpinner() {
		txnTypeSpinner.setLayoutAndInit(R.layout.com_types_spinner_layout, new TiSpinnerInit() {
			
			public void initData(Map<Integer, Integer> itemIds, Map<Integer, Integer> itemImgs,
					Map<Integer, String> itemValues, Map<Integer, Integer> itemStrs) {

				itemIds.put(R.id.com_txntypes_all_layout, R.id.com_txntypes_all_str);
				itemIds.put(R.id.com_txntypes_purchase_layout, R.id.com_txntypes_purchase_str);
				itemIds.put(R.id.com_txntypes_refund_layout, R.id.com_txntypes_refund_str);
				itemIds.put(R.id.com_txntypes_void_layout, R.id.com_txntypes_void_str);
				itemImgs.put(R.id.com_txntypes_all_layout, R.id.com_txntypes_all_img);
				itemImgs.put(R.id.com_txntypes_purchase_layout, R.id.com_txntypes_purchase_img);
				itemImgs.put(R.id.com_txntypes_refund_layout, R.id.com_txntypes_refund_img);
				itemImgs.put(R.id.com_txntypes_void_layout, R.id.com_txntypes_void_img);
				itemValues.put(R.id.com_txntypes_all_layout, null);
				itemValues.put(R.id.com_txntypes_purchase_layout, TxnTypes.PURCHASE);
				itemValues.put(R.id.com_txntypes_refund_layout, TxnTypes.REFUND);
				itemValues.put(R.id.com_txntypes_void_layout, TxnTypes.VOID_PURCHASE);
				itemStrs.put(R.id.com_txntypes_all_layout, R.string.tqm_txn_types_all_str);
				itemStrs.put(R.id.com_txntypes_purchase_layout,
						R.string.tqm_txn_types_purchase_str);
				itemStrs.put(R.id.com_txntypes_refund_layout, R.string.tqm_txn_types_refund_str);
				itemStrs.put(R.id.com_txntypes_void_layout, R.string.tqm_txn_types_void_str);
				
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

	public CardNoEditText getCardNoEv() {
		return cardNoEv;
	}

}
