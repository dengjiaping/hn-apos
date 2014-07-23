package me.andpay.apos.ssm.activity;

import java.util.LinkedList;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.apos.R;
import me.andpay.apos.ssm.action.SettleInfoUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettleInfoAdapter extends SsmBaseAdapter<TxnBatch> {

	public SettleInfoAdapter(LinkedList<TxnBatch> records, Activity activity,
			String dateStr) {
		super(records, activity, dateStr);
	}

	@Override
	public long getItemId(int position) {
		return getRerords().get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TxnBatch batch = getRerords().get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(
					getActivity().getApplicationContext()).inflate(
					R.layout.ssm_settle_info_item_layout, null);
		}
		convertView.setEnabled(false);

		TextView txnTime = (TextView) convertView
				.findViewById(R.id.ssm_main_time_tv);
		TextView batchNo = (TextView) convertView
				.findViewById(R.id.ssm_main_batchno_tv);
		TextView txnAmount = (TextView) convertView
				.findViewById(R.id.ssm_main_txnamount_tv);
		TextView txnCount = (TextView) convertView
				.findViewById(R.id.ssm_main_txncount_tv);
		TextView refundAmount = (TextView) convertView
				.findViewById(R.id.ssm_main_cancelamount_tv);
		TextView refundCount = (TextView) convertView
				.findViewById(R.id.ssm_main_cancelcount_tv);
		TextView voidAmount = (TextView) convertView
				.findViewById(R.id.ssm_main_voidamount_tv);
		TextView voidCount = (TextView) convertView
				.findViewById(R.id.ssm_main_voidcount_tv);
		TextView amount = (TextView) convertView
				.findViewById(R.id.ssm_main_amount_tv);
		TextView count = (TextView) convertView
				.findViewById(R.id.ssm_main_count_tv); 
		txnTime.setText(String.format(getDateStr(), batch.getTxnStartTime(),
				batch.getTxnEndTime()));
		batchNo.setText(batch.getTermBatchNo().toString());
		txnAmount.setText(StringConvertor.convert2Currency(SettleInfoUtil
				.getItemTotal(batch.getItems(), TxnTypes.PURCHASE)
				.doubleValue()));
		txnCount.setText(String.valueOf(SettleInfoUtil.getItemCount(
				batch.getItems(), TxnTypes.PURCHASE)));
		refundAmount
				.setText(StringConvertor.convert2Currency(SettleInfoUtil
						.getItemTotal(batch.getItems(), TxnTypes.REFUND)
						.doubleValue()));
		refundCount.setText(String.valueOf(SettleInfoUtil.getItemCount(
				batch.getItems(), TxnTypes.REFUND)));
		voidAmount.setText(StringConvertor.convert2Currency(SettleInfoUtil
				.getItemTotal(batch.getItems(), TxnTypes.VOID).doubleValue()));
		voidCount.setText(String.valueOf(SettleInfoUtil.getItemCount(
				batch.getItems(), TxnTypes.VOID)));
		convertView.setBackgroundColor(this
				.getActivity()
				.getResources()
				.getColor(
						(position % 2 == 0) ? R.color.com_bgroud_col
								: R.color.com_bgroud_common_col));

		amount.setText(StringConvertor.convert2Currency(batch.getSummary()
				.getTotal().doubleValue()));
		count.setText("" + batch.getSummary().getCount());
		convertView.invalidate();
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
