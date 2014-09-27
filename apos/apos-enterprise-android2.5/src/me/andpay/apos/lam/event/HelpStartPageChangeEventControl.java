package me.andpay.apos.lam.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class HelpStartPageChangeEventControl extends AbstractEventController {

	public void onPageSelected(Activity refActivty, FormBean formBean,
			int pageNo) {

	}

	public void onPageScrolled(Activity refActivty, FormBean formBean,
			int arg0, float arg1, int arg2) {
		System.out.println("asdasd");
	}

	public void onPageScrollStateChanged(Activity refActivty,
			FormBean formBean, int arg0) {
		System.out.println("asdasd");

	}

}
