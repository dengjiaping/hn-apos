package me.andpay.apos.dao.model;

import me.andpay.apos.base.upimage.UploadImageCallback;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

/**
 * 等待上传的图片
 * 
 * @author cpz
 *
 */
@TableName(name = "WaitUploadImage", version = 2)
public class WaitUploadImage {

	
	private UploadImageCallback callback;
	
	
	
	public UploadImageCallback getCallback() {
		return callback;
	}

	public void setCallback(UploadImageCallback callback) {
		this.callback = callback;
	}

	/**
	 * 主键编号
	 */
	@ID
	@Column
	private Integer id;

	/**
	 * 终端流水号
	 */
	@Column
	private String termTraceNo;
	/**
	 * 终端交易时间
	 */
	@Column
	private String termTxnTime;
	/**
	 * 创建时间
	 */
	@Column
	private String createDate;

	/**
	 * 文件路径
	 */
	@Column
	private String filePath;

	/**
	 * 上传文件类型
	 */
	@Column
	private String itemType;

	/**
	 * 上传编号
	 */
	@Column
	private String itemId;

	/**
	 * 上传次数
	 */
	@Column
	private Integer times;
	/**
	 * 准备文件上传
	 */
	@Column
	private Boolean readyUpload;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getReadyUpload() {
		return readyUpload;
	}

	public void setReadyUpload(Boolean readyUpload) {
		this.readyUpload = readyUpload;
	}

}
