package me.andpay.timobileframework.cache;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存信息数据库操作类
 * @author tinyliu
 *
 */
public class TiCacheDBClient extends SQLiteOpenHelper {
	
	private String modeName;

	public TiCacheDBClient(Context context, String modeName, String tag) {
		super(context, context.getPackageName() + ".cache", null, 1);
		this.modeName = modeName + tag;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "
				+ this.modeName
				+ "  (_id integer primary key autoincrement,cache_url varchar(50), create_time integer, usetimes integer,cache_filename varchar(50),cache_size integer)");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public synchronized boolean insert(TiCacheInfo cacheInfo, SQLiteDatabase db) {
		try {
			db.execSQL(
					"insert into "
							+ this.modeName
							+ "(cache_url,create_time,usetimes,cache_filename,cache_size) values (?,?,?,?,?)",
					new Object[] { cacheInfo.getUrl().toString(),
							Long.valueOf(cacheInfo.getCreatAt()),
							Integer.valueOf(cacheInfo.getUsetimes()),
							cacheInfo.getFileName(),
							Long.valueOf(cacheInfo.getFileSize()) });
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public synchronized boolean update(int usetimes, String url, SQLiteDatabase db) {
		try {
			db.execSQL("update " + this.modeName
					+ " set usetimes=? where cache_url='" + url + "'",
					new Object[] { Integer.valueOf(usetimes) });
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public synchronized boolean update(long createTime, String url, SQLiteDatabase db) {
		try {
			db.execSQL("update " + this.modeName
					+ " set create_time=? where cache_url='" + url + "'",
					new Object[] { Long.valueOf(createTime) });
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public synchronized boolean updateOther(int usetimes, String url, SQLiteDatabase db) {
		try {
			db.execSQL("update " + this.modeName
					+ " set usetimes=? where cache_url not in('" + url + "')",
					new Object[] { Integer.valueOf(usetimes) });
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public synchronized TiCacheInfo select(String url, SQLiteDatabase db) {
		String sql = "select cache_url,create_time,usetimes,cache_filename,cache_size from "
				+ this.modeName + " where cache_url='" + url + "'";
		Cursor cursor = db.rawQuery(sql, null);
		if ((cursor != null) && (cursor.getCount() > 0)) {
			cursor.moveToFirst();
			TiCacheInfo cacheInfo = new TiCacheInfo();
			try {
				cacheInfo.setUrl(new URL(cursor.getString(0)));
			} catch (MalformedURLException e) {
				return null;
			}
			cacheInfo.setCreatAt(cursor.getLong(1));
			cacheInfo.setUsetimes(cursor.getInt(2));
			cacheInfo.setFileName(cursor.getString(3));
			cacheInfo.setFileSize(cursor.getLong(4));
			cursor.close();
			return cacheInfo;
		}
		return null;
	}

	public synchronized boolean delete(String url, SQLiteDatabase db) {
		try {
			db.execSQL("delete from " + this.modeName + " where cache_url='"
					+ url + "'");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public synchronized List<TiCacheInfo> selectAll(SQLiteDatabase db) {
		Cursor cursor = db.rawQuery(
				"select cache_url,create_time,usetimes,cache_filename,cache_size from "
						+ this.modeName, null);
		if ((cursor != null) && (cursor.getCount() > 0)) {
			List cacheInfos = new ArrayList();
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				TiCacheInfo cacheInfo = new TiCacheInfo();
				try {
					cacheInfo.setUrl(new URL(cursor.getString(0)));
				} catch (MalformedURLException e) {
					return null;
				}
				cacheInfo.setCreatAt(cursor.getLong(1));
				cacheInfo.setUsetimes(cursor.getInt(2));
				cacheInfo.setFileName(cursor.getString(3));
				cacheInfo.setFileSize(cursor.getLong(4));
				cacheInfos.add(cacheInfo);
			}
			cursor.close();
			return cacheInfos;
		}
		return null;
	}
}
