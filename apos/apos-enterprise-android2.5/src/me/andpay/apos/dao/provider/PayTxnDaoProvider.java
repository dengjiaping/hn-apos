package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class PayTxnDaoProvider implements Provider<PayTxnInfoDao> {

	@Inject
	private Application application;

	public PayTxnInfoDao get() {
		android.util.Log.i("acti", "PayTxnInfoDao");
		TableName table = PayTxnInfo.class.getAnnotation(TableName.class);
		return new PayTxnInfoDao(application.getApplicationContext(), null,
				null, table.version());
	}
}
