package me.andpay.timobileframework.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;
import me.andpay.timobileframework.sqlite.convert.DataConverter;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class SqLiteUtil {

	/**
	 * 数据模型反射信息缓存
	 */
	public static Map<String, ModelDbProp> dbPropCache = new HashMap<String, ModelDbProp>();

	public static Map<String, Method> custorTypeMethods = new HashMap<String, Method>();

	public static Map<String, DataConverter> dataConverters = new HashMap<String, DataConverter>();
	static {
		try {
			Class<?> clazz = Cursor.class;
			custorTypeMethods.put(byte[].class.getName(),
					clazz.getMethod("getBlob", int.class));
			custorTypeMethods.put(Map.class.getName(),
					clazz.getMethod("getBlob", int.class));
			custorTypeMethods.put(Set.class.getName(),
					clazz.getMethod("getBlob", int.class));
			custorTypeMethods.put(List.class.getName(),
					clazz.getMethod("getBlob", int.class));
			custorTypeMethods.put(Date.class.getName(),
					clazz.getMethod("getString", int.class));
			custorTypeMethods.put(BigDecimal.class.getName(),
					clazz.getMethod("getString", int.class));

			custorTypeMethods.put(double.class.getName(),
					clazz.getMethod("getDouble", int.class));
			custorTypeMethods.put(int.class.getName(),
					clazz.getMethod("getInt", int.class));
			custorTypeMethods.put(short.class.getName(),
					clazz.getMethod("getShort", int.class));
			custorTypeMethods.put(String.class.getName(),
					clazz.getMethod("getString", int.class));
			custorTypeMethods.put(long.class.getName(),
					clazz.getMethod("getLong", int.class));
			custorTypeMethods.put(float.class.getName(),
					clazz.getMethod("getFloat", int.class));

			custorTypeMethods.put(Byte[].class.getName(),
					clazz.getMethod("getBlob", int.class));
			custorTypeMethods.put(Double.class.getName(),
					clazz.getMethod("getDouble", int.class));
			custorTypeMethods.put(Integer.class.getName(),
					clazz.getMethod("getInt", int.class));
			custorTypeMethods.put(Short.class.getName(),
					clazz.getMethod("getShort", int.class));
			custorTypeMethods.put(Long.class.getName(),
					clazz.getMethod("getLong", int.class));
			custorTypeMethods.put(Float.class.getName(),
					clazz.getMethod("getFloat", int.class));
			custorTypeMethods.put(Boolean.class.getName(),
					clazz.getMethod("getString", int.class));
			custorTypeMethods.put(Date.class.getName(),
					clazz.getMethod("getString", int.class));
			custorTypeMethods.put(BigDecimal.class.getName(),
					clazz.getMethod("getString", int.class));

		} catch (Exception e) {
			// igore error
		}
	}

	/**
	 * 获取数据字段名字
	 * 
	 * @param field
	 * @param column
	 * @return
	 */
	public static String getColName(Field field, Column column) {

		if (StringUtil.isBlank(column.name())) {
			return field.getName();
		}
		return column.name();
	}

	public static Class<?> getRealType(Class<?> valueType) {
		if (valueType.equals(byte[].class) || valueType.equals(Byte[].class)
				|| valueType.equals(Map.class) || valueType.equals(Set.class)
				|| valueType.equals(List.class)) {
			return byte[].class;
		}

		return valueType;
	}

	public static void putContentValues(ContentValues values, String key,
			Object value, Class<?> valueType) {
		valueType = getRealType(valueType);
		if (valueType.equals(byte[].class)) {
			values.put(key, (byte[]) value);
			return;
		}

		if (valueType.equals(boolean.class) || valueType.equals(Boolean.class)) {
			values.put(key, (Boolean) value);
		}

		values.put(key, value.toString());

	}

	/**
	 * 获得数据库模型
	 * @param modelClass 
	 * @return
	 */
	public static ModelDbProp getModelDbProp(Class<?> modelClass) {

		ModelDbProp dpPorp = dbPropCache.get(modelClass.getName());

		if (dpPorp != null) {
			return dpPorp;
		}
		dpPorp = new ModelDbProp();
		dpPorp.setModelClass(modelClass);
		Map<String, Method> setMethods = dpPorp.getSetMethods();

		TableName tableName = modelClass.getAnnotation(TableName.class);
		dpPorp.setTableName(tableName.name());

		List<String> colList = new ArrayList<String>();

		for (Field field : modelClass.getDeclaredFields()) {
			Column col = field.getAnnotation(Column.class);
			if (col == null) {
				continue;
			}
			String colName = SqLiteUtil.getColName(field, col);
			colList.add(colName);

			dpPorp.getFields().put(colName, field);

			String setName = null;
			if (Character.isUpperCase(field.getName().charAt(1))) {
				setName = "set" + field.getName();
			} else {
				setName = "set" + field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
			}

			Method setMethod = null;
			try {
				setMethod = modelClass.getMethod(setName, field.getType());
			} catch (NoSuchMethodException e) {

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			setMethods.put(colName, setMethod);
			/**
			 * 获得主键
			 */
			ID id = field.getAnnotation(ID.class);
			if (id != null) {
				dpPorp.setIdFiledName(field.getName());
				dpPorp.setIdColumnName(colName);
			}
		}
		dpPorp.setCloumnNams(colList.toArray(new String[colList.size()]));

		return dpPorp;
	}

	@SuppressWarnings("unchecked")
	public static Object covertObj(Cursor cursor, Class<?> clazz) {

		String[] columnNames = cursor.getColumnNames();
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		ModelDbProp dpProp = getModelDbProp(clazz);

		for (String colName : columnNames) {
			Method method = dpProp.getSetMethods().get(colName);

			Field field = dpProp.getFields().get(colName);
			Column col = field.getAnnotation(Column.class);

			String tpyeName = method.getParameterTypes()[0].getName();
			Method curMethod = custorTypeMethods.get(tpyeName);
			try {
				if (curMethod == null) {
					Log.e("sqlLite Util", "the null curmethod, type is "
							+ tpyeName);
				}
				Object value = curMethod.invoke(cursor,
						cursor.getColumnIndex(colName));

				if (value != null) {

					DataConverter dataConverter = getDataConverter(col, clazz,
							field);
					if (dataConverter != null) {
						value = dataConverter.convertToObj(value);
					}

					if (tpyeName.equals(Boolean.class.getName())
							|| tpyeName.equals(boolean.class.getName())) {
						if (value.equals("true")) {
							method.invoke(obj, true);
						} else {
							method.invoke(obj, false);
						}
					} else {
						value = jsonDataConvertObject(value, tpyeName, dpProp
								.getModelClass().getDeclaredField(colName)
								.getGenericType());
						method.invoke(obj, value);
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public static void putAllContentValue(ContentValues contentValues,
			Class<?> clazz, Object model) {
		for (Field field : clazz.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				field.setAccessible(true);
				String colName = SqLiteUtil.getColName(field, column);
				Object obj = null;
				try {
					obj = field.get(model);
					if (column.dataConverter() != null) {
						DataConverter dataConverter = getDataConverter(column,
								clazz, field);
						if (dataConverter != null) {
							obj = dataConverter.convertToString(obj);
						}
					}
					obj = objectConvertJsonData(obj);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				if (obj != null) {
					SqLiteUtil.putContentValues(contentValues, colName, obj,
							field.getType());
				}
			}
		}
	}

	private static DataConverter getDataConverter(Column column,
			Class<?> clazz, Field field) throws InstantiationException,
			IllegalAccessException {
		if (column.dataConverter().isInterface()) {
			return null;
		}
		String key = clazz.getName() + "." + field.getName();
		DataConverter dataConverter = dataConverters.get(key);
		if (dataConverter == null) {
			dataConverter = column.dataConverter().newInstance();
			dataConverters.put(key, dataConverter);
		}
		return dataConverter;
	}

	/**
	 * 集合对象转换为json数据入库
	 * 
	 * @param obj
	 * @return
	 */
	public static Object objectConvertJsonData(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Map || obj instanceof Set || obj instanceof List) {
			byte[] jsonData = JacksonSerializer.newPrettySerializer()
					.serialize(obj);
			return jsonData;
		}

		return obj;
	}

	public static Object jsonDataConvertObject(Object obj, String tpyeName,
			Type type) {
		if (obj == null) {
			return null;
		}
		if (tpyeName.equals(Map.class.getName())) {
			return JacksonSerializer.newPrettySerializer().deserialize(type,
					(byte[]) obj);
		} else if (tpyeName.equals(Set.class.getName())) {
			return JacksonSerializer.newPrettySerializer().deserialize(type,
					(byte[]) obj);
		} else if (tpyeName.equals(List.class.getName())) {
			return JacksonSerializer.newPrettySerializer().deserialize(type,
					(byte[]) obj);

		}
		return obj;
	}

	public static String getFieldName(Field field, String colFieldName,
			Class<?> modelClass) {

		String fieldName = colFieldName;
		if (StringUtil.isBlank(fieldName)) {
			fieldName = field.getName();
		}
		try {
			Field modelField = modelClass.getDeclaredField(fieldName);
			Column col = modelField.getAnnotation(Column.class);

			if (col != null && !StringUtil.isBlank(col.name())) {
				return col.name();
			}
			return fieldName;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
