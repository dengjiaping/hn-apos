package me.andpay.apos.dao;

import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 
 * @author cpz
 *
 */
public class PayTxnInfoDao extends GenSqLiteDao<PayTxnInfo, QueryPayTxnInfoCond, Integer> {

	
	public PayTxnInfoDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version, PayTxnInfo.class);
	}
	
	
	

}
