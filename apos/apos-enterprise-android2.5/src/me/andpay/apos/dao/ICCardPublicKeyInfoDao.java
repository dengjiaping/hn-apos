package me.andpay.apos.dao;

import me.andpay.apos.dao.model.ICCardPublicKeyInfo;
import me.andpay.apos.dao.model.QueryICCardPublicKeyInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ICCardPublicKeyInfoDao
		extends
		GenSqLiteDao<ICCardPublicKeyInfo, QueryICCardPublicKeyInfoCond, Integer> {

	public ICCardPublicKeyInfoDao(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version, ICCardPublicKeyInfo.class);
	}

}
