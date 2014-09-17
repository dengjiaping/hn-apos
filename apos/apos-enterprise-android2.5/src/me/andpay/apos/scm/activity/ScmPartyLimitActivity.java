package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.scm.action.ScmPartyLimitAction;
import me.andpay.apos.scm.callback.impl.ScmPartyLimitCallBackImpl;
import me.andpay.apos.scm.event.LimitReturnClickControl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


@ContentView(R.layout.scm_partylimit_layout)
public class ScmPartyLimitActivity extends AposBaseActivity {
	
	
	@InjectView(R.id.scm_cd_month_use_limit_tv)
	public TextView useCreditMonthLimit;
	@InjectView(R.id.scm_cd_month_remain_limit_tv)
	public TextView remainCreditMonthLimit;
	@InjectView(R.id.scm_cd_month_total_limit_tv)
	public TextView totalCreditMonthLimit;
	@InjectView(R.id.scm_cd_month_limit)
	public LinearLayout creditMonthLimitLv;
	
	@InjectView(R.id.scm_month_use_limit_tv)
	public TextView useMonthLimit;
	@InjectView(R.id.scm_month_remain_limit_tv)
	public TextView remainMonthLimit;
	@InjectView(R.id.scm_month_total_limit_tv)
	public TextView totalMonthLimit;
	@InjectView(R.id.scm_month_limit)
	public LinearLayout monthLimitLv;
	
	@InjectView(R.id.scm_day_use_limit_tv)
	public TextView useDayLimit;
	@InjectView(R.id.scm_day_remain_limit_tv)
	public TextView remainDayLimit;
	@InjectView(R.id.scm_day_total_limit_tv)
	public TextView totalDayLimit;
	@InjectView(R.id.scm_day_limit)
	public LinearLayout dayLimitLv;
	
	

	@InjectView(R.id.scm_daily_txn_quota_sign_card_tv)
	public TextView totalDailyTxnQuota;
	@InjectView(R.id.scm_daily_txn_quota_sign_card_limit)
	public LinearLayout totalDailyTxnQuotaLv;
	
	@InjectView(R.id.scm_month_txn_quota_sign_card_tv)
	public TextView totalMonthTxnQuota;
	@InjectView(R.id.scm_month_txn_quota_sign_card_limit)
	public LinearLayout totalMonthTxnQuotaLv;
	
	
	
	@InjectView(R.id.scm_processing_layout)
	public LinearLayout processLy;
	
	
	@InjectView(R.id.scm_no_data_layout)
	public RelativeLayout noDataLy;
	
	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = LimitReturnClickControl.class)
	public ImageView backImage;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queryLimit();
	}
	
	
	public void queryLimit() {
		EventRequest request = generateSubmitRequest(this);
		request.open(ScmPartyLimitAction.DOMAIN_NAME, ScmPartyLimitAction.QUERY_PARTY_LIMIT, Pattern.async);
		showProgess();
		request.callBack(new ScmPartyLimitCallBackImpl(this));
		request.submit();
		
	}
	
	public void showProgess() {
		creditMonthLimitLv.setVisibility(View.GONE);
		monthLimitLv.setVisibility(View.GONE);
		dayLimitLv.setVisibility(View.GONE);
		processLy.setVisibility(View.VISIBLE);
		noDataLy.setVisibility(View.GONE);
	}
	
	public void hideProgess() {
		processLy.setVisibility(View.GONE);
		noDataLy.setVisibility(View.GONE);
	}
	
	public void showNoData() {
		creditMonthLimitLv.setVisibility(View.GONE);
		monthLimitLv.setVisibility(View.GONE);
		dayLimitLv.setVisibility(View.GONE);
		processLy.setVisibility(View.GONE);
		noDataLy.setVisibility(View.VISIBLE);
	}
}
