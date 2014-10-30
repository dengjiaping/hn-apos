package me.andpay.apos.lft.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.base.tools.MathUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.even.CardPayTextWatcherEventControl;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 信用卡还款
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_credit_card_payment)
public class CreditCardPaymentsActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_credit_card_payment_back)
	private ImageView back;

	@InjectView(R.id.lft_credit_card_payment_poundage)
	public TextView poundage;// 手续费

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CardPayTextWatcherEventControl.class)
	@InjectView(R.id.lft_credit_card_payment_money)
	public EditText money;// 付款金额

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = CardPayTextWatcherEventControl.class)
	@InjectView(R.id.lft_credit_card_payment_number)
	public EditText number;// 信用卡卡号

	@InjectView(R.id.lft_credit_card_payment_person)
	private ImageView person;// 常用信用卡

	@EventDelegate(type = DelegateType.method, toMethod = "sure", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_credit_card_payment_sure)
	public Button sure;// 确认信用卡付款

	/************************************************ 返回 ***********************************/
	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public void sure(View v) {
		if(!MathUtil.isfloatNumber(money.getText().toString())){
			ShowUtil.showShortToast(this,"请输入合法的金额数");
			return;
		}
		if(Float.valueOf(money.getText().toString())==0){
			ShowUtil.showShortToast(this,"金额数不能为0");
			return;
		}
		Map<String, String> senData = new HashMap<String, String>();
		senData.put("money", money.getText().toString());
		senData.put("poundage", poundage.getText().toString());
		senData.put("bankNumber", number.getText().toString());

		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.CREDIT_CARD_CONFIRM, senData);

	}

}
