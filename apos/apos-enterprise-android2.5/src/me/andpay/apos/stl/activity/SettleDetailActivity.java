package me.andpay.apos.stl.activity;

import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.stl.event.ShowTxnListOnclickController;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


@ContentView(R.layout.stl_settle_detail_layout)
public class SettleDetailActivity extends AposBaseActivity  {
	
	public SettleOrder settleOrder;
	
	@InjectView(R.id.stl_settle_amt_tv)
	public TextView settleAmtText;
	@InjectView(R.id.stl_settle_time_tv)
	public TextView settleTimeText;
	@InjectView(R.id.stl_sales_amt_tv)
	public TextView salesText;
	@InjectView(R.id.stl_refund_amt_tv)
	public TextView refundText;
	@InjectView(R.id.stl_txn_fee_tv)
	public TextView txnFeeText;
	@InjectView(R.id.stl_txn_count_tv)
	public TextView txnCountText;
	
	@InjectView(R.id.com_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;
	
	@InjectView(R.id.stl_txn_list_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ShowTxnListOnclickController.class)
	public Button showTxnListBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		byte[] infoBytes = this.getIntent().getByteArrayExtra("settleOrder");
		settleOrder = JacksonSerializer.newPrettySerializer().deserialize(
					SettleOrder.class, infoBytes);
		settleAmtText.setText(StringConvertor.convert2Currency(settleOrder.getSettleAmt()));
//		TextPaint tp = settleAmtText.getPaint(); 
//		tp.setFakeBoldText(true);
		settleTimeText.setText(DateUtil.format("yyyy-MM-dd",
				settleOrder.getSettleTime()));
		salesText.setText(StringConvertor.convert2Currency(settleOrder.getSalesTxnAmtTotal()));
		refundText.setText(StringConvertor.convert2Currency(settleOrder.getRefundTxnAmtTotal()));
		txnFeeText.setText(StringConvertor.convert2Currency(settleOrder.getTxnFee().add(settleOrder.getSrvFee())));
		txnCountText.setText(String.valueOf(settleOrder.getTxnCount()) );

		
	}
}
