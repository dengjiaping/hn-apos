package me.andpay.apos.lft.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;

import me.andpay.apos.lft.data.OftenUser;
import me.andpay.apos.lft.even.PayeeBankNumberTextWatcherEventControl;
import me.andpay.apos.lft.even.PayeeIfmTextWatcherEventControl;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.TiFlowCallback;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 收款人信息
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_transfer_accounts_payee_information)
public class PayeeInformationActivity extends AposBaseActivity implements
		TiFlowCallback {
	@InjectView(R.id.lft_transfer_accounts_payee_information_back)
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	private ImageView back;

	@InjectView(R.id.lft_transfer_accounts_payee_information_poundage)
	private TextView poundage;// 手续费

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayeeIfmTextWatcherEventControl.class)
	@InjectView(R.id.lft_transfer_accounts_payee_information_money)
	public EditText money;// 金额

	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayeeBankNumberTextWatcherEventControl.class),
			@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayeeIfmTextWatcherEventControl.class)

	})
	@InjectView(R.id.lft_transfer_accounts_payee_information_number)
	public EditText number;// 卡号

	@EventDelegate(type = DelegateType.method, toMethod = "sure", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_transfer_accounts_payee_information_sure)
	public Button sure;// 确定

	@EventDelegate(type = DelegateType.method, toMethod = "person", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_transfer_accounts_payee_information_person)
	private ImageView person;// 常用联系人

	@InjectView(R.id.lft_payee_information_number_title)
	public TextView cardNumberTitle;

	@Inject
	TxnContext txnContext;

	/*
	 * =====================================================事件触发==================
	 * ======================
	 */

	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 常用联系人
	 * 
	 * @param v
	 */
	public void person(View v) {

		TiFlowControlImpl.instanceControl()
				.nextSetup(this, FlowConstants.OFTEN);
	}

	public void callback(String sourceNodeName) {
		// TODO Auto-generated method stub
		OftenUser oftenUser = TiFlowControlImpl.instanceControl()
				.getFlowContextData(OftenUser.class);
		if (oftenUser != null) {
			number.setText(oftenUser.getBankCardNumber());
		}
	}

	/**
	 * 确定
	 * 
	 * @param v
	 */
	public void sure(View v) {
		Map<String, String> senData = new HashMap<String, String>();
		senData.put("money", money.getText().toString());
		senData.put("poundage", poundage.getText().toString());
		senData.put("bankNumber", number.getText().toString());

		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.OFTEN_DEATAIL, senData);

	}
}
