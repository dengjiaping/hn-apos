package me.andpay.apos.dao;


import me.andpay.apos.dao.model.QueryTxnConfirmCond;
import me.andpay.apos.dao.model.TxnConfirm;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class TxnConfirmDao extends GenSqLiteDao<TxnConfirm,QueryTxnConfirmCond, Integer>  {

	public TxnConfirmDao(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version, TxnConfirm.class);
	}

}
