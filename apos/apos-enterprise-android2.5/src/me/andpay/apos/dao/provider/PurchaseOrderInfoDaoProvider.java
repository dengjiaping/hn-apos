package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class PurchaseOrderInfoDaoProvider implements Provider<PurchaseOrderInfoDao> {

	@Inject
	private Application application;

	public PurchaseOrderInfoDao get() {
		TableName table = PurchaseOrderInfo.class.getAnnotation(TableName.class);
		return new PurchaseOrderInfoDao(application, null, null, table.version(),
				PurchaseOrderInfo.class);
	}

}
