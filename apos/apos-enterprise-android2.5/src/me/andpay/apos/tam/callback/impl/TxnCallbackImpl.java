package me.andpay.apos.tam.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.callback.SessionKeepCallback;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.callback.CloudPosCallback;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.view.View;
import android.view.View.OnClickListener;

@CallBackHandler
public class TxnCallbackImpl implements TxnCallback, CloudPosCallback,
		SessionKeepCallback {
	public TxnAcitivty activity;

	public TxnControl txnControl;

	private CloudPosCallback delegate;

	/**
	 * 资源清理
	 */

	public void txnSuccess(TxnActionResponse actionResponse) {

		TxnCallbackHelper.convertResponse(actionResponse);

		// IC卡二次授权
		if (actionResponse.isIcCardTxn()) {
			CardReaderManager.secondIssuance(actionResponse
					.getAposICCardDataInfo());
			CardReaderManager.setCurrCallback(txnControl
					.getSwipCardReaderCallBack());
			return;
		}

		if (txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}

		TxnCallbackHelper.txnSuccess(txnControl.getCurrActivity(),
				actionResponse);
	}

	public void showFaild(TxnActionResponse actionResponse) {
		TxnCallbackHelper.clearAc(txnControl);
		TxnCallbackHelper.convertResponse(actionResponse);

		// IC卡二次授权
		secondIssuance(actionResponse);

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", actionResponse.getResponMsg());

		if (ExtTypes.EXT_TYPE_TXN_GET.equals(txnControl.getTxnContext()
				.getExtType())) {
			intentData.put("buttonName", "重新获取交易");
		} else {
			intentData.put("buttonName", "重新刷卡");

		}

		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FAILED, intentData);
	}

	public void onTimeout(TxnActionResponse actionResponse) {

		if (activity.isFinishing()) {
			return;
		}
		if (txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		TxnCallbackHelper.convertResponse(actionResponse);
		// secondIssuance(actionResponse);
		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(), FlowConstants.FAILED_SEPT1);
	}

	private void secondIssuance(TxnActionResponse actionResponse) {
		// IC卡二次授权
		if (actionResponse.getAposICCardDataInfo() != null) {
			CardReaderManager.setDefaultCallBack();
			CardReaderManager.secondIssuance(actionResponse
					.getAposICCardDataInfo());
		} else {
			CardReaderManager.clearScreen();
		}
	}

	public void showInputPassword(TxnActionResponse actionResponse) {
		if (txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		TxnCallbackHelper.convertResponse(actionResponse);
		secondIssuance(actionResponse);

		final OperationDialog dialog = new OperationDialog(activity,
				ResourceUtil.getString(activity, R.string.tam_txn_error_str),
				actionResponse.getResponMsg(), true);
		dialog.setCancelButtonName(ResourceUtil.getString(activity,
				R.string.com_back_str));
		dialog.setSureButtonName(ResourceUtil.getString(activity,
				R.string.tam_reinput_pwd_str));

		dialog.setCancelable(false);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				txnControl.reInputPassword();
			}
		});

		dialog.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				activity.txnControl.reInput();
			}
		});

		dialog.show();

	}

	public void initCallBack(TxnControl txnControl) {
		this.txnControl = txnControl;
		this.activity = (TxnAcitivty) txnControl.getCurrActivity();
		if (txnControl.getTxnContext().isCloudOrder()) {
			delegate = new SupportCloudTxnCallbackImpl(this.txnControl);
		}
	}

	public void networkError(TxnActionResponse actionResponse) {
		// ignore
	}

	/**
	 * 处理云订单请求
	 */
	public void pushOrderSucc(String cloudOrderId) {
		if (delegate != null) {
			delegate.pushOrderSucc(cloudOrderId);
		}

	}

	public void pushOrderNetworkError(String errorMsg) {
		if (delegate != null) {
			delegate.pushOrderNetworkError(errorMsg);
		}
	}

	/**
	 * 资源清理
	 */
	protected void clearAc() {
		if (txnControl != null && txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
	}

	public void loginFaild(String errorMsg) {
		if (txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		final PromptDialog promptDialog = new PromptDialog(activity,
				ResourceUtil.getString(activity,
						R.string.tam_txn_submit_error_str), errorMsg);
		promptDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				promptDialog.dismiss();
				Map<String, String> intentData = new HashMap<String, String>();
				intentData.put("errorMsg", "请先重新登录！然后重新刷卡");
				intentData.put("buttonName", "重新登陆");
				intentData.put("startNewFlow", "true");
				TiFlowControlImpl.instanceControl().nextSetup(
						txnControl.getCurrActivity(), FlowConstants.FAILED,
						intentData);
			}
		});
		promptDialog.show();

	}

	public void networkError(String errorMsg) {
		if (txnControl.getTxnDialog() != null
				&& txnControl.getTxnDialog().isShowing()) {
			txnControl.getTxnDialog().cancel();
		}
		secondIssuance(null);

		final PromptDialog promptDialog = new PromptDialog(activity,
				ResourceUtil.getString(activity,
						R.string.tam_txn_submit_error_str), errorMsg);
		promptDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				promptDialog.dismiss();
				Map<String, String> intentData = new HashMap<String, String>();
				intentData.put("errorMsg", "请先检查网络设置，然后再重新刷卡！");
				intentData.put("buttonName", "重新刷卡");
				TiFlowControlImpl.instanceControl().nextSetup(
						txnControl.getCurrActivity(), FlowConstants.FAILED,
						intentData);
			}
		});
		promptDialog.show();
	}
}
