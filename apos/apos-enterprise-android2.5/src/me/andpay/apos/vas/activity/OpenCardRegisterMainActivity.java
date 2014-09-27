package me.andpay.apos.vas.activity;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CardNoEditText;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.apos.cmview.SolfKeyBoardDialog.OnClickHideButtonListener;
import me.andpay.apos.cmview.SolfKeyBoardDialog.OnKeyboardListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.event.CardNoTextEventController;
import me.andpay.apos.vas.event.OpenCardRegisterMainControl;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.apos.vas.helper.PdFulfillCtxConvertCenter;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.inject.Inject;

@ContentView(R.layout.vas_open_card_register_main_layout)
public class OpenCardRegisterMainActivity extends AposBaseActivity implements
		OnKeyboardListener, OnClickHideButtonListener {

	public OpenCardContext openCardContext;

	@InjectView(R.id.vas_cardNo_text)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = CardNoTextEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CardNoTextEventController.class) })
	public CardNoEditText startBlankPartCardNo;
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = CardNoTextEventController.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = CardNoTextEventController.class) })
	@InjectView(R.id.vas_recardNo_text)
	public CardNoEditText endBlankPartCardNo;

	@InjectView(R.id.vas_submit_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenCardRegisterMainControl.class)
	public Button submitBtn;

	public SolfKeyBoardDialog solfKeyBoardDialog;

	@InjectView(R.id.vas_content_scroll)
	public ScrollView scrollView;

	@InjectView(R.id.vas_opencard_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	public CommonDialog diCommonDialog;

	@Inject
	private PurchaseOrderInfoDao purchaseOrderInfoDao;
	@Inject
	public OpenCardRegisterMainControl oprCardRegisterMainControl;

	public CommonDialog loginDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics metric = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metric);

		solfKeyBoardDialog = SolfKeyBoardDialog.instance(this,
				endBlankPartCardNo.getRootView(),
				Float.valueOf((300 * metric.density)).intValue(), this);
		solfKeyBoardDialog.setOnClickHideButtonListener(this);

		openCardContext = initContext();

		this.setFlowContextData(openCardContext);
		backBtn.setVisibility(openCardContext.isBack() ? View.VISIBLE
				: View.GONE);
		String productType = openCardContext.getProductType();
		if (ShopProductTypes.E_SVC.equals(productType)) {
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.SUCCESS);
			return;
		}
	}

	@Override
	protected void onResumeProcess() {
		solfKeyBoardDialog.hideKeyboard();
		resizeView();
		if (openCardContext.getCardQuantity() == 1) {
			startBlankPartCardNo.setHint(R.string.vas_py_cardNo_must_str);
			endBlankPartCardNo.setHint(R.string.vas_py_recardNo_must_str);
		} else {
			// 开通多张卡
			startBlankPartCardNo.setHint(R.string.vas_py_first_cardNo_str);
			endBlankPartCardNo.setHint(R.string.vas_py_last_recardNo_str);
		}
		loginDialog = new CommonDialog(this,
				me.andpay.apos.common.util.ResourceUtil.getString(this,
						R.string.vas_user_register_str));

	}

	public void sureClick() {
		oprCardRegisterMainControl.submit(this);
	}

	public void onKeyboardHide() {
		resizeView();
	}

	public void resizeView() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		scrollView.layout(0, 0, metric.widthPixels, metric.heightPixels);
	}

	private OpenCardContext initContext() {

		OpenCardContext openCardContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(OpenCardContext.class);
		if (openCardContext != null) {
			return openCardContext;
		}
		boolean showBackFlag = Boolean.parseBoolean(getIntent().getStringExtra(
				VasProvider.VAS_INTENT_SHOWBACK_FLAG_KEY));
		this.getFlowContext().put(VasProvider.VAS_INTENT_SHOWBACK_FLAG_KEY,
				showBackFlag);
		String pid = this.getIntent().getExtras()
				.getString(VasProvider.VAS_INTENT_PURCHASE_INFO_ID_KEY);
		QueryPurchaseOrderInfoCond cond = new QueryPurchaseOrderInfoCond();
		cond.setOrderId(Long.valueOf(pid));
		openCardContext = (OpenCardContext) PdFulfillCtxConvertCenter
				.convert(purchaseOrderInfoDao.query(cond, 0, 1).get(0));
		openCardContext.setBack(showBackFlag);
		return openCardContext;
	}

}
