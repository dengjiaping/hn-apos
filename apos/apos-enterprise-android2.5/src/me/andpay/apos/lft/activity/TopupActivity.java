package me.andpay.apos.lft.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
/**
 * 生活手机充值
 * @author Administrator
 *
 */
@ContentView(R.layout.lft_top_up)
public class TopupActivity extends AposBaseActivity{
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
    @InjectView(R.id.lft_top_up_back)
    private ImageView back;//返回
	
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
	@InjectView(R.id.lft_top_up_phonenumber)
	private EditText phoneNumber;//手机号码
	@EventDelegate(type=DelegateType.method,toMethod="phoneNumberSelect",delegateClass=OnClickListener.class)
	@InjectView(R.id.lft_top_up_phonenumber_select)
	private ImageView phoneNumberSelect;//选取联系人 
	
	@EventDelegate(type=DelegateType.method,toMethod="back",delegateClass=OnClickListener.class)
	@InjectView(R.id.lft_top_up_amount)
	private EditText amount;//金额数
	@EventDelegate(type=DelegateType.method,toMethod="amountSelect",delegateClass=OnClickListener.class)
	@InjectView(R.id.lft_top_up_amount_select)
	private ImageView amountSelect;//金额数选择
	
	
	
	/**
	 * 返回上一级
	 * @param v
	 */
	
	public void back(View v){
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}
	/**
	 * 手机号码选择
	 * @param v
	 */
	public void phoneNumberSelect(View v){
		
	}
	/**
	 * 充值金额选择
	 * @param v
	 */
	public void amountSelect(View v){
		
	}
}
