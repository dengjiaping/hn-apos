package me.andpay.apos.tam.context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.callback.SwipCardReaderCallBack;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.tam.action.TxnAction;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.context.handler.GenChangeStatusHander;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.BeanUtils;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

/**
 * 交易过程控制控制器
 * 
 * @author cpz
 * 
 */
public class TxnControl {

	@Inject
	private SwipCardReaderCallBack swipCardReaderCallBack;

	@Inject
	public UpLoadFileServce upLoadFileServce;

	@Inject
	LocationService locationService;

	/**
	 * 当前状态对应的activity
	 */
	private TiActivity currActivity;

	private String currStatus;

	/**
	 * 交易回调函数
	 */
	private TxnCallback txnCallback;

	public CommonDialog txnDialog;

	/**
	 * 改变状态处理器
	 */
	private static Map<String, GenChangeStatusHander> changeHandlers = new HashMap<String, GenChangeStatusHander>();

	/**
	 * 注册状态改变处理器
	 * 
	 * @param status
	 * @param handler
	 */
	public static void registerHandler(String status,
			GenChangeStatusHander handler) {
		changeHandlers.put(status, handler);

	}

	@Inject
	private Application application;

	/**
	 * 交易状态改变
	 * 
	 * @param status
	 * @returnsetup
	 */
	public void changeTxnStatus(String status, TiActivity activity) {

		GenChangeStatusHander handler = changeHandlers.get(status);
		currActivity = activity;
		if (handler == null) {
			Log.w("apos error log", "can not find status handler");
		} else {
			handler.processHandle(this);
		}

		currStatus = status;
		getTxnContext().setTxnStatus(status);

	}

	private void setConfig(TiContext tiConfig, String key, String value,
			String userName) {
		if (StringUtil.isNotBlank(value)) {
			tiConfig.setAttribute(key + "_" + userName, value);
		}
	}

	public String getConfig(TiContext tiConfig, String key, String userName) {

		String value = (String) tiConfig.getAttribute(key + "_" + userName);
		return value;

	}

	public String getConfigUser(TiContext tiConfig, String key) {
		String value = (String) tiConfig.getAttribute(key);
		String[] strs = value.toString().split("_");
		return strs[0];

	}

	/**
	 * 返回主页
	 * 
	 * @param activity
	 */
	public void backHomePage(Activity activity) {

		TiContext tiContext = ((TiActivity) activity).getAppContext();
		if (OrderPayUtil.isOrderPay(tiContext)) {
			if (getTxnContext().getTxnActionResponse() != null
					&& getTxnContext().getTxnActionResponse().getTxnResponse() != null
					&& getTxnContext().getTxnActionResponse().getTxnResponse()
							.getRespCode().equals(ResponseCodes.SUCCESS)) {
				OrderPayUtil.successBackApp((TiActivity) activity);
			} else {
				OrderPayUtil.failBackApp((TiActivity) activity);
			}
			releaseResource();
			return;
		} else {
			Map<String, String> intentData = new HashMap<String, String>();
			if (getTxnContext().getBackTagName() != null) {
				intentData.put(CommonProvider.TAGNAME, getTxnContext()
						.getBackTagName());
			}
			deleteTempFile(getTxnContext());
			TiFlowControlImpl.instanceControl().nextSetup(activity,
					FlowConstants.FINISH, intentData);
			releaseResource();

		}

	}

	private void deleteTempFile(TxnContext txnContext) {

		if (StringUtil.isNotBlank(txnContext.getGoodsFileURL())) {
			File file = new File(txnContext.getGoodsFileURL());
			if (file != null && file.exists()) {
				file.delete();
			}
		}
		if (StringUtil.isNotBlank(txnContext.getSignFileURL())) {
			File file = new File(txnContext.getSignFileURL());
			if (file != null && file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 初始化上下文
	 * 
	 * @return
	 */
	public TxnContext init() {
		releaseResource();
		txnCallback = null;
		return new TxnContext();
	}

	/**
	 * 释放资源
	 */
	public void releaseResource() {

		this.currActivity = null;
		this.currStatus = null;
		this.txnCallback = null;
		this.txnDialog = null;

	}

	public boolean isDead(TiActivity activity) {
		if (activity == null || activity.isFinishing()) {
			return true;
		}
		return false;
	}

	public void submitTxn(TiActivity txnctivity) {
		submitTxn(null, txnctivity);
	}

	public void submitNoCardTxn(TiActivity txnctivity) {
		// resetTxnContext();
		submitTxn(null, txnctivity);
	}

	/**
	 * 提交交易
	 */
	public void submitTxn(String pin, final TiActivity txnctivity){
		if (isDead(currActivity)) {
			return;
		}
		final AposBaseActivity txnAcitivty = (AposBaseActivity) currActivity;

		if (StringUtil.isNotBlank(pin)) {
			getTxnContext().setPin(pin);
		} else {
			getTxnContext().setPin(null);
		}

		EventRequest request = txnAcitivty.generateSubmitRequest(txnAcitivty);
		request.open(TxnAction.DOMAIN_NAME, TxnAction.TXN_ACTION, Pattern.async);
		txnDialog = new CommonDialog(txnAcitivty, txnAcitivty.getResources()
				.getString(R.string.tam_txn_submit_str));

		txnDialog.show();
		txnCallback.initCallBack(this);
		request.callBack(txnCallback);

		Map submitData = new HashMap();
		TxnForm txnForm = BeanUtils.copyProperties(TxnForm.class,
				getTxnContext());
		submitData.put("txnForm", txnForm);
		request.submit(submitData);
	}

	/**
	 * 在流程中的重新刷卡
	 */
	public void retrySwiperInFlow(Activity activity) {

		if (ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(getTxnContext()
				.getExtType())) {
			TiFlowControlImpl.instanceControl().previousSetup(activity);
		} else {
			// resetTxnContext();
			getTxnContext().setPinErrorCount(0);
			CardReaderManager.setCurrCallback(swipCardReaderCallBack);
			TiFlowControlImpl.instanceControl().previousSetup(activity);

		}

	}

	public void retrySwiper() {
		if (isDead(currActivity)) {
			return;
		}
		TxnAcitivty txnAcitivty = (TxnAcitivty) currActivity;
		// resetTxnContext();
		getTxnContext().setPinErrorCount(0);
		CardReaderManager.setCurrCallback(txnAcitivty.swipCardReaderCallBack);
		txnAcitivty.txnControl.changeTxnStatus(TxnStatus.WAIT_SWIPER,
				txnAcitivty);
	}

	public void reInput(final TxnAcitivty txnAcitivty) {
		if (isDead(txnAcitivty)) {
			return;
		}

		final TxnContext txc = getTxnContext();
		txnAcitivty.runOnUiThread(new Runnable() {
			public void run() {
				if (CardReaderManager.isInput()
						&& !ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(txc
								.getExtType())) {
					txnAcitivty.topTextView.setText(ResourceUtil.getString(
							application, R.string.com_device_stopping_str));
				}

			}
		});

		Thread backThread = new Thread(new Runnable() {
			public void run() {
				CardReaderManager.setDefaultCallBack();

				if (CardReaderManager.isInput()
						&& !ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(txc
								.getExtType())) {
					CardReaderManager.stopSwiper();
				}
				txnAcitivty.runOnUiThread(new Runnable() {
					public void run() {
						if (isDead(txnAcitivty)) {
							return;
						}

						if (txnAcitivty.gifDrawable != null) {
							txnAcitivty.gifDrawable.recycle();
						}
						TiContext tiContext = (txnAcitivty).getAppContext();
						if (OrderPayUtil.isOrderPay(tiContext)) {
							OrderPayUtil.failBackApp(txnAcitivty);
							return;
						}
						TiFlowControlImpl.instanceControl().nextSetup(
								txnAcitivty, FlowConstants.FINISH1);
						currActivity = null;

					}
				});

			}
		});
		backThread.start();

	}

	public void reInputPassword() {
		if (CardReaderManager.getInputType() == AposSwiperContext.INPUT_CARD_READER) {
			CardReaderManager.setCurrCallback(swipCardReaderCallBack);
			changeTxnStatus(TxnStatus.WAIT_PASSWORD_OUT, getCurrActivity());
		} else {
			changeTxnStatus(TxnStatus.WAIT_PASSWORD, getCurrActivity());

		}
	}

	/**
	 * 取消刷卡，返回输入页面
	 */
	public void reInput() {
		reInput((TxnAcitivty) currActivity);
	}

	/**
	 * 获取当前cardreadertype
	 * 
	 * @return
	 */
	public int getCurrentCardReaderType() {
		return Integer.parseInt((String) this.currActivity.getAppConfig()
				.getAttribute(ConfigAttrNames.CARD_READER_TYPE));
	}

	public TxnContext getTxnContext() {
		return TiFlowControlImpl.instanceControl().getFlowContextData(
				TxnContext.class);
	}

	public void setCurrActivity(TiActivity currActivity) {
		this.currActivity = currActivity;
	}

	public TiActivity getCurrActivity() {
		return currActivity;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public TxnCallback getTxnCallback() {
		return txnCallback;
	}

	public void setTxnCallback(TxnCallback txnCallback) {
		if (this.txnCallback != null) {
			return;
		}
		this.txnCallback = txnCallback;
	}

	public CommonDialog getTxnDialog() {
		return txnDialog;
	}

	public static Map<String, GenChangeStatusHander> getChangeHandlers() {
		return changeHandlers;
	}

	public SwipCardReaderCallBack getSwipCardReaderCallBack() {
		return swipCardReaderCallBack;
	}

}
