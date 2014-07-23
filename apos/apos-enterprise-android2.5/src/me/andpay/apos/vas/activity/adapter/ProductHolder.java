package me.andpay.apos.vas.activity.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductHolder {

	private ImageView productImge;
	private TextView productName;
	private TextView productPrice;
	private View unitView;
	private TextView unitText;
	private ImageView interImageView;

	public ImageView getProductImge() {
		return productImge;
	}

	public void setProductImge(ImageView productImge) {
		this.productImge = productImge;
	}

	public TextView getProductName() {
		return productName;
	}

	public void setProductName(TextView productName) {
		this.productName = productName;
	}

	public TextView getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(TextView productPrice) {
		this.productPrice = productPrice;
	}

	public View getUnitView() {
		return unitView;
	}

	public void setUnitView(View unitView) {
		this.unitView = unitView;
	}

	public TextView getUnitText() {
		return unitText;
	}

	public void setUnitText(TextView unitText) {
		this.unitText = unitText;
	}

	public ImageView getInterImageView() {
		return interImageView;
	}

	public void setInterImageView(ImageView interImageView) {
		this.interImageView = interImageView;
	}
	
	

}
