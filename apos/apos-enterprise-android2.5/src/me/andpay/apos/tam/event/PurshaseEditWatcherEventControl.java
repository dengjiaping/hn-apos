package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class PurshaseEditWatcherEventControl extends AbstractEventController {
	
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		PurchaseFirstActivity purActivity = (PurchaseFirstActivity)activity;
		purActivity.amtChangeEvent();

	
		
		/*if(purActivity.orderText.length()>0) {
			purActivity.orderImgView.setVisibility(View.VISIBLE);
		}else {
			purActivity.orderImgView.setVisibility(View.GONE);
		}
		
		if(purActivity.memoText.length()>0){
			purActivity.memoImgView.setVisibility(View.VISIBLE);
		}else {
			purActivity.memoImgView.setVisibility(View.GONE);
		}*/
		
		
	
	}
	
	
	 public void beforeTextChanged(Activity activity, FormBean formBean,CharSequence s, int start,
             int count, int after) {
		 
	 }

	 public void afterTextChanged(Activity activity, FormBean formBean,Editable s) {
		 
	 }
	
}
