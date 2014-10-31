package me.andpay.apos.lft.activity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.tam.callback.impl.CreditPaymentCallBackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.ti.util.AttachmentItem;
import me.andpay.timobileframework.flow.TiFlowSubFinishAware;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 信用卡确认界面
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_credit_card_confirm)
public class CreditCardConfirmActivity extends AposBaseActivity implements
		TiFlowSubFinishAware {

	@InjectExtra("money")
	private String moneyStr;// 金钱

	@InjectExtra("poundage")
	private String poundageStr;// 手续费

	@InjectExtra("bankNumber")
	private String bankNumberStr;// 卡号

	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_credit_confirm_back)
	private ImageView back;

	@InjectView(R.id.lft_credit_confirm_money)
	private TextView moneyView;

	@InjectView(R.id.lft_credit_confirm_poundage)
	private TextView poundageView;

	@InjectView(R.id.lft_credit_confirm_bank_number)
	private TextView bankNumber;

	@EventDelegate(type = DelegateType.method, toMethod = "cardPayTxn", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_credit_confirm_sure)
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		moneyView.setText(Float.valueOf(poundageView.getText().toString())
				+ Float.valueOf(moneyStr) + "");
		poundageView.setText("手续费:" + poundageView.getText().toString() + "元");
		bankNumber.setText(bankNumberStr);

	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 信用卡还款
	 * 
	 * @param v
	 */
	@Inject
	TxnControl txnControl;
	@Inject
	CreditPaymentCallBackImpl creditPaymentCallBackImpl;

	public void cardPayTxn(View v) {
		TxnContext txnContext = txnControl.init();

		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnType.MPOS_PAY_CREDIT_CARD);
		txnContext.setBackTagName(TabNames.LEFT_PAGE);
		txnControl.setTxnCallback(creditPaymentCallBackImpl);
		String amountStr = "￥" + moneyView.getText().toString();

		txnContext.setAmtFomat(StringConvertor.filterEmptyString(amountStr));
		txnContext.setPromptStr("还款中...");
		setFlowContextData(txnContext);

		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.LFT_CARD_PAY_TXN);
	}

	@Inject
	WaitUploadImageDao waitUploadImageDao;

	public void subFlowFinished(Map<String, Serializable> subFlowContext) {
		// TODO Auto-generated method stub
		CommonTermTxnResponse cm = (CommonTermTxnResponse) subFlowContext
				.get(CommonTermTxnResponse.class.getName());

		if (cm.isSuccess()) {// 交易成功

			TiFlowControlImpl
					.instanceControl()
					.getFlowContext()
					.put(TxnContext.class.getName(), txnControl.getTxnContext());
			TiFlowControlImpl.instanceControl().getFlowContext()
					.put(CommonTermTxnResponse.class.getName(), cm);
			for (AttachmentItem item : cm.getAttachmentItems()) {
				WaitUploadImage waitImg = new WaitUploadImage();
				waitImg.setCreateDate(TimeUtil.getInstance().formatDate(
						new Date(), TimeUtil.DATE_PATTERN_11));
				String itemType = item.getAttachmentType();
				waitImg.setItemType(itemType);
				waitImg.setTermTraceNo(cm.getTermTraceNo());
				waitImg.setTermTxnTime(TimeUtil.getInstance().formatDate(
						cm.getTermTxnTime(), TimeUtil.DATE_PATTERN_11));
				waitImg.setItemId(item.getIdUnderType());
				waitImg.setTimes(0);
				waitImg.setReadyUpload(false);
				waitUploadImageDao.insert(waitImg);
			}
			TiFlowControlImpl.instanceControl().nextSetup(this, "success");

		} else {// 交易失败

			Map<String, String> dateMap = new HashMap();
			dateMap.put("txnType", TxnType.MPOS_PAY_CREDIT_CARD);
			dateMap.put("isSuccess", "false");
			TiFlowControlImpl.instanceControl().nextSetup(
					txnControl.getCurrActivity(), "result", dateMap);
		}
	}

}
