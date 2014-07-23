package me.andpay.timobileframework.sqlite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.Expression;
import me.andpay.timobileframework.sqlite.anno.TableName;
import me.andpay.timobileframework.sqlite.convert.DataConverter;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库统一处理类
 * 
 * @author cpz
 * 
 * @param <T>
 * @param <Q>
 * @param <K>
 */
public abstract class GenSqLiteDao<T, Q, K> extends SQLiteOpenHelper implements
		SqLiteDao<T, Q, K> {

	private Context genContext;
	
	public static Map<String, DataConverter> dataConverters = new HashMap<String, DataConverter>();


	public GenSqLiteDao(Context context, String name, CursorFactory factory,
			int version, Class<? extends T> t) {

		super(context, name == null ? t.getSimpleName() : name, factory,
				version);
		clazz = t;
		genContext = context;

		if (!isTableExist()) {
			SQLiteDatabase sqlDb = getWritableDatabase();
			onCreate(sqlDb);
		}

	}

	private boolean isTableExist() {
		ModelDbProp dpProp = SqLiteUtil.getModelDbProp(clazz);
		SQLiteDatabase sqlDb = getWritableDatabase();
		String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
				+ dpProp.getTableName() + "' ";
		Cursor cursor = sqlDb.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count > 0) {
				return true;
			}
		}
		return false;
	}

	protected Class<? extends T> clazz;
	
	
	private static DataConverter getDataConverter(Expression expression,
			Class<?> clazz, Field field) throws InstantiationException,
			IllegalAccessException {
		if (expression.dataConverter().isInterface()) {
			return null;
		}
		String key = clazz.getName() + "." + field.getName();
		DataConverter dataConverter = dataConverters.get(key);
		if (dataConverter == null) {
			dataConverter = expression.dataConverter().newInstance();
			dataConverters.put(key, dataConverter);
		}
		return dataConverter;
	}

	public List<T> query(Q cond, long firstResult, long maxResults) {

		SQLiteDatabase sqlDb = getWritableDatabase();
		Class<?> condClass = cond.getClass();
		ModelDbProp dpProp = SqLiteUtil.getModelDbProp(clazz);

		StringBuffer whereSql = new StringBuffer();
		List<String> whereArgs = new ArrayList<String>();

		boolean firstFlag = false;

		for (Field field : condClass.getDeclaredFields()) {
			Expression expression = field.getAnnotation(Expression.class);

			if (expression != null) {

				try {
					
					String colFieldName = expression.paraName();
					String colName = SqLiteUtil.getFieldName(field,
							colFieldName, clazz);
					field.setAccessible(true);

					Object value = field.get(cond);
					if (value == null) {
						continue;
					}
					String logicSymbol = " ";
					if(!firstFlag) {
						firstFlag = true;
					}else {
						logicSymbol = "and";
					}

					
					
					DataConverter dataConverter = getDataConverter(expression, condClass, field);
					if(dataConverter!=null) {
						value = dataConverter.convertToString(value);
					}
					
					String sqlFormart = expression.sqlformat();
					if(StringUtil.isNotBlank(sqlFormart)) {
						sqlFormart = sqlFormart.replace("${paraName}", colName);
						sqlFormart = sqlFormart.replace("${value}", value.toString());
						whereSql.append(" " +logicSymbol+" "+sqlFormart);
						continue;
					}
		
					//in 的处理方法
					if (field.getType().equals(Set.class)) {
						Set valueSet = (Set) value;
						if (valueSet.size() == 0) {
							continue;
						}

						whereSql.append(" " + logicSymbol + " " + colName
								+ " in(");

						for (Object tv : valueSet) {
							whereSql.append("?,");
							whereArgs.add(tv.toString());
						}
						whereSql.delete(whereSql.length()-1, whereSql.length());
						whereSql.append(")  ");

						continue;
					} else {
						//like 处理方法
						String operater = expression.operater();
						if (StringUtil.isNotBlank(operater)) {
							if (operater.equals("like")) {
								value = "%" + value + "%";
							}
						} else {
							operater = "=";
						}

						whereSql.append(" " + logicSymbol + " " + colName + " "
								+ operater + " " + "?");
						whereArgs.add(value.toString());
					}


				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		}
		
		Log.d(this.getClass().getName(), whereSql.toString());


		String sortSqlStr = getSorts(cond);

		String[] whereArgArr = null;
		if (whereArgs.size() > 0) {
			whereArgArr = whereArgs.toArray(new String[whereArgs.size()]);
		}

		String limit = firstResult + ", " + maxResults;
		if (maxResults == -1) {
			limit = null;
		}

		Cursor cursor = sqlDb.query(dpProp.getTableName(),
				dpProp.getCloumnNams(),
				whereSql.length() > 0 ? whereSql.toString() : null,
				whereArgArr, null, null, sortSqlStr, limit);
		List<T> listData = new ArrayList<T>();
		while (cursor.moveToNext()) {
			Object obj = SqLiteUtil.covertObj(cursor, clazz);
			listData.add(clazz.cast(obj));
		}

		cursor.close();
		return listData;
	}

	private String getSorts(Q cond) {
		StringBuffer sortSql = new StringBuffer();
		Sorts sorts = (Sorts) cond;
		String sortstr = sorts.getSorts();
		if (StringUtil.isNotBlank(sortstr)) {
			String[] sro = sortstr.split(",");
			for (String st : sro) {
				if (st.indexOf("+") >= 0) {
					sortSql.append(st.replace("+", "") + " asc,");
				} else if (st.indexOf("-") >= 0) {
					sortSql.append(st.replace("-", "") + " desc,");
				}
			}
		}

		if (sortSql.toString().length() > 0) {
			return sortSql.substring(0, sortSql.length() - 1);
		}

		return null;
	}

	

	public T get(K key) {

		SQLiteDatabase sqlDb = getReadableDatabase();

		ModelDbProp dbProp = SqLiteUtil.getModelDbProp(clazz);

		Cursor cursor = sqlDb.query(dbProp.getTableName(),
				dbProp.getCloumnNams(), dbProp.getIdColumnName() + "=?",
				new String[] { String.valueOf(key) }, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			return null;
		}

		cursor.moveToNext();

		Object obj = SqLiteUtil.covertObj(cursor, clazz);
		cursor.close();
		return clazz.cast(obj);
	}

	public int update(T model) {
		SQLiteDatabase sqlDb = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		ModelDbProp dpProp = SqLiteUtil.getModelDbProp(clazz);

		Field fieldId = null;
		Object value = null;
		try {
			fieldId = clazz.getDeclaredField(dpProp.getIdFiledName());
			fieldId.setAccessible(true);
			value = fieldId.get(model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String columnName = SqLiteUtil.getColName(fieldId,
				fieldId.getAnnotation(Column.class));

		SqLiteUtil.putAllContentValue(contentValues, clazz, model);

		return sqlDb.update(dpProp.getTableName(), contentValues, columnName
				+ "=?", new String[] { value.toString() });
	}

	public long insert(T model) {
		SQLiteDatabase sqlDb = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		Class<?> classes = model.getClass();
		SqLiteUtil.putAllContentValue(contentValues, clazz, model);
		TableName tableName = classes.getAnnotation(TableName.class);
		long row = sqlDb.insert(tableName.name(), null, contentValues);
		return row;
	}

	public long delete(K key) {

		if (key == null) {
			return -1;
		}
		SQLiteDatabase sqlDb = getWritableDatabase();
		ModelDbProp dpProp = SqLiteUtil.getModelDbProp(clazz);

		return sqlDb.delete(dpProp.getTableName(), dpProp.getIdColumnName()
				+ "=?", new String[] { key.toString() });
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		ModelDbProp dpProp = SqLiteUtil.getModelDbProp(clazz);
		String delete = "drop table  " + dpProp.getTableName();
		db.execSQL(delete);
		onCreate(db);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDb) {

		AssetManager am = getGenContext().getAssets();
		String createSql = null;
		InputStream is = null;
		try {
			is = am.open(clazz.getSimpleName() + ".sql");
			createSql = convertStreamToString(is);
			sqlDb.execSQL(createSql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {

				}
				;
			}
		}
	}

	private String convertStreamToString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = is.read();
		while (i != -1) {
			baos.write(i);
			i = is.read();
		}
		return baos.toString();
	}

	public Context getGenContext() {
		return genContext;
	}

}
