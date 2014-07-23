package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.ICCardPublicKeyInfoDao;
import me.andpay.apos.dao.model.ICCardPublicKeyInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ICCardPublicKeyInfoDaoProvider implements
		Provider<ICCardPublicKeyInfoDao> {

	@Inject
	private Application application;

	public ICCardPublicKeyInfoDao get() {

		TableName table = ICCardPublicKeyInfo.class
				.getAnnotation(TableName.class);
		return new ICCardPublicKeyInfoDao(application.getApplicationContext(),
				null, null, table.version());
	}
}
