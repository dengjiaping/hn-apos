package me.andpay.apos.ssm.event;

import java.util.LinkedList;

import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.apos.ssm.activity.SettleInfoActivity;
import me.andpay.apos.ssm.callback.InfoDataUpdateCallbackHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class SettleInfoLvController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {
		SettleInfoActivity activity = (SettleInfoActivity) refActivty;
		EventRequest request = generateSubmitRequest(refActivty);
		request.open(SsmProvider.SSM_DOMAIN_QUERY,
				SsmProvider.SSM_ACTION_MAIN_QUERYSETTLEINFO, Pattern.async);
		request.getSubmitData().put("recordCounts",
				SsmProvider.SSM_MAX_COUNTS_CONST);
		LinkedList<TxnBatch> records = activity.getAdapter().getRerords();
		if (isRefresh) {
			request.getSubmitData().put("minBatchNo",
					"" + activity.getAdapter().getRerords().get(0).getId());
		} else {
			request.getSubmitData().put("maxBatchNo",
					"" + records.get(records.size() - 1).getId());
		}
		request.callBack(new InfoDataUpdateCallbackHandler(activity, isRefresh));
		request.submit();
	}

}
