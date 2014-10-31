package me.andpay.apos.tam.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tam.callback.impl.TxnPurchaseOrderCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.PostSkipVcEventControl;
import me.andpay.apos.tam.event.PostVcEditWatcherEventControl;
import me.andpay.apos.tam.event.PostVcEventControl;
import me.andpay.apos.tam.event.ShowRulesEventControl;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.tam.form.PostVoucherForm;
import me.andpay.apos.vas.action.PurchaseOrderAction;
import me.andpay.apos.vas.form.PurchaseOrderForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.util.DateUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.tam_send_credentials_layout)
@FormBind(formBean = PostVoucherForm.class)
public class PostVoucherActivity extends AposBaseActivity implements
		ValueContainer{

	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PostVcEditWatcherEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PostVcEditWatcherEventControl.class) })
	@InjectView(R.id.tam_credentials_phone_edit)
	public EditText phoneEditText;

	@InjectView(R.id.tam_credentials_skip_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = PostSkipVcEventControl.class)
	public Button skipBtn;
	public CommonDialog postDialog;

	@InjectView(R.id.tam_credentials_send_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = PostVcEventControl.class)
	public Button postSmsbtn;

	@InjectView(R.id.tam_credentials_scroll_view)
	public ScrollView mainScroll;

	@InjectView(R.id.tam_credentials_price_msg)
	public TextView priceMsgTextView;

	@InjectView(R.id.tam_credentials_date_msg)
	public TextView dateMsgTextView;

	@InjectView(R.id.tam_credentials_help_img)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = View.OnClickListener.class, toEventController = ShowRulesEventControl.class)
	public ImageView helpImageView;

	@Inject
	public TxnControl txnControl;
	/**
	 * 重发凭证标志
	 */
	public PostVoucherContext postVoucherContext;

	public String realPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		postVoucherContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(PostVoucherContext.class);

		this.refreshPriceMsgTextView(postVoucherContext);
		this.refreshDateMsgTextView(postVoucherContext);

		Map<String, String> contactInfos = postVoucherContext.getContactInfos();

		if (contactInfos != null && contactInfos.size() > 0) {
			String phone = contactInfos.get("0");
			if (phone != null) {
				realPhone = phone;
				phone = "*******" + phone.substring(7);
			}
			phoneEditText.setText(phone);
		} else {
		}

	}

	private void refreshPriceMsgTextView(PostVoucherContext postVoucherContext) {
		StringBuffer buffer = new StringBuffer();
		String formatedAmt = postVoucherContext.getFormatedAmt();
		String priceMas = priceMsgTextView.getText().toString();
		if (StringUtil.isNotBlank(formatedAmt)) {
			buffer.append(formatedAmt);
			buffer.append(" ");
		}
		buffer.append(priceMas);
		Paint paint = priceMsgTextView.getPaint();
		paint.setFakeBoldText(true);
		priceMsgTextView.setText(buffer.toString());
	}

	private void refreshDateMsgTextView(PostVoucherContext postVoucherContext) {
		StringBuffer buffer = new StringBuffer();
		String dateMag = dateMsgTextView.getText().toString();
		buffer.append(dateMag);
		Date settlementTime = postVoucherContext.getSettlementTime();
		if (settlementTime != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = dateFormat.format(settlementTime);
			String dayOfWeekInChinese = DateUtil
					.getWeekChineseDesc(settlementTime);
			buffer.append(" ");
			buffer.append(dateStr);
			buffer.append(" ");
			buffer.append(dayOfWeekInChinese);
		}
		dateMsgTextView.setText(buffer.toString());
	}

	@Override
	protected void onResumeProcess() {
		// if (StringUtil.isNotBlank(postVoucherContext)) {
		// dateMsgTextView.setVisibility(View.VISIBLE);
		// dateMsgTextView.setText(postVoucherContext.getMessage());
		// } else {
		// dateMsgTextView.setVisibility(View.GONE);
		// }
	}

	public void sumbitPurchaseOrder() {
		PurchaseOrderForm form = new PurchaseOrderForm();
		form.setPaymeneMethed(PaymentMethods.SWIPING);
		form.setShoppingCart(postVoucherContext.getShoppingCart());
		form.setPurchaseStatus(PurchaseOrder.STATUS_PAID);
		form.setReceiptNo(postVoucherContext.getReceiptNo());
		form.setPaymentTxnId(postVoucherContext.getTxnId());
		postVoucherContext.setOrderTime(new Date());
		form.setOrderTime(postVoucherContext.getOrderTime());
		EventRequest request = this.generateSubmitRequest(this);
		request.open(PurchaseOrderAction.DOMAIN_NAME,
				PurchaseOrderAction.PLACEORDER, Pattern.async);
		postDialog = new CommonDialog(this, "处理中...");
		postDialog.show();
		request.callBack(new TxnPurchaseOrderCallback(this, postDialog));
		request.getSubmitData().put("PurchaseOrderForm", form);
		request.submit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
