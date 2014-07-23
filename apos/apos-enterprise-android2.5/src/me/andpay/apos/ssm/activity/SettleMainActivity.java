package me.andpay.apos.ssm.activity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.apos.ssm.callback.NumberCounter;
import me.andpay.apos.ssm.event.MainHistoryController;
import me.andpay.apos.ssm.event.MainSettleBackController;
import me.andpay.apos.ssm.event.MainSettleController;
import me.andpay.apos.ssm.event.RefreshSettleController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@ContentView(R.layout.ssm_main_layout)
public class SettleMainActivity extends AposBaseActivity implements ValueContainer {

	@InjectView(R.id.ssm_main_time_tv)
	private TextView ssm_main_time_tv;

	@InjectView(R.id.ssm_main_txncount_tv)
	private TextView ssm_main_txncount_tv;

	@InjectView(R.id.ssm_main_txnamount_tv)
	private TextView ssm_main_txnamount_tv;

	@InjectView(R.id.ssm_main_cancelcount_tv)
	private TextView ssm_main_cancelcount_tv;

	@InjectView(R.id.ssm_main_cancelamount_tv)
	private TextView ssm_main_cancelamount_tv;

	@InjectView(R.id.ssm_main_voidcount_tv)
	private TextView ssm_main_voidcount_tv;

	@InjectView(R.id.ssm_main_voidamount_tv)
	private TextView ssm_main_voidamount_tv;

	@InjectView(R.id.ssm_main_count_tv)
	private TextView ssm_main_count_tv;

	@InjectView(R.id.ssm_main_amount_tv)
	private TextView ssm_main_amount_tv;

	@InjectView(R.id.ssm_main_history_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = MainHistoryController.class)
	private Button ssm_main_history_btn;

	@InjectView(R.id.com_net_error_layout)
	View com_net_error_layout;

	@InjectView(R.id.ssm_main_settle_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = MainSettleController.class)
	public Button ssm_main_settle_btn;

	@InjectView(R.id.ssm_has_data_layout)
	private View ssm_has_data_layout;
	
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = MainSettleBackController.class)
	@InjectView(R.id.com_back_btn)
	public ImageView backImage;

//	@InjectView(R.id.com_processing_layout)
//	View com_progress_layout;

	@InjectResource(R.string.ssm_main_time_str)
	private String timeDescription;

	@InjectResource(R.string.com_progress_prompt_str)
	private String com_progress_prompt_str;

	@InjectResource(R.string.ssm_settle_fail_str)
	private String scm_settle_fail_str;

	private CommonDialog dialog = null;
	
	@InjectView(R.id.ssm_settle_refresh_img)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = RefreshSettleController.class)
	private ImageView refreshImg;
	
	@InjectView(R.id.com_title_processing_pb)
	private ProgressBar progressBar;
	
//	@InjectView(R.id.com_show_silder_btn)
//	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
//	public ImageView showSilder;

	// private CommonDialog settleFailDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ssm_main_settle_btn.setEnabled(false);
		dialog = new CommonDialog(this, com_progress_prompt_str);
		// settleFailDialog = new CommonDialog(this, com_progress_prompt_str);
		loadUnCheckOutInfo();
	}

	/**
	 * 加载用户未结算信息
	 */
	public void loadUnCheckOutInfo() {
		EventRequest request = this.generateSubmitRequest(this);
		request.open(SsmProvider.SSM_DOMAIN_QUERY,
				SsmProvider.SSM_ACTION_MAIN_LOADUNSETTLEINFO, Pattern.async);
		request.callBack(new UpdateSettleInfoCallBack(this));
		request.submit();
		showProgressView();

	}

	public void resetView() {
		ssm_main_time_tv.setText("");
		ssm_main_cancelamount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_amount_default_str));
		ssm_main_voidamount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_amount_default_str));
		ssm_main_amount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_amount_default_str));
		ssm_main_txnamount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_amount_default_str));

		ssm_main_cancelcount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_count_default_str));
		ssm_main_voidcount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_count_default_str));
		ssm_main_count_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_count_default_str));
		ssm_main_txncount_tv.setText(this.getApplication().getResources()
				.getString(R.string.ssm_main_settle_count_default_str));
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void showListView() {
		refreshImg.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
	}

	public void showProgressView() {
//		ssm_has_data_layout.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		com_net_error_layout.setVisibility(View.GONE);
		refreshImg.setVisibility(View.GONE);
	}

	public void showNetErrorView() {
		com_net_error_layout.setVisibility(View.VISIBLE);
		ssm_has_data_layout.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
		refreshImg.setVisibility(View.VISIBLE);
	}

	public CommonDialog getDialog() {
		return dialog;
	}

	/**
	 * 该用户批次未结算信息显示
	 * 
	 * @author tinyliu
	 * 
	 */
	class UpdateSettleInfoCallBack extends AfterProcessWithErrorHandler {

		public UpdateSettleInfoCallBack(Activity activity) {
			super(activity);
		}

		public void afterRequest(ModelAndView mv) {
			Boolean hasUnSettleInfo = (Boolean) mv.getModels().get(
					"hasUnSettleInfo");
			showListView();
			/**
			 * 如果没有未结帐信息，则disable未结帐信息查看和发送邮件的按钮 并且激活无数据的提示信息
			 */
			if (hasUnSettleInfo == false) {
				resetView();
				ssm_main_settle_btn.setEnabled(false);
				return;
			}

			ssm_main_settle_btn.setEnabled(true);
			Date beginDate = (Date) mv.getModels().get("beginDate");
			Date endDate = (Date) mv.getModels().get("endDate");

			String txnCount = (String) mv.getModels().get("txnCount");
			String txnAmount = (String) mv.getModels().get("txnAmount");
			String cancelCount = (String) mv.getModels().get("cancelCount");
			String cancelAmount = (String) mv.getModels().get("cancelAmount");
			String voidCount = (String) mv.getModels().get("voidCount");
			String voidAmount = (String) mv.getModels().get("voidAmount");
			String amount = (String) mv.getModels().get("amount");
			String count = (String) mv.getModels().get("count");

			final NumberCounter txnCountCounter = new NumberCounter(
					ssm_main_txncount_tv.getText().toString(), txnCount);
			final NumberCounter txnAmtCounter = new NumberCounter(
					StringConvertor.convertCurrency2Str(ssm_main_txnamount_tv
							.getText().toString()), txnAmount);
			final NumberCounter cancelCountCounter = new NumberCounter(
					ssm_main_cancelcount_tv.getText().toString(), cancelCount);
			final NumberCounter cancelAmtCounter = new NumberCounter(
					StringConvertor.convertCurrency2Str(ssm_main_cancelamount_tv
							.getText().toString()), cancelAmount);
			final NumberCounter voidCountCounter = new NumberCounter(
					ssm_main_voidcount_tv.getText().toString(), voidCount);
			final NumberCounter voidAmtCounter = new NumberCounter(
					StringConvertor.convertCurrency2Str(ssm_main_voidamount_tv
							.getText().toString()), voidAmount);
			final NumberCounter amtCounter = new NumberCounter(
					StringConvertor.convertCurrency2Str(ssm_main_amount_tv
							.getText().toString()), amount);
			final NumberCounter countCounter = new NumberCounter(
					ssm_main_count_tv.getText().toString(), count);

			Timer timer = new Timer();
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 1:
						ssm_main_cancelamount_tv.setText(StringConvertor
								.convert2Currency(cancelAmtCounter
										.getAndAddNum()));
						ssm_main_voidamount_tv
								.setText(StringConvertor
										.convert2Currency(voidAmtCounter
												.getAndAddNum()));
						ssm_main_amount_tv.setText(StringConvertor
								.convert2Currency(amtCounter.getAndAddNum()));
						ssm_main_txnamount_tv
								.setText(StringConvertor
										.convert2Currency(txnAmtCounter
												.getAndAddNum()));

						ssm_main_cancelcount_tv.setText(cancelCountCounter
								.getAndAddNum().toString());
						ssm_main_voidcount_tv.setText(voidCountCounter
								.getAndAddNum().toString());
						ssm_main_count_tv.setText(countCounter.getAndAddNum()
								.toString());
						ssm_main_txncount_tv.setText(txnCountCounter
								.getAndAddNum().toString());
					}
				}
			};
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					if (txnCountCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (txnAmtCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (cancelCountCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (cancelAmtCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (voidCountCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (voidAmtCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (amtCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					if (countCounter.hasNextNum()) {
						sendMsg();
						return;
					}
					cancel();

				}

				private void sendMsg() {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);

				}
			};
			
			ssm_main_settle_btn.setEnabled(true);

			ssm_main_time_tv.setText(String.format(timeDescription, beginDate,
					endDate));
			ssm_main_time_tv.setText(String.format(timeDescription, beginDate,
					endDate));
			timer.schedule(task, 200, 200);


		}

		@Override
		public void refreshAfterNetworkError() {
			showListView();
			loadUnCheckOutInfo();
		}

	}

	public Button getSsm_main_settle_btn() {
		return ssm_main_settle_btn;
	}

	// public CommonDialog getSettleFailDialog() {
	// return settleFailDialog;
	// }

}
