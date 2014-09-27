package me.andpay.apos.dao;

import me.andpay.apos.dao.model.ICCardInfo;
import me.andpay.apos.dao.model.QueryICCardInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ICCardInfoDao extends
		GenSqLiteDao<ICCardInfo, QueryICCardInfoCond, Integer> {

	public ICCardInfoDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version, ICCardInfo.class);
	}

}
