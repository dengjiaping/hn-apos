package me.andpay.timobileframework.mvc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

	private Map<String, Object> models = new HashMap<String, Object>();

	private String domain = null;

	private String action = null;

	private String view = null;

	/**
	 * 框架保留字段，用于判断异常，代码中不许使用
	 */
	private Exception happedEx;

	public ModelAndView() {

	}

	public ModelAndView(String domain, String action, String view) {
		this.domain = domain;
		this.action = action;
		this.view = view;
	}

	public ModelAndView addModelValue(String key, Object value) {
		models.put(key, value);
		return this;
	}

	public ModelAndView addModelValue(Map<String, Object> datas) {
		models.putAll(datas);
		return this;
	}

	public Object getValue(String key) {
		return this.models.get(key);
	}

	public <T> T getValue(String key, Class<T> classObj) {
		return classObj.cast(models.get(key));
	}

	public ModelAndView domain(String domain) {
		this.domain = domain;
		return this;
	}

	public ModelAndView action(String action) {
		this.action = action;
		return this;
	}

	public ModelAndView view(String view) {
		this.view = view;
		return this;
	}

	public Exception getHappedEx() {
		return happedEx;
	}

	public void setHappedEx(Exception happedEx) {
		this.happedEx = happedEx;
	}

	public Map<String, Object> getModels() {
		return models;
	}

	public String getDomain() {
		return domain;
	}

	public String getAction() {
		return action;
	}

	public String getView() {
		return view;
	}

	public static void main(String[] args) {
		Date d1 = new Date();
		Date d2 = new Date();
		String test = "test";
		// System.out.println(String.format("交易时间：%1$tY-%1$tm-%1$td", d1));
		System.out
				.println(String
						.format("交易时间：%1$tY-%1$tM-%1$td %1$tH:%1$tM 至 %2$tY-%2$tm-%2$td %2$tH:%2$tM",
								d1, d2));
		System.out.println(String.format("结算编号：%1$", test));

	}

}
