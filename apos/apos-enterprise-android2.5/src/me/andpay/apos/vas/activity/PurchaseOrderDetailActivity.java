package me.andpay.apos.vas.activity;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.activity.adapter.PurchaseOrderDetailAdapter;
import me.andpay.apos.vas.event.DetailFulfullBtnClickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@ContentView(R.layout.vas_purchaseorder_detail_layout)
public class PurchaseOrderDetailActivity extends AposBaseActivity {
	@InjectView(R.id.vas_purchaseorder_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@InjectView(R.id.vas_po_list_fulfull_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = DetailFulfullBtnClickController.class)
	Button fulfillBtn;

	@InjectView(R.id.list_view)
	private TiSectionListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PurchaseOrderInfo info = this.getControl().getFlowContextData(
				PurchaseOrderInfo.class);
		// 只有PO在支付完成状态才允许后续履约
		if (info.getStatus().equals(PurchaseOrder.STATUS_PAID)) {
			fulfillBtn.setVisibility(View.VISIBLE);
		} else {
			fulfillBtn.setVisibility(View.GONE);
		}
		listView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.vas_purchaseorder_section_layout, listView, false));
		listView.setAdapter(new PurchaseOrderDetailAdapter(this.getControl()
				.getFlowContextData(PurchaseOrderInfo.class), this));
	}

}
