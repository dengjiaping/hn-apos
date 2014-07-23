package me.andpay.apos.dao.provider;

import me.andpay.apos.dao.TxnConfirmDao;
import me.andpay.apos.dao.model.TxnConfirm;
import me.andpay.timobileframework.sqlite.anno.TableName;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TxnConfirmDaoProvider  implements Provider<TxnConfirmDao> {
	
	@Inject
	private Application application;
	
	public TxnConfirmDao get() {
		android.util.Log.i("acti","RunTimeConfigDao");		
		TableName table = TxnConfirm.class.getAnnotation(TableName.class);	
		return new TxnConfirmDao(application.getApplicationContext(),null,null,table.version());
	}

}
