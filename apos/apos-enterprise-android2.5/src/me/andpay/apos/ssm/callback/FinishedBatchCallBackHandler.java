package me.andpay.apos.ssm.callback;

import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.apos.ssm.activity.SettleMainActivity;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.util.DialogUtil;
import android.content.Intent;

public class FinishedBatchCallBackHandler extends AfterProcessWithErrorHandler {

	private SettleMainActivity mainActivity;

	private PayTxnInfoDao dao = null;

	public FinishedBatchCallBackHandler(SettleMainActivity mainActivity,
			PayTxnInfoDao dao) {
		super(mainActivity);
		this.mainActivity = mainActivity;
		this.dao = dao;
		
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		DialogUtil.setDialogAllowClose(mainActivity.getDialog().getDialog(),
				true);
		mainActivity.getDialog().cancel();
		Object batchObject = mv.getValue("batch");
		
		if (batchObject != null) {
			TxnBatch batch = (TxnBatch)batchObject;
			TiContext context = mainActivity.getAppContext();
			PartyInfo party = (PartyInfo) context
					.getAttribute(RuntimeAttrNames.PARTY_INFO);
			LoginUserInfo info = (LoginUserInfo) context
					.getAttribute(RuntimeAttrNames.LOGIN_USER);
			dao.getWritableDatabase()
					.execSQL(
							"update PayTxnInfo set termBatchNo = ? where termBatchNo is null and txnStatus = ?  and operNo = ? and txnPartyId = ?",
							new Object[] { batch.getTermBatchNo(),
									PayTxnInfoStatus.STATUS_SUCCESS,
									info.getUserName(),
									party.getPartyId() });
			Intent sendMailIntent = new Intent(
					SsmProvider.SSM_ACTIVITY_SEND_MAIL);
			sendMailIntent.putExtra("batchId", batch.getId());
			mainActivity.resetView();
			mainActivity.ssm_main_settle_btn.setEnabled(false);

			mainActivity.startActivity(sendMailIntent);
		} else {
			String errorMsg = (String)mv.getValue("errorMsg");
			PromptDialog dialog = new PromptDialog(mainActivity, mainActivity
					.getResources().getString(R.string.scm_settle_error_str),
					errorMsg);
			dialog.show();
		}
	}

	@Override
	protected void refreshAfterNetworkError() {
		mainActivity.getSsm_main_settle_btn().performClick();
	}

}
