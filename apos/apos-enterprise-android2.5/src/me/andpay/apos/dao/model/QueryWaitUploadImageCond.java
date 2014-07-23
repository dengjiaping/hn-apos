package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;


public class QueryWaitUploadImageCond extends Sorts {
	
	/**
	 * 终端流水号
	 */
	@Expression
	private String termTraceNo;
	/**
	 * 终端交易时间
	 */
	@Expression
	private String termTxnTime;
	/**
	 * 创建时间
	 */
	@Expression
	private String createDate;
	
	/**
	 * 文件路径
	 */
	@Expression
	private String filePath;
	
	/**
	 * 上传文件类型
	 */
	@Expression
	private String itemType;
	
	/**
	 * 上传编号
	 */
	@Expression
	private String itemId;
	
	/**
	 * 上传次数
	 */
	@Expression
	private Integer times;
	
	
	@Expression
	private Boolean readyUpload;

	public String getTermTraceNo() {
		return termTraceNo;
	}

	public void setTermTraceNo(String termTraceNo) {
		this.termTraceNo = termTraceNo;
	}

	public String getTermTxnTime() {
		return termTxnTime;
	}

	public void setTermTxnTime(String termTxnTime) {
		this.termTxnTime = termTxnTime;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Boolean getReadyUpload() {
		return readyUpload;
	}

	public void setReadyUpload(Boolean readyUpload) {
		this.readyUpload = readyUpload;
	}
	
}
