package me.andpay.apos.vas.event;

import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.VasMainActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 增值服务主页控制器
 * @author cpz
 *
 */
public class VasMainEventControl  extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		VasMainActivity mainActivity = (VasMainActivity)activity;
		
		if(mainActivity.salesLayout.getId() == view.getId()) {
			Intent intent = new Intent();
			intent.setAction(VasProvider.VAS_PRODUCTSALES_ACTIVITY);
			mainActivity.startActivity(intent);
		}
		
	}

}
