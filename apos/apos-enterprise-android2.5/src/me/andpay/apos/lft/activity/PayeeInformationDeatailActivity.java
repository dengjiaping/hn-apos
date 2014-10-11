package me.andpay.apos.lft.activity;

import com.google.inject.Inject;

import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.tam.callback.impl.TopUpCallBackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
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

/**
 * 收款人详情界面
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_transfer_accounts_payee_information_deatail)
public class PayeeInformationDeatailActivity extends AposBaseActivity {
	@InjectExtra("money")
	private String moneyStr;// 金钱

	@InjectExtra("poundage")
	private String poundageStr;// 手续费

	@InjectExtra("bankNumber")
	private String bankNumberStr;// 卡号

	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_transfer_accounts_payee_information_deatail_back)
	private ImageView back;

	@InjectView(R.id.lft_transfer_accounts_payee_information_deatail_money)
	private TextView moneyView;

	@InjectView(R.id.lft_transfer_accounts_payee_information_deatail_poundage)
	private TextView poundageView;

	@InjectView(R.id.lft_transfer_accounts_payee_information_deatail_bank_number)
	private TextView bankNumber;

	@EventDelegate(type = DelegateType.method, toMethod = "tranfTxn", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_transfer_accounts_payee_information_deatail_sure)
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		moneyView.setText(moneyStr);
		poundageView.setText(poundageStr);
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
	 * 刷卡转账
	 * 
	 * @param v
	 */
	@Inject
	TxnControl txnControl;

	public void tranfTxn(View v) {
		TxnContext txnContext = txnControl.init();

		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnType.MPOS_TRANSFER_ACCOUNT);
		txnContext.setBackTagName(TabNames.LEFT_PAGE);
		txnControl.setTxnCallback(new TopUpCallBackImpl());
		String amountStr = "￥" + moneyStr;
		// amountStr = "￥"+amountStr.substring(0,amountStr.length()-1);
		txnContext.setAmtFomat(StringConvertor.filterEmptyString(amountStr));
		txnContext.setPromptStr("转账中...");
		setFlowContextData(txnContext);

		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.LFT_TRANSFER_TXN);
	}

}
