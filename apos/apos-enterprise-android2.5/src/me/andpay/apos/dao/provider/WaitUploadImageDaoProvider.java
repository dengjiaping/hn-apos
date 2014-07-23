package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WaitUploadImageDaoProvider implements Provider<WaitUploadImageDao> { 
	
	
	@Inject
	private Application application;
	
	public WaitUploadImageDao get() {
		TableName table = WaitUploadImage.class.getAnnotation(TableName.class);	
		return new WaitUploadImageDao(application.getApplicationContext(),null,null,table.version());
	}
}
