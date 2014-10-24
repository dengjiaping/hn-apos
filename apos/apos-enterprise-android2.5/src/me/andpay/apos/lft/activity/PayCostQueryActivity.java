package me.andpay.apos.lft.activity;

import java.util.Map;

import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.ac.term.api.vas.txn.VasTxnPropNames;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.data.PayInformation;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.lft.flow.PayCostType;
import me.andpay.apos.tam.callback.impl.PayCostTxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.cache.HashMap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 缴费查询
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_paycost_query)
public class PayCostQueryActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_lft_paycost_query_back)
	private ImageView back;

	@InjectView(R.id.lft_paycost_query_unit)
	private TextView unit;// 单位

	@InjectView(R.id.lft_paycost_query_id)
	private TextView serialNumber;// 编号

	@InjectView(R.id.lft_paycost_query_money)
	private TextView money;// 应缴金额

	@InjectView(R.id.lft_paycost_query_true_money)
	private EditText trueMoney;// 实缴金额

	@InjectView(R.id.lft_paycost_query_username)
	private TextView userName;// 用户姓名

	@EventDelegate(type = DelegateType.method, toMethod = "paySure", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_paycost_query_sure)
	private Button sure;// 确认缴费

	@EventDelegate(type = DelegateType.method, toMethod = "deatail", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_paycost_query_deatail)
	private TextView deatail;// 详情

	@InjectExtra("unit")
	private String unitStr;// 单位

	@InjectExtra("serialNumber")
	private String serialNumberStr;// 单号
	
	protected VasTxnService vasTxnService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		unit.setText(unitStr);
		serialNumber.setText(serialNumberStr);
		
		//getDeatail();
		
	}
	
	/*拉取详情*/
	private void getDeatail(){
		/*请求缴纳详细信息*/
		PayCostType type = (PayCostType) TiFlowControlImpl.instanceControl()
				.getFlowContext().get("type");
		CommonTermTxnRequest txnRequest = new CommonTermTxnRequest();
		Map<String,String> map = new HashMap();
		map.put(VasTxnPropNames.MERCHANT_TYPE, type == PayCostType.ELECTRICITY?"1800":"1866");
	    map.put(VasTxnPropNames.LIFEPAY_CUST_NO, serialNumberStr);
		txnRequest.setVasTxnType(VasTxnTypes.LIFE_PAY_QUERY);
		txnRequest.setTxnRequestContentObj(map);
		CommonTermTxnResponse response = vasTxnService.processCommonTxn(txnRequest);
		
		/*设置信息*/
		setInformation(response.getTxnResponseContentObj());
		
		
		
		
		
		
	}
	/*设置详细信息*/
	public void setInformation(Map map){
		PayInformation payInformation  = new PayInformation();
		payInformation.parse(map);
		serialNumber.setText(payInformation.getUnitCode());
		money.setText(payInformation.getTotalBills());
		userName.setText(payInformation.getUnitCode());
	}

	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public void deatail(View v) {
		PayCostType type = (PayCostType) TiFlowControlImpl.instanceControl()
				.getFlowContext().get("type");
		if (type == PayCostType.ELECTRICITY) {// 电费详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.ELE_DEATAIL);
		} else if (type == PayCostType.WATER) {// 水费详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.WATER_DEATAIL);
		}
	}

	/**
	 * 确认缴费
	 * 
	 * @param view
	 */
	@Inject
	TxnControl txnControl;
	
	@Inject 
	PayCostTxnCallbackImpl payCostTxnCallbackImpl;

	public void paySure(View view){
		
		if(StringUtil.isEmpty(trueMoney.getText().toString())){
			ShowUtil.showLongToast(this,"未输入缴费金额");
			return;
		}
		
		TxnContext txnContext = txnControl.init();

		txnContext.setNeedPin(true);
		PayCostType type = (PayCostType) TiFlowControlImpl.instanceControl()
				.getFlowContext().get("type");
		txnContext.setTxnType(type==PayCostType.ELECTRICITY?TxnType.MPOS_PAYCOST_ELE:TxnType.MPOS_PAYCOST_WATER);
		txnContext.setBackTagName(TabNames.LEFT_PAGE);
		txnControl.setTxnCallback(payCostTxnCallbackImpl);
		String amountStr = "￥" + trueMoney.getText().toString();
	
		txnContext.setAmtFomat(StringConvertor.filterEmptyString(amountStr));
		txnContext.setPromptStr(type==PayCostType.ELECTRICITY?"电费缴纳中...":"水费缴纳中...");
		setFlowContextData(txnContext);
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.PAY_COST_SURE);

	}
}
