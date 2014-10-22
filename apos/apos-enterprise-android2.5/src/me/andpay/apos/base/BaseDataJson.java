package me.andpay.apos.base;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 基本数据json格式
 * 
 * @author Administrator
 *
 */
public interface BaseDataJson extends Serializable{
	/**
	 * 解析
	 * 
	 * @param jo
	 */
	public void parse(JSONObject jo);

	/**
	 * 打包
	 * 
	 * @return
	 */
	public String page();

}
