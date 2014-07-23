package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.ICCardParamsInfoDao;
import me.andpay.apos.dao.model.ICCardParamsInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ICCardParamsInfoDaoProvider implements
		Provider<ICCardParamsInfoDao> {

	@Inject
	private Application application;

	public ICCardParamsInfoDao get() {

		TableName table = ICCardParamsInfo.class
				.getAnnotation(TableName.class);
		return new ICCardParamsInfoDao(application.getApplicationContext(),
				null, null, table.version());
	}
}
