package me.andpay.apos.trm.activity;

import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.NewSolfKeyBoardView;
import me.andpay.apos.cmview.NewSolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.tam.activity.TiDynamicFieldDialog.DynamicDialogDelegate;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.trm.event.AmtEventControl;
import me.andpay.apos.trm.event.NoCardBtnClickController;
import me.andpay.apos.trm.event.RefundAmtEventControl;
import me.andpay.apos.trm.event.TrmExtInfoClickController;
import me.andpay.apos.trm.flow.model.RefundInputContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.trm_refund_input_first_layout)
public class RefundInputActivity extends AposBaseActivity implements
		ValueContainer, OnKeyboardListener, DynamicDialogDelegate {

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = TrmExtInfoClickController.class)
	@InjectView(R.id.trm_memo_tv)
	public TextView extInfoTv;

	public String extTraceNo;

	public String memo;

	@InjectView(R.id.trm_foot_lay)
	public RelativeLayout rootView;

	public NewSolfKeyBoardView solfKeyBoard;

	@InjectView(R.id.trm_amt_txnview)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = RefundAmtEventControl.class) })
	private AmtEditTextView amtEditText;

	@InjectResource(R.string.trm_refund_succ_str)
	private String refundSuccStr;

	@InjectResource(R.string.trm_refund_failed_str)
	private String refundFailedStr;

	@Inject
	public TxnControl txnControl;

	@Inject
	public DynamicFieldHelper dyHelper;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@Inject
	NoCardBtnClickController refundEventController;

	public RefundInputContext refundInputContext;
	
	@Inject
	private LocationService locationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationService.requestLocation();


	}

	@Override
	protected void onResumeProcess() {

		refundInputContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(RefundInputContext.class);

		solfKeyBoard = NewSolfKeyBoardView.instance(this, rootView, this);
		solfKeyBoard.setWithCalculator(false);
		solfKeyBoard.showKeyboard(amtEditText);
		
		amtEditText.setText(refundInputContext.getRefundAmt().toString());
		amtEditText.requestFocus();
		this.extTraceNo = refundInputContext.getExTraceNO();
		this.memo = refundInputContext.getMemo();

	
		
		// 判断是否有动态字段
		if (dyHelper.getFieldDefine(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO,
				TxnTypes.REFUND) == null
				&& dyHelper.getFieldDefine(FlexFieldDefine.FIELD_NAME_MEMO,
						TxnTypes.REFUND) == null) {
			this.extInfoTv.setVisibility(View.GONE);
		} else {
			setExtInfoText();
		}
		solfKeyBoard.changeSureButton(true, "退款");
	}

	public void sureButtonClick(Map<String, String> values) {
		this.extTraceNo = values.get(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO);
		this.memo = values.get(FlexFieldDefine.FIELD_NAME_MEMO);
		this.setExtInfoText();
	}

	protected void setExtInfoText() {
		
		String desc = StringUtil.defaultString(memo, "") + " "
				+ StringUtil.defaultString(extTraceNo, "");
		if (desc.startsWith(" ") || desc.endsWith(" ")) {
			desc = desc.replace(" ", "");
		}
		if (StringUtil.isEmpty(desc)) {
			this.extInfoTv.setText(getResources().getString(
					R.string.tam_pur_ext_info_str));
		} else {
			float textWidth = extInfoTv.getPaint().measureText("中");
			DisplayMetrics metric = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay()
					.getMetrics(metric);

		
			int with = 	Float.valueOf((100 * metric.density)).intValue();
			float textCount = with / textWidth;
			if (desc.length() > textCount) {
				this.extInfoTv.setText(desc.substring(0, ((int) textCount) - 1)
						+ "...");
			} else {
				this.extInfoTv.setText(desc);
			}
		}
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationService.requestLocation();
	}
	
	public void sureClick() {
		this.refundEventController.onClick(this, null, null);
	}

	public AmtEditTextView getAmt() {
		return amtEditText;
	}

	public String getRefundSuccStr() {
		return refundSuccStr;
	}

	public String getRefundFailedStr() {
		return refundFailedStr;
	}

	public AmtEditTextView getAmtEditText() {
		return amtEditText;
	}

	public void showCalculator() {
		// TODO Auto-generated method stub
		
	}
}
