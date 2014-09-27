package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.model.ICCardInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ICCardInfoDaoProvider implements Provider<ICCardInfoDao> {

	@Inject
	private Application application;

	public ICCardInfoDao get() {

		TableName table = ICCardInfo.class.getAnnotation(TableName.class);
		return new ICCardInfoDao(application.getApplicationContext(), null,
				null, table.version());
	}

}
