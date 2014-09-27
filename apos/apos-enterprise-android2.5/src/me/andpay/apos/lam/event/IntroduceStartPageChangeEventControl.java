package me.andpay.apos.lam.event;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ConfigAttrValues;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.lam.activity.IntroduceStartActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.view.View;

/**
 * viewPage翻页事件处理
 * 
 * @author cpz
 * 
 */
public class IntroduceStartPageChangeEventControl extends
		AbstractEventController {

	public int startFlag = 0;

	public int currentPageNo;

	public void onPageSelected(Activity refActivty, FormBean formBean,
			int pageNo) {
		IntroduceStartActivity inActivity = (IntroduceStartActivity) refActivty;
		currentPageNo = pageNo;
		startFlag = 0;
		switch (pageNo) {
		case 0:
			inActivity.mPage0.setBackgroundDrawable(inActivity.getResources()
					.getDrawable(R.drawable.com_icon_guide_dot_sel_img));
			inActivity.mPage1.setBackgroundDrawable(inActivity.getResources()
					.getDrawable(R.drawable.com_icon_guide_dot_img));
			inActivity.mPage0.setVisibility(View.VISIBLE);
			inActivity.mPage1.setVisibility(View.VISIBLE);
			// inActivity.mPage2.setVisibility(View.VISIBLE);

			break;
		case 1:
			inActivity.mPage0.setBackgroundDrawable(inActivity.getResources()
					.getDrawable(R.drawable.com_icon_guide_dot_img));
			inActivity.mPage1.setBackgroundDrawable(inActivity.getResources()
					.getDrawable(R.drawable.com_icon_guide_dot_sel_img));
			inActivity.mPage0.setVisibility(View.VISIBLE);
			inActivity.mPage1.setVisibility(View.VISIBLE);
			// inActivity.mPage2.setVisibility(View.VISIBLE);

			break;

		/*
		 * case 2: inActivity.mPage0.setVisibility(View.GONE);
		 * inActivity.mPage1.setVisibility(View.GONE);
		 * //inActivity.mPage2.setVisibility(View.GONE);
		 */
		}

	}

	public void onPageScrolled(Activity refActivty, FormBean formBean,
			int arg0, float arg1, int arg2) {
		if (currentPageNo == 1 && arg0 == 1) {
			if (startFlag == 1) {
				// 设置
				((TiActivity) refActivty).getAppConfig().setAttribute(
						ConfigAttrNames.ONCE_INSTALL_USE,
						ConfigAttrValues.ONCE_INSTALL_USE_VALUE);
				TiFlowControlImpl.instanceControl().nextSetup(refActivty,
						FlowConstants.SUCCESS);
				// Log.i("onPageScrolled", "startFlag ");
			}
			startFlag++;
		}

	}

	public void onPageScrollStateChanged(Activity refActivty,
			FormBean formBean, int arg0) {

	}

}
