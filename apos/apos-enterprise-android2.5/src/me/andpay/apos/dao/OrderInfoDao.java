package me.andpay.apos.dao;

import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 *  
 * @author cpz
 *
 */
public class OrderInfoDao extends GenSqLiteDao<OrderInfo, OrderInfoCond, Integer> {

	public OrderInfoDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version, OrderInfo.class);
	}
}
