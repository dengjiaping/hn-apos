package me.andpay.apos.trm.event;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.trm.activity.RefundInputActivity;
import me.andpay.apos.trm.callback.NoCardRefundTxnCallback;
import me.andpay.apos.trm.flow.model.RefundInputContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.NetWorkUtil;
import roboguice.inject.InjectResource;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

public class NoCardBtnClickController extends AbstractEventController {

	@Inject
	protected TxnControl txnControl;

	@InjectResource(R.string.trm_refund_input_frist_nocard_str)
	private String noCardTitle;

	@InjectResource(R.string.trm_refund_nocard_prompt_str)
	private String noCardPromptStr;

	@Inject
	private LocationService locationService;

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		RefundInputActivity activity = (RefundInputActivity) refActivty;
		if (activity.getAmtEditText().getText().toString().equals("")
				|| activity
						.getAmtEditText()
						.getText()
						.toString()
						.equals(ResourceUtil.getString(activity,
								R.string.com_amt_str))) {

			final PromptDialog dialog = new PromptDialog(activity, "提示",
					ResourceUtil.getString(activity,
							R.string.trm_refund_amt_big_zero_str));
			dialog.setSureButtonOnclickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			return;
		}

		String msg1 = activity.dyHelper.checkDate(
				FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO, TxnTypes.REFUND,
				activity.extTraceNo);

		String msg2 = activity.dyHelper
				.checkDate(FlexFieldDefine.FIELD_NAME_MEMO, TxnTypes.REFUND,
						activity.memo);

		if (msg1 != null || msg2 != null) {
			final PromptDialog dialog = new PromptDialog(activity, "提示",
					msg1 == null ? msg2 : msg1);
			dialog.setSureButtonOnclickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			return;
		}

		RefundInputContext refundInputContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(RefundInputContext.class);

		TxnContext txnContext = txnControl.init();
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);

		txnContext.setTxnType(TxnTypes.REFUND);
		txnContext.setMemo(activity.memo);
		txnContext.setBackTagName(TabNames.QUERY_PAGE);
		txnContext.setAmtFomat(activity.getAmt().getText().toString());
		txnContext.setExtTraceNo(activity.extTraceNo);
		txnContext.setOrigPayTxnInfo(refundInputContext.getPayTxnInfo());
		processAfterContextSet(txnContext, activity);

	}

	public void processAfterContextSet(final TxnContext txnContext,
			final TiActivity refActivty) {
		final OperationDialog dialog = new OperationDialog(refActivty,
				noCardTitle, noCardPromptStr, true);
		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				submitTxn();
			}

			private void submitTxn() {
				if (!NetWorkUtil.isNetworkConnected(refActivty
						.getApplicationContext())) {
					final OperationDialog opDialog = new OperationDialog(
							refActivty, ResourceUtil.getString(refActivty,
									R.string.tam_txn_newwork_error_str),
							ResourceUtil.getString(refActivty,
									R.string.com_please_check_network_str),
							true);
					opDialog.setSureButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							opDialog.dismiss();
							submitTxn();
						}
					});
					opDialog.setCancelButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							opDialog.dismiss();
							refActivty.finish();
						}
					});
					opDialog.setCancelButtonName(ResourceUtil.getString(
							refActivty, R.string.com_back_str));
					opDialog.setSureButtonName(ResourceUtil.getString(
							refActivty, R.string.tam_txn_retry_str));

					opDialog.show();
					return;
				}
				txnControl.setCurrActivity(refActivty);
				NoCardRefundTxnCallback callback = new NoCardRefundTxnCallback();
				txnControl.setTxnCallback(callback);
				txnControl.submitTxn(refActivty);
			}
		});
		dialog.show();

	}

}
