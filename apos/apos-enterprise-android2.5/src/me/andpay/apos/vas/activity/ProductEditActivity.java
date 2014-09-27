package me.andpay.apos.vas.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.event.ProductEditBackControl;
import me.andpay.apos.vas.event.ProductEditEventControl;
import me.andpay.apos.vas.event.ProductEditWatcherEventControl;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 产品编辑
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.vas_product_edit_layout)
public class ProductEditActivity extends AposBaseActivity {

	/**
	 * 减少产品数量
	 */
	@InjectView(R.id.vas_sub_product_imgview)
	@EventDelegate(type = DelegateType.eventController, isPassFastClick = true, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ProductEditEventControl.class)
	public ImageView subProductImgView;

	/**
	 * 添加产品数量
	 */
	@InjectView(R.id.vas_add_product_imgview)
	@EventDelegate(type = DelegateType.eventController, isPassFastClick = true, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ProductEditEventControl.class)
	public ImageView addProductImagView;

	/**
	 * 删除产品
	 */
	@InjectView(R.id.vas_delete_product_bt)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ProductEditEventControl.class)
	public Button deleteProduct;

	@InjectView(R.id.vas_product_quantity_text)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = ProductEditWatcherEventControl.class)
	public EditText productQuantityText;

	@InjectView(R.id.vas_top_back_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ProductEditBackControl.class)
	public ImageView backImage;

	@InjectView(R.id.vas_title_tv)
	public TextView vasTopTexView;
	/**
	 * 产品编号
	 */
	public Long productId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		productId = (Long) getIntent().getExtras().get("productId");

	}

	@Override
	protected void onResumeProcess() {
		ProductItem productItem = ShoppingCartCenter.getProduct(productId);
		productQuantityText.setText(Integer.valueOf(productItem.getUnit())
				.toString());
		productQuantityText
				.setSelection(productQuantityText.getText().length());
		vasTopTexView.setText(productItem.getProductName());

		// productQuantityText.setFilters(new InputFilter[] { new InputFilter()
		// {
		//
		// public CharSequence filter(CharSequence source, int start, int end,
		// Spanned dest, int dstart, int dend) {
		//
		// if(dest.length()<=0) {
		// if(source.equals("0")||StringUtil.isBlank(source.toString())) {
		// return "1";
		// }
		// }
		//
		// if(dest.length() == 1& StringUtil.isBlank(source.toString())) {
		// return dest.subSequence(dstart, dend);
		// }
		//
		// return source;
		//
		//
		// }
		// } });
	}
}
