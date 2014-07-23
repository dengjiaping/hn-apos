package me.andpay.timobileframework.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 模型数据库属性
 * @author cpz
 *
 */
public class ModelDbProp {
	
	/**
	 * id列名
	 */
	private String idColumnName;
	
	/**
	 * 所有列名
	 */
	private String[] cloumnNams;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 主健列名
	 */
	private String idFiledName;
	
	private Map<String,Method> setMethods= new HashMap<String,Method>();
	
	private Map<String,Field>  fields = new HashMap<String,Field>();
	
	private Class<?> modelClass;

	public Map<String, Method> getSetMethods() {
		return setMethods;
	}

	public void setSetMethods(Map<String, Method> setMethods) {
		this.setMethods = setMethods;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}

	public String[] getCloumnNams() {
		return cloumnNams;
	}

	public void setCloumnNams(String[] cloumnNams) {
		this.cloumnNams = cloumnNams;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdFiledName() {
		return idFiledName;
	}

	public void setIdFiledName(String idFiledName) {
		this.idFiledName = idFiledName;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public Map<String, Field> getFields() {
		return fields;
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}
	
	
	
	
}
