package me.andpay.apos.vas.event;

import me.andpay.apos.vas.VasProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 显示产品明细
 * 
 * @author cpz
 *
 */
public class ShowProductDetailControl extends AbstractEventController {

	public void onClick(Activity refActivity, FormBean formBean, View v) {
		Intent intent = new Intent();
		intent.setAction(VasProvider.VAS_PRODUCT_DETAIL_ACTIVITY);
		refActivity.startActivity(intent);
	}

}
