package me.andpay.apos.lft.activity;

import java.util.Map;

import me.andpay.ac.term.api.vas.txn.VasTxnPropNames;
import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.lft.data.PhoneNumber;
import me.andpay.apos.lft.even.TopupTextWatcherEventControl;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.tam.callback.impl.TopUpCallBackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.TiFlowCallback;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.inject.Inject;

/**
 * 生活手机充值
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_top_up)
public class TopupActivity extends AposBaseActivity implements OnClickListener,
		TiFlowCallback {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_top_up_back)
	private ImageView back;// 返回

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = TopupTextWatcherEventControl.class)
	@InjectView(R.id.lft_top_up_phonenumber)
	public EditText phoneNumber;// 手机号码

	@EventDelegate(type = DelegateType.method, toMethod = "phoneNumberSelect", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_top_up_phonenumber_select)
	private ImageView phoneNumberSelect;// 选取联系人

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = TopupTextWatcherEventControl.class)
	@InjectView(R.id.lft_top_up_amount)
	public EditText amount;// 金额数

	@EventDelegate(type = DelegateType.method, toMethod = "amountSelect", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_top_up_amount_select)
	private View amountSelect;// 金额数选择

	@EventDelegate(type = DelegateType.method, toMethod = "sure", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_top_up_sure)
	public Button sure;

	/**
	 * 返回上一级
	 * 
	 * @param v
	 */

	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 手机号码选择
	 * 
	 * @param v
	 */
	public void phoneNumberSelect(View v) {
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.ADDRESS_BOOK);
	}

	/**
	 * 充值确定
	 */
	@Inject
	TxnControl txnControl;

	public void sure(View v) {
	
		TxnContext txnContext = txnControl.init();

		
        Map<String,String> map = new HashMap();
        LoginUserInfo user = (LoginUserInfo)getAppContext().getAttribute(RuntimeAttrNames.LOGIN_USER);
        map.put(VasTxnPropNames.USER_NAME,user.getUserName());
        map.put(VasTxnPropNames.MOBILE_PHONE, phoneNumber.getText().toString());
        
        txnContext.setMap(map);
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnType.MPOS_TOPUP);
		txnContext.setBackTagName(TabNames.LEFT_PAGE);
		txnControl.setTxnCallback(new TopUpCallBackImpl());
		String amountStr = "￥"+amount.getText().toString();
		//amountStr = "￥"+amountStr.substring(0,amountStr.length()-1);
		txnContext.setAmtFomat(StringConvertor.filterEmptyString(amountStr));
		txnContext.setPromptStr("充值中...");
		setFlowContextData(txnContext);
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.TOPUP_TXN);
	}

	/**
	 * 充值金额选择
	 * 
	 * @param v
	 */
	private CustomDialog amoutDialog = null;

	public void amountSelect(View v) {
		amoutDialog = ShowUtil.getCustomDialog(this,
				R.layout.lft_top_up_amount_select_dialog, 0);
		amoutDialog.getcView().findViewById(R.id.lft_top_up_30)
				.setOnClickListener(this);
		amoutDialog.getcView().findViewById(R.id.lft_top_up_50)
				.setOnClickListener(this);
		amoutDialog.getcView().findViewById(R.id.lft_top_up_100)
				.setOnClickListener(this);
		amoutDialog.getcView().findViewById(R.id.lft_top_up_300)
				.setOnClickListener(this);
		amoutDialog.getcView().findViewById(R.id.lft_top_up_500)
				.setOnClickListener(this);

		amoutDialog.show();

	}

	/**
	 * 关闭
	 */
	public void closeAmountSelect() {
		if (amoutDialog != null) {
			amoutDialog.dismiss();
		}
	}

	public void callback(String sourceNodeName) {
		// TODO Auto-generated method stub
		PhoneNumber phoneNumberStr = (PhoneNumber) TiFlowControlImpl
				.instanceControl().getFlowContext()
				.get(PhoneNumber.class.getName());
		if (phoneNumberStr != null) {
			phoneNumber.setText(phoneNumberStr.getDisplayNumber());
		}
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.lft_top_up_30:
			closeAmountSelect();
			amount.setText("30");
			break;

		case R.id.lft_top_up_50:
			closeAmountSelect();
			amount.setText("50");
			break;
		case R.id.lft_top_up_100:
			closeAmountSelect();
			amount.setText("100");
			break;
		case R.id.lft_top_up_300:
			closeAmountSelect();
			amount.setText("300");
			break;
		case R.id.lft_top_up_500:
			closeAmountSelect();
			amount.setText("500");
			break;
		}
	}
}
