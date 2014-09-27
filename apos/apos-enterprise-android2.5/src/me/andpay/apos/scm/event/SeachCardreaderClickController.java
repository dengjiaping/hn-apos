package me.andpay.apos.scm.event;

import me.andpay.apos.cardreader.callback.SearchCardreaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class SeachCardreaderClickController extends AbstractEventController {

	@Inject
	private SearchCardreaderCallBack searchCardreaderCallBack;

	@Inject
	private AposContext aposContext;

	public void onClick(Activity activity, FormBean formBean, View view) {

		searchCardreaderCallBack.setTiActivity((TiActivity) activity);
		CardReaderManager.initCardReader(activity.getApplication(),
				AposCardReaderUtil.genAposSwiperContext(aposContext
						.getAppConfig()));
		CardReaderManager.searchDevice();

	}
}
