package me.andpay.apos.merchantservice.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
 * 商户服务查询
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.mservice_query)
public class MserviceQueryActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.mservice_query_back_btn)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "clear", delegateClass = OnClickListener.class)
	@InjectView(R.id.mservice_query_cancel_btn)
	private Button clearBtn;

	@InjectView(R.id.mservice_query_date_begin_ev)
	private EditText beginTime;

	@InjectView(R.id.mservice_query_date_end_ev)
	private EditText endTime;

	// @EventDelegate(type = DelegateType.method, toMethod = "canel",
	// delegateClass = OnClickListener.class)
	@InjectView(R.id.mservice_query_cancel_btn)
	private Button cancel;

	@EventDelegate(type = DelegateType.method, toMethod = "sure", delegateClass = OnClickListener.class)
	@InjectView(R.id.mservice_query_sure_btn)
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel(null);
			}
		});

	}

	/**
	 * 返回
	 * 
	 * @param view
	 */
	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 清除
	 * 
	 * @param view
	 */
	public void clear(View view) {

	}

	/**
	 * 取消
	 * 
	 * @param view
	 */
	public void cancel(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 确定
	 * 
	 * @param view
	 */
	public void sure(View view) {

	}

}
