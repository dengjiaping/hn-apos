package me.andpay.apos.lft.activity;

import me.andpay.apos.R;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	/* 显示标题 */
	@InjectView(R.id.lft_trading_successful_showtxt)
	private TextView showTxt;

	/* 返回 */
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_trading_successful_back)
	private ImageView back;

	/* 状态图片描述 */
	@InjectView(R.id.lft_trading_successful_img)
	private ImageView img;

	/* 状态文字描述 */
	@InjectView(R.id.lft_trading_successful_status)
	private TextView statusTxt;

	@InjectExtra("txnType")
	private String type;

	@InjectExtra("isSuccess")
	private String success;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		boolean isSuccess = Boolean.valueOf(success);
		if (isSuccess) {
			img.setImageResource(R.drawable.com_salesslip_succeed_img);
		} else {
			img.setImageResource(R.drawable.com_salesslip_crying_face_img);

		}

		if (type.equals(TxnType.MPOS_TOPUP)) {// 手机充值

			showTxt.setText("手机充值");
			if (isSuccess) {
				statusTxt.setText("充值成功");
			} else {
				statusTxt.setText("充值失败");
			}

		} else if (type.equals(TxnType.MPOS_PAYCOST_ELE)) {
			showTxt.setText("缴电费");
			if (isSuccess) {
				statusTxt.setText("缴费成功");
			} else {
				statusTxt.setText("缴费失败");
			}

		} else if (type.equals(TxnType.MPOS_PAYCOST_WATER)) {
			showTxt.setText("缴水费");
			if (isSuccess) {
				statusTxt.setText("缴费成功");
			} else {
				statusTxt.setText("缴费失败");
			}
		} else if (type.equals(TxnType.MPOS_TRANSFER_ACCOUNT)) {
			showTxt.setText("转账");
			if (isSuccess) {
				statusTxt.setText("转账成功");
			} else {
				statusTxt.setText("转账失败");
			}
		} else if (type.equals(TxnType.MPOS_PAY_CREDIT_CARD)) {
			showTxt.setText("信用卡还款");
			if (isSuccess) {
				statusTxt.setText("信用卡还款成功");
			} else {
				statusTxt.setText("信用卡还款失败");
			}
		}

	}

	public void back(View view) {
		finish();
		// TiFlowControlImpl.instanceControl().previousSetup(this);
	}
}
