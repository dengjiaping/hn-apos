package me.andpay.apos.dao;

import me.andpay.apos.dao.model.QueryWaitUploadImageCond;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class WaitUploadImageDao extends
		GenSqLiteDao<WaitUploadImage, QueryWaitUploadImageCond, Integer> {

	public WaitUploadImageDao(Context context,String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version, WaitUploadImage.class);
	}
}
