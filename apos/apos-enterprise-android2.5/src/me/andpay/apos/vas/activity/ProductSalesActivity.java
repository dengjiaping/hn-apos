package me.andpay.apos.vas.activity;

import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.ProductInfoDao;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.dao.model.QueryProductInfoCond;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.vas.action.ProductAction;
import me.andpay.apos.vas.activity.adapter.ProductListAdapter;
import me.andpay.apos.vas.callback.ProductLocalQueryCallbackImpl;
import me.andpay.apos.vas.event.ProductCommonBackControl;
import me.andpay.apos.vas.event.ProductItemClickControl;
import me.andpay.apos.vas.event.ProductPayClickControl;
import me.andpay.apos.vas.event.ProductQueryEditWatcherEventControl;
import me.andpay.apos.vas.event.ShowProductDetailControl;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 产品消费页面
 * 
 * @author cpz
 */
@ContentView(R.layout.vas_product_sales_layout)
public class ProductSalesActivity extends AposBaseActivity {

	@InjectView(R.id.vas_product_list)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = ProductItemClickControl.class)
	public ListView productList;

	@InjectView(R.id.com_processing_layout)
	public LinearLayout progress;

	@InjectView(R.id.vas_total_price_text)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ShowProductDetailControl.class)
	public TextView totalPrice;

	@InjectView(R.id.vas_product_total_text)
	public TextView productTotal;

	@InjectView(R.id.vas_product_total_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ShowProductDetailControl.class)
	public RelativeLayout productTotalLay;

	@InjectView(R.id.vas_next_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ProductPayClickControl.class)
	public ImageView nextImView;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ProductCommonBackControl.class)
	public ImageView backBtn;

	@Inject
	private ProductInfoDao productInfoDao;

	public ProductListAdapter productListAdapter;

	@InjectView(R.id.vas_query_content_edt)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = ProductQueryEditWatcherEventControl.class)
	public EditText queryEditText;

	@InjectView(R.id.com_no_data_layout)
	public RelativeLayout noDatalayout;

	public List<ProductInfo> allProductInfos;

	@Inject
	public TxnControl txnControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShoppingCartCenter.clearShoppingCard();
		// 暂时隐藏按钮
		nextImView.setVisibility(View.GONE);
		productTotal.setVisibility(View.GONE);
		productTotalLay.setVisibility(View.GONE);

		nextImView.setEnabled(false);
		QueryProductInfoCond cond = new QueryProductInfoCond();
		PartyInfo partyInfo = (PartyInfo) getAppContext().getAttribute(
				RuntimeAttrNames.PARTY_INFO);
		cond.setMerchPartyId(partyInfo.getPartyId());
		queryProduct(cond);
	}

	public void queryProduct(QueryProductInfoCond cond) {
		showProgress();
		EventRequest request = this.generateSubmitRequest(this);
		request.open(ProductAction.DOMAIN_NAME, ProductAction.QUERY_LOCAL,
				Pattern.async);
		request.getSubmitData().put("QueryProductInfoCond", cond);
		request.callBack(new ProductLocalQueryCallbackImpl(this));
		request.submit();
	}

	public void showProgress() {
		progress.setVisibility(View.VISIBLE);
		noDatalayout.setVisibility(View.GONE);
		productList.setVisibility(View.GONE);
	}

	public void showList(List<ProductInfo> productInfos) {

		if (productInfos == null || productInfos.isEmpty()) {
			showNoDate();
			return;
		}
		if (productListAdapter == null) {
			productListAdapter = new ProductListAdapter(productInfos, this);
			productList.setAdapter(productListAdapter);
		} else {
			productListAdapter.setProductInfos(productInfos);
		}

		progress.setVisibility(View.GONE);
		noDatalayout.setVisibility(View.GONE);
		productList.setVisibility(View.VISIBLE);
		productListAdapter.notifyDataSetChanged();
	}

	public void showNoDate() {
		progress.setVisibility(View.GONE);
		noDatalayout.setVisibility(View.VISIBLE);
		productList.setVisibility(View.GONE);

	}

	public ProductListAdapter getProductListAdapter() {
		return productListAdapter;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShoppingCartCenter.clearShoppingCard();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);

	}

}
