package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

@TableName(name = "TxnConfirm")
public class TxnConfirm {

	/**
	 * 主键
	 */
	@ID
	@Column
	private Integer id;
	/**
	 * 交易编号
	 */
	@Column
	private String txnId;
	/**
	 * 更新时间
	 */
	@Column
	private String updateTime;
	/**
	 * 重试次数
	 */
	@Column
	private Integer retryCount;
	/**
	 * 创建时间
	 */
	@Column
	private String createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

}
