package me.andpay.apos.vas.activity;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.action.PurchaseOrderAction;
import me.andpay.apos.vas.callback.PurchaseOrderFaildRetryCallback;
import me.andpay.apos.vas.event.PurchaseOrderFaildControl;
import me.andpay.apos.vas.flow.model.PurchaseOrderFaildContext;
import me.andpay.apos.vas.form.PurchaseOrderForm;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.tam_txn_faild_layout)
public class PurchaseOrderFaildActivity extends AposBaseActivity {

	@InjectView(R.id.com_title_tv)
	public TextView topTitle;

	@InjectView(R.id.com_out_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = PurchaseOrderFaildControl.class)
	public Button outButton;

	@InjectView(R.id.com_msg_content)
	public TextView msgContent;

	@InjectView(R.id.com_event_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = PurchaseOrderFaildControl.class)
	public Button retryBtn;
	//
	private String errorMsg;

	// private String buttonName;

	public CommonDialog postDialog;

	private PurchaseOrderFaildContext orderFaildContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		retryBtn.setText("重新提交订单");
		topTitle.setText("网络异常");
		outButton.setText("退出交易");
		orderFaildContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(PurchaseOrderFaildContext.class);
		outButton
				.setVisibility(orderFaildContext.isShowOutButton() ? View.VISIBLE
						: View.GONE);
		msgContent.setText(orderFaildContext.getMsgContent());

	}

	public void sumbitPurchaseOrder() {
		PurchaseOrderForm form = new PurchaseOrderForm();
		form.setPaymeneMethed(orderFaildContext.getPaymentMethod());
		form.setShoppingCart(orderFaildContext.getShoppingCart());
		form.setPurchaseStatus(PurchaseOrder.STATUS_PAID);
		form.setReceiptNo(orderFaildContext.getReceiptNo());
		form.setPaymentTxnId(orderFaildContext.getTxnId());
		form.setOrderTime(orderFaildContext.getOrderTime());
		EventRequest request = this.generateSubmitRequest(this);
		request.open(PurchaseOrderAction.DOMAIN_NAME,
				PurchaseOrderAction.PLACEORDER, Pattern.async);
		postDialog = new CommonDialog(this, "处理中...");
		postDialog.show();
		request.callBack(new PurchaseOrderFaildRetryCallback(this));
		request.getSubmitData().put("PurchaseOrderForm", form);
		request.submit();
	}

}
