package me.andpay.apos.vas.activity.adapter;

import me.andpay.apos.R;
import me.andpay.apos.vas.activity.ProductDetailListActivity;
import me.andpay.apos.vas.helper.ProductResouceHelper;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCartCenter;
import me.andpay.apos.vas.spcart.ShoppingCartHelper;
import me.andpay.ti.util.StringUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailListAdapter extends BaseAdapter {

	
	private ProductDetailListActivity productDetailListActivity;
	
	public ProductDetailListAdapter(
			ProductDetailListActivity productDetailListActivity) {
		this.productDetailListActivity = productDetailListActivity;
	}

	public int getCount() {
		return ShoppingCartCenter.getShoppingCart().getItemsList().size();
	}
	

	public Object getItem(int position) {
		return ShoppingCartCenter.getShoppingCart().getItemsList().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ProductItem productItem = ShoppingCartCenter.getShoppingCart().getItemsList().get(position);
		
		ProductHolder productHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(productDetailListActivity).inflate(
					R.layout.vas_product_list_item_layout, null);
			productHolder = new ProductHolder();
			productHolder.setProductImge((ImageView) convertView
					.findViewById(R.id.vas_product_img));
			productHolder.setProductName((TextView) convertView
					.findViewById(R.id.vas_product_name));
			productHolder.setProductPrice((TextView) convertView
					.findViewById(R.id.vas_product_price));
			productHolder.setUnitView(convertView
					.findViewById(R.id.vas_product_unit_lay));
			productHolder.setUnitText((TextView)convertView.findViewById(R.id.vas_product_unit_text));
			productHolder.setInterImageView((ImageView) convertView
					.findViewById(R.id.vas_item_img));
			
			convertView.setTag(productHolder);
		} else {
			productHolder = (ProductHolder) convertView.getTag();
		}

	
		productHolder.getProductImge().setBackgroundResource(
				ProductResouceHelper.getProductImages(productItem
						.getProductType()));
		if(StringUtil.isNotBlank(productItem.getProductName())) {
			productHolder.getProductName().setText(productItem.getProductName());
		}
		productHolder.getProductPrice().setText(
				productItem.getPrice().toString());
		if(productItem.getUnit()<=1) {
			productHolder.getUnitView().setVisibility(View.GONE);
		}else{
			productHolder.getUnitView().setVisibility(View.VISIBLE);
			productHolder.getUnitText().setText(ShoppingCartHelper.getProductUnit(productItem.getUnit()));
		}
		
		productHolder.getInterImageView().setVisibility(View.VISIBLE);
	
		return convertView;
	}
	
	
}
