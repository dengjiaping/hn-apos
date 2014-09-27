package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.adapter.PurchaseOrderListAdapter;
import me.andpay.apos.vas.callback.QueryPoListAfterProcessHandler;
import me.andpay.apos.vas.event.PurQueryCodButtonClickController;
import me.andpay.apos.vas.event.PurchaseOrderItemClickController;
import me.andpay.apos.vas.event.QueryPoRefreshController;
import me.andpay.timobileframework.flow.TiFlowCallback;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;

@ContentView(R.layout.vas_purchaseorder_list_layout)
public class PurchaseOrderListActivity extends AposBaseActivity implements
		TiFlowCallback {

	private PurchaseOrderListAdapter adapter;
	// 用户输入条件
	private QueryPurchaseOrderInfoCond cond;

	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = PurchaseOrderItemClickController.class)
	private TiSectionListView listView;

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = QueryPoRefreshController.class)
	private PullToRefreshLayout refresh_layout;

	@InjectView(R.id.com_processing_layout)
	private View com_progress_layout;

	@InjectView(R.id.com_net_error_layout)
	private View com_net_error_layout;

	@InjectView(R.id.com_list_layout)
	private View com_list_view;

	@InjectView(R.id.com_list_info_layout)
	private View com_list_info_view;

	@InjectView(R.id.com_no_data_layout)
	private View com_no_data_layout;

	@InjectView(R.id.vas_po_list_condition_btn)
	@EventDelegateArray({
			@EventDelegate(delegateClass = OnClickListener.class, toEventController = PurQueryCodButtonClickController.class),
			@EventDelegate(delegateClass = OnLongClickListener.class, toEventController = PurQueryCodButtonClickController.class) })
	Button conditionButton;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@InjectView(R.id.vas_hascondition_image)
	public ImageView hasCondImg;

	private boolean isHasQueryCondition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refresh_layout.initView();
		listView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.vas_purchaseorder_section_layout, listView, false));
		cond = new QueryPurchaseOrderInfoCond();
		isHasQueryCondition = false;
		queryAll();
	}

	public void queryAll() {
		isHasQueryCondition = false;
		cond = new QueryPurchaseOrderInfoCond();
		queryPoList();
	}

	@SuppressWarnings("unchecked")
	public void queryPoList() {
		refresh_layout.scrollTo(0, 0);
		refresh_layout.setPullRefreshEnable(true);
		refresh_layout.setPullLoadEnable(true);
		// 生成查询请求，查询出前10条记录
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("queryForm", cond);
		request.open(VasProvider.VAS_DOMAIN_QUERY,
				isHasQueryCondition ? VasProvider.VAS_ACTION_QUERY_GETPOLIST
						: VasProvider.VAS_ACTION_QUERY_GETPOLISTSTORAGE,
				Pattern.async);
		request.callBack(new QueryPoListAfterProcessHandler(this));
		request.submit();
		showProgressView();
	}

	public void clearCondition() {
		hasCondImg.setVisibility(View.GONE);
	}

	/**
	 * 监听条件页面返回事件
	 */
	public void callback(String sourceNodeName) {
		QueryPurchaseOrderInfoCond condition = getControl().getFlowContextData(
				QueryPurchaseOrderInfoCond.class);
		if (condition == null) {
			return;
		}
		isHasQueryCondition = condition.isHasViewCond();
		hasCondImg
				.setVisibility(isHasQueryCondition ? View.VISIBLE : View.GONE);
		this.cond = condition;
		getControl().getFlowContext().remove(
				QueryPurchaseOrderInfoCond.class.getName());
		queryPoList();
	}

	public void showListView() {
		hasCondImg
				.setVisibility(isHasQueryCondition ? View.VISIBLE : View.GONE);
		com_list_view.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.VISIBLE);
		com_progress_layout.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
	}

	public void showProgressView() {
		com_list_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_list_info_view.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.VISIBLE);
	}

	public void showNoDataView() {
		com_no_data_layout.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.GONE);
		com_list_view.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public void showNetErrorView() {
		com_net_error_layout.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_list_view.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public PurchaseOrderListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(PurchaseOrderListAdapter adapter) {
		this.adapter = adapter;
	}

	public QueryPurchaseOrderInfoCond getCond() {
		return cond;
	}

	public TiSectionListView getListView() {
		return listView;
	}

	public PullToRefreshLayout getRefresh_layout() {
		return refresh_layout;
	}

	public boolean isHasQueryCondition() {
		return isHasQueryCondition;
	}

}
