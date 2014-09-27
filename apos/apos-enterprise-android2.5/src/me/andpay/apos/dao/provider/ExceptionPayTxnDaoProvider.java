package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ExceptionPayTxnDaoProvider implements
		Provider<ExceptionPayTxnInfoDao> {

	@Inject
	private Application application;

	public ExceptionPayTxnInfoDao get() {

		TableName table = ExceptionPayTxnInfo.class
				.getAnnotation(TableName.class);
		return new ExceptionPayTxnInfoDao(application.getApplicationContext(),
				null, null, table.version());
	}
}
