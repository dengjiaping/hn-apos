package me.andpay.apos.opm.form;

import me.andpay.ac.term.api.txn.order.QueryOrderRecordCond;
import me.andpay.apos.dao.model.OrderInfoCond;

/**
 * 查询对象转换结果
 * 
 * @author cpz
 *
 */
public class OrderCondResult {
	/**
	 * 本地查询对象
	 */
	private OrderInfoCond queryInfoCond;
	/**
	 * 远端查询对象
	 */
	private QueryOrderRecordCond queryOrderRecordCond;

	private long firstResult;

	private long maxResults;

	public OrderInfoCond getQueryInfoCond() {
		if (queryInfoCond == null) {
			queryInfoCond = new OrderInfoCond();
		}
		return queryInfoCond;
	}

	public void setQueryInfoCond(OrderInfoCond queryInfoCond) {
		this.queryInfoCond = queryInfoCond;
	}

	public QueryOrderRecordCond getQueryOrderRecordCond() {
		if (queryOrderRecordCond == null) {
			queryOrderRecordCond = new QueryOrderRecordCond();
		}
		return queryOrderRecordCond;
	}

	public void setQueryOrderRecordCond(
			QueryOrderRecordCond queryOrderRecordCond) {
		this.queryOrderRecordCond = queryOrderRecordCond;
	}

	public long getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(long firstResult) {
		this.firstResult = firstResult;
	}

	public long getMaxResults() {
		if (maxResults == 0) {
			return 10;
		}
		return maxResults;
	}

	public Integer getIntegerMaxResults() {

		if (maxResults == 0) {
			return 20;
		}
		return Integer.valueOf(String.valueOf(maxResults));

	}

	public void setMaxResults(long maxResults) {
		this.maxResults = maxResults;
	}
}
