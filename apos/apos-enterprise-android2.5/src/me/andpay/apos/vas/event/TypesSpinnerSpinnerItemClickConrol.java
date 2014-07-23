package me.andpay.apos.vas.event;

import me.andpay.apos.cmview.tispinner.TiSpinnerItem;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class TypesSpinnerSpinnerItemClickConrol  extends AbstractEventController {

	public void onItemClick(Activity refActivity, FormBean formBean, View view, TiSpinnerItem selItem) {

		ImageView imageView  = (ImageView)view.getTag();
		imageView.setVisibility(View.VISIBLE);
	}

}
