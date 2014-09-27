package me.andpay.apos.trm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.trm.TrmProvider;
import me.andpay.apos.trm.activity.RefundBatchQueryActivity;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 退款条件查询按钮事件 注意：退款查询条件页面需要隐藏交易类型选项
 * 
 * @author tinyliu
 *
 */
public class RefundCodBtClickController extends AbstractEventController {
	public void onClick(Activity refActivty, FormBean formBean, View v) {

		RefundBatchQueryActivity refundBatchQueryActivity = (RefundBatchQueryActivity) refActivty;
		Intent txnConditionIntent = new Intent(
				TqmProvider.TQM_ACTIVITY_LIST_CONDITION_ACTION);
		txnConditionIntent.putExtra(TqmProvider.TQM_CONST_HIDE_TXNTYPE, true);
		if (refundBatchQueryActivity.getAdapter() != null) {
			byte[] formByte = JacksonSerializer.newPrettySerializer()
					.serialize(
							refundBatchQueryActivity.getAdapter()
									.getCondition());
			txnConditionIntent.putExtra(TqmProvider.TQM_QUERY_COND_FORM,
					formByte);
		}

		refActivty.startActivityForResult(txnConditionIntent,
				TrmProvider.TRM_RESULT_CODE_CONDITION);
	}

	public boolean onLongClick(Activity refActivty, FormBean formBean, View v) {
		final RefundBatchQueryActivity refundBatchQueryActivity = (RefundBatchQueryActivity) refActivty;
		if (refundBatchQueryActivity.getAdapter() == null
				|| !refundBatchQueryActivity.getAdapter().getCondition()
						.isHasViewCond()) {
			return false;
		}
		final OperationDialog dialog = new OperationDialog(refActivty,
				ResourceUtil.getString(refActivty, R.string.com_show_str),
				refActivty.getResources().getString(
						R.string.tqm_condition_clear_str), true);

		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				refundBatchQueryActivity.queryAll();
			}
		});
		dialog.show();
		return true;
	}
}
