package me.andpay.apos.vas.activity.adapter;

import java.math.BigDecimal;
import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.vas.activity.ProductSalesActivity;
import me.andpay.apos.vas.helper.ProductResouceHelper;
import me.andpay.timobileframework.util.StringConvertor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter {

	private List<ProductInfo> productInfos;

	private ProductSalesActivity productSalesActivity;

	public ProductListAdapter(List<ProductInfo> productInfos,
			ProductSalesActivity productSalesActivity) {
		this.productInfos = productInfos;
		this.productSalesActivity = productSalesActivity;
	}

	public int getCount() {
		return productInfos.size();
	}

	public Object getItem(int position) {
		return productInfos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ProductInfo productInfo = productInfos.get(position);

		ProductHolder productHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(productSalesActivity).inflate(
					R.layout.vas_product_list_item_layout, null);
			productHolder = new ProductHolder();
			productHolder.setProductImge((ImageView) convertView
					.findViewById(R.id.vas_product_img));
			productHolder.setProductName((TextView) convertView
					.findViewById(R.id.vas_product_name));
			productHolder.setProductPrice((TextView) convertView
					.findViewById(R.id.vas_product_price));
			convertView.setTag(productHolder);
		} else {
			productHolder = (ProductHolder) convertView.getTag();
		}

		productHolder.getProductImge().setBackgroundResource(
				ProductResouceHelper.getProductImages(productInfo
						.getProductType()));

		productHolder.getProductName().setText(productInfo.getName());

		BigDecimal productPrice = productInfo.getPrice();
		productHolder.getProductPrice().setText(
				StringConvertor.convert2Currency(productPrice));
		return convertView;
	}

	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}

}
