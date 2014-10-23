package me.andpay.apos.merchantservice.activity;

import com.google.inject.Inject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 商户信息
 * 
 * @author Administrator
 *
 */

@ContentView(R.layout.merchants_information)
public class MerchantsInformationActivity extends AposBaseActivity implements FinishRequestInterface {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "base", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_base)
	private TextView base;

	@EventDelegate(type = DelegateType.method, toMethod = "settlement", delegateClass = OnClickListener.class)
	@InjectView(R.id.merchants_information_settlement)
	private TextView settlement;

	@InjectView(R.id.merchants_information_content)
	private LinearLayout content;

	private View baseView;
	private View settlementView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseView = ShowUtil.LoadXmlView(this, R.layout.ms_base_information);
		settlementView = ShowUtil.LoadXmlView(this,
				R.layout.ms_settlement_information);
		base(null);
	}

	@SuppressLint("NewApi")
	public void base(View view) {
		base.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		base.setTextColor(getResources().getColor(android.R.color.white));
		settlement.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		settlement.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		content.removeAllViews();
		content.addView(baseView);

	}

	@SuppressLint("NewApi")
	public void settlement(View view) {
		settlement.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		settlement.setTextColor(getResources().getColor(android.R.color.white));
		base.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		base.setTextColor(getResources().getColor(android.R.color.darker_gray));

		content.removeAllViews();
		content.addView(settlementView);

	}
	/**
	 * 获得商户基本信息
	 */
	@Inject RequestManager requestManager;
	private CommonDialog txnDialog=new CommonDialog(this,"获取中...");
	public void getBaseInformation(){
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		optRequest.setUserName("13838380439");
		optRequest.setOperateType(VasOptTypes.OSS_MERCHANT_BASE_INFO_QUERY);
		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);
		txnDialog.show();
		requestManager.startService();
	}

	public void back(View view){
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if(txnDialog!=null&&txnDialog.isShowing()){
			txnDialog.cancel();
		}
		if(response==null){
			ShowUtil.showShortToast(this,"获取失败");
		}else{
			CommonTermOptResponse optResponse = (CommonTermOptResponse)response;
			if(!optResponse.isSuccess()){
				ShowUtil.showShortToast(this,"数据为空");
			}else{
				String jsonStr = (String)optResponse.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
			}
		}
		
	}

}
