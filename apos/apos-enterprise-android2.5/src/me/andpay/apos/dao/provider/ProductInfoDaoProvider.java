package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.ProductInfoDao;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ProductInfoDaoProvider implements Provider<ProductInfoDao> {

	@Inject
	private Application application;

	public ProductInfoDao get() {
		TableName table = ProductInfo.class.getAnnotation(TableName.class);
		return new ProductInfoDao(application, null, null, table.version(),
				ProductInfo.class);
	}

}
