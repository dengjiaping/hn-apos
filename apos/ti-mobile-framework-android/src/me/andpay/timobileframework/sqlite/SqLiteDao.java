package me.andpay.timobileframework.sqlite;

import java.util.List;
import me.andpay.ti.base.AppBizException;



public interface SqLiteDao<T, Q, K> {

	/**
	 * 查询列表
	 * @param cond
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws AppBizException
	 */
	public List<T> query(Q cond, long firstResult, long maxResults);
	
	/**
	 * 根据主健获取对象
	 * @param k
	 * @return
	 */
	public T get(K k);
	
	/**
	 * 更新数据
	 * @param model
	 */
	public int update(T model);
	/**
	 * 插入数据
	 * @param t
	 * @return
	 * @throws AppBizException
	 */
	public long insert(T t);	
	/**
	 * 删除数据
	 * @param k
	 * @return
	 */
	public long delete(K k);
	
	
}
