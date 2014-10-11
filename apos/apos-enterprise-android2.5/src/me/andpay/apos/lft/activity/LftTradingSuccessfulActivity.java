package me.andpay.apos.lft.activity;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 交易成功界面
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.lft_trading_successful)
public class LftTradingSuccessfulActivity extends AposBaseActivity {
	/*显示标题*/
	@InjectView(R.id.lft_trading_successful_showtxt)
	private TextView showTxt;
	
	/*返回*/
	@InjectView(R.id.lft_trading_successful_back)
	private ImageView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Map<String,Serializable> context = TiFlowControlImpl.instanceControl().getFlowContext();
		String type = (String)context.get("txnType");
		
		CommonTermTxnResponse response = (CommonTermTxnResponse)context.get(CommonTermTxnResponse.class.getName());
		
		if(type.equals(VasTxnTypes.MOBILE_RECHARGE)){//手机充值
			
			showTxt.setText("手机充值");
		}
		
		
	}
     
}
