package me.andpay.apos.dao.provider;

import javax.inject.Provider;

import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;

public class OrderInfoDaoProvider  implements Provider<OrderInfoDao> {
	
	@Inject
	private Application application;

	public OrderInfoDao get() {
		android.util.Log.i("acti", "OrderInfoDao");
		TableName table = OrderInfo.class.getAnnotation(TableName.class);
		return new OrderInfoDao(application.getApplicationContext(), null,
				null, table.version());
	}
}
