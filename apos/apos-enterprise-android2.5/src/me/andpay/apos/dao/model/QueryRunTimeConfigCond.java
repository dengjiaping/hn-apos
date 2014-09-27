package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;

public class QueryRunTimeConfigCond extends Sorts {

	/**
	 * 主键编号
	 */
	@Expression
	private Integer id;
	/**
	 * 数据
	 */
	@Expression
	private String configData;
	/**
	 * 数据名称
	 */
	@Expression
	private String dataName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigData() {
		return configData;
	}

	public void setConfigData(String configData) {
		this.configData = configData;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

}
