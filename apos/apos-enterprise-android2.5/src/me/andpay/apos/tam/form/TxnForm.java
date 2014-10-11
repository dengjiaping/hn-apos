package me.andpay.apos.tam.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tam.flow.model.TxnCancelFlag;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;

@FormInfo(domain = "/tam/txn.action", action = "purchase", formName = "txnForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE)
public class TxnForm implements Serializable {

	// 初始状态，交易发送中
	public static final int PRSTATUS_INTI = 0;
	// 交易已返回处理
	public static final int PRSTATUS_RESPONSE = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5278378036041113985L;

	// 状态
	/**
	 * 需要冲正标志
	 */
	private boolean needRetry;
	/**
	 * 处理状态
	 */
	private volatile int processStatus = PRSTATUS_INTI;

	/**
	 * 额外数据
	 */
    private Map<String,String> map;
    
    
	/**
	 * 销售金额
	 */
	private BigDecimal salesAmt;

	/**
	 * 订单号
	 */
	private String extTraceNo;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 交易类型
	 */
	private String txnType;

	/**
	 * 刷卡信息
	 */
	private CardInfo cardInfo;

	/**
	 * 商品照片是否需要上传
	 */
	private boolean goodsUpload;
	/**
	 * 签名照片是否需要上传
	 */
	private boolean signUplaod;
	/**
	 * 交易密码
	 */
	private String pin;

	/**
	 * 是否有磁道
	 */
	private boolean hasTrack;

	/**
	 * 卡号解析信息
	 */
	private ParseBinResponse parseBinResp;
	/**
	 * 输入pin的类型
	 */
	private int inputPinType;
	/**
	 * 销售票据号
	 */
	private String receiptNo;

	private PayTxnInfo origPayTxnInfo;
	/**
	 * 扩展类型
	 */
	private String extType;

	/**
	 * 商品文件路径
	 */
	private String goodsFileURL;
	/**
	 * 签名文件路径
	 */
	private String signFileURL;
	/**
	 * 密码错误计数器
	 */
	private int pinErrorCount;

	/**
	 * 终端流水号
	 */
	private String termTraceNo;

	/**
	 * 终端交易时间
	 */
	private String termTxnTime;

	private TxnRequest reEntryTxnRequest;

	/**
	 * 原交易编号
	 */
	private String origTxnId;

	private long timeoutTime;

	/**
	 * 云订单编号
	 */
	private String cloudOrderId;

	/**
	 * 是否是云订单
	 */
	private Boolean isCloudOrder = false;

	/**
	 * 判断此次交易是否取消
	 */
	private TxnCancelFlag txnCancelFlag = null;
	/**
	 * IC卡数据信息
	 */
	private AposICCardDataInfo aposICCardDataInfo;

	/**
	 * 交易响应
	 */
	private TxnActionResponse response = new TxnActionResponse();

	/**
	 * 数卡器mac数据
	 */
	private String mac;

	private boolean icCardTxn;

	public boolean isIcCardTxn() {
		return icCardTxn;
	}

	public void setIcCardTxn(boolean icCardTxn) {
		this.icCardTxn = icCardTxn;
	}

	public int getPinErrorCount() {
		return pinErrorCount;
	}

	public boolean getHasTrack() {
		return hasTrack;
	}

	public void setHasTrack(boolean hasTrack) {
		this.hasTrack = hasTrack;
	}

	public void setPinErrorCount(int pinErrorCount) {
		this.pinErrorCount = pinErrorCount;
	}

	public String getExtType() {
		return extType;
	}

	public void setExtType(String extType) {
		this.extType = extType;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public PayTxnInfo getOrigPayTxnInfo() {
		return origPayTxnInfo;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setOrigPayTxnInfo(PayTxnInfo origPayTxnInfo) {
		this.origPayTxnInfo = origPayTxnInfo;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	public boolean isNeedRetry() {
		return needRetry;
	}

	public void setNeedRetry(boolean needRetry) {
		this.needRetry = needRetry;
	}

	public String getExtTraceNo() {
		return extTraceNo;
	}

	public void setExtTraceNo(String extTraceNo) {
		this.extTraceNo = extTraceNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public boolean isSignUplaod() {
		return signUplaod;
	}

	public void setSignUplaod(boolean signUplaod) {
		this.signUplaod = signUplaod;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public ParseBinResponse getParseBinResp() {
		return parseBinResp;
	}

	public void setParseBinResp(ParseBinResponse parseBinResp) {
		this.parseBinResp = parseBinResp;
	}

	// public int getInputPinType() {
	// return inputPinType;
	// }

	// public void setInputPinType(int inputPinType) {
	// this.inputPinType = inputPinType;
	// }

	public TxnActionResponse getResponse() {
		return response;
	}

	public void setResponse(TxnActionResponse response) {
		this.response = response;
	}

	public String getGoodsFileURL() {
		return goodsFileURL;
	}

	public void setGoodsFileURL(String goodsFileURL) {
		this.goodsFileURL = goodsFileURL;
	}

	public String getSignFileURL() {
		return signFileURL;
	}

	public void setSignFileURL(String signFileURL) {
		this.signFileURL = signFileURL;
	}

	public String getTermTraceNo() {
		return termTraceNo;
	}

	public void setTermTraceNo(String termTraceNo) {
		this.termTraceNo = termTraceNo;
		response.setTermTraceNo(termTraceNo);
	}

	public String getTermTxnTime() {
		return termTxnTime;
	}

	public void setTermTxnTime(String termTxnTime) {
		this.termTxnTime = termTxnTime;
		response.setTermTraceNo(termTraceNo);
	}

	public TxnRequest getReEntryTxnRequest() {
		return reEntryTxnRequest;
	}

	public void setReEntryTxnRequest(TxnRequest reEntryTxnRequest) {
		this.reEntryTxnRequest = reEntryTxnRequest;
	}

	public boolean getGoodsUpload() {
		return goodsUpload;
	}

	public void setGoodsUpload(boolean goodsUpload) {
		this.goodsUpload = goodsUpload;
	}

	public String getOrigTxnId() {
		return origTxnId;
	}

	public void setOrigTxnId(String origTxnId) {
		this.origTxnId = origTxnId;
	}

	public long getTimeoutTime() {
		return timeoutTime;
	}

	public void setTimeoutTime(long timeoutTime) {
		this.timeoutTime = timeoutTime;
	}

	public String getCloudOrderId() {
		return cloudOrderId;
	}

	public void setCloudOrderId(String cloudOrderId) {
		this.cloudOrderId = cloudOrderId;
	}

	public TxnCancelFlag getTxnCancelFlag() {
		return txnCancelFlag;
	}

	public void setTxnCancelFlag(TxnCancelFlag txnCancelFlag) {
		this.txnCancelFlag = txnCancelFlag;
	}

	public Boolean getIsCloudOrder() {
		return isCloudOrder;
	}

	public void setIsCloudOrder(Boolean isCloudOrder) {
		this.isCloudOrder = isCloudOrder;
	}

	public AposICCardDataInfo getAposICCardDataInfo() {
		return aposICCardDataInfo;
	}

	public void setAposICCardDataInfo(AposICCardDataInfo aposICCardDataInfo) {
		this.aposICCardDataInfo = aposICCardDataInfo;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getInputPinType() {
		return inputPinType;
	}

	public void setInputPinType(int inputPinType) {
		this.inputPinType = inputPinType;
	}

}
