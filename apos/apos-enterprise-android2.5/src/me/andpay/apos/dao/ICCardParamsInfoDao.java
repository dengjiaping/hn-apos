package me.andpay.apos.dao;

import me.andpay.apos.dao.model.ICCardParamsInfo;
import me.andpay.apos.dao.model.QueryICCardParamsInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ICCardParamsInfoDao extends
		GenSqLiteDao<ICCardParamsInfo, QueryICCardParamsInfoCond, Integer> {

	public ICCardParamsInfoDao(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version, ICCardParamsInfo.class);
	}

}
