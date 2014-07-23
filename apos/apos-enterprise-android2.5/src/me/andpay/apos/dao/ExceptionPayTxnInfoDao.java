package me.andpay.apos.dao;

import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.QueryExceptionPayTxnInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 
 * @author cpz
 *
 */
public class ExceptionPayTxnInfoDao extends GenSqLiteDao<ExceptionPayTxnInfo, QueryExceptionPayTxnInfoCond, Integer> {

	
	public ExceptionPayTxnInfoDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version, ExceptionPayTxnInfo.class);
	}
	

}
