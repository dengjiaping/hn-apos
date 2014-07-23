package me.andpay.apos.vas.event;

import me.andpay.apos.dao.model.QueryProductInfoCond;
import me.andpay.apos.vas.activity.ProductSalesActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class ProductQueryEditWatcherEventControl extends AbstractEventController {
	
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		
		ProductSalesActivity productSalesActivity = (ProductSalesActivity)activity;
		QueryProductInfoCond cond = new QueryProductInfoCond();
		
		String queryContent = productSalesActivity.queryEditText.getText().toString();
		if(StringUtil.isBlank(queryContent)) {
			productSalesActivity.showList(productSalesActivity.allProductInfos);
			return;
		}
		
		cond.setNameLike(queryContent);
		cond.setSorts("name+,price+");
		productSalesActivity.queryProduct(cond);
		
	}
	
	
	 public void beforeTextChanged(Activity activity, FormBean formBean,CharSequence s, int start,
             int count, int after) {
		 
	 }

	 public void afterTextChanged(Activity activity, FormBean formBean,Editable s) {
		 
	 }
	
}


