package me.andpay.apos.tam.flow.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.vas.spcart.ShoppingCart;
import me.andpay.ti.util.StringUtil;

/**
 * 交易流程上下文
 * 
 * @author cpz
 */
public class TxnContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易超时时间
	 */
	public static final int TXN_TIMEOUT_TIME = 60000;

	/**
	 * 交易处理流程状态
	 */
	private String txnStatus = TxnStatus.INIT;
	/**
	 * 交易类型
	 */
	private String txnType;

	/**
	 * 刷卡信息
	 */
	private CardInfo cardInfo;

	/**
	 * 卡号解析信息
	 */
	private ParseBinResponse parseBinResp;

	/**
	 * 金额格式化形式
	 */
	private String amtFomat;
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
	 * 商品文件路径
	 */
	private String goodsFileURL;
	/**
	 * 签名文件路径
	 */
	private String signFileURL;

	/**
	 * 回调activity
	 */
	private String backTagName;

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
	 * 密码错误计数器
	 */
	private int pinErrorCount;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 原交易
	 */
	private PayTxnInfo origPayTxnInfo;
	/**
	 * 原交易编号
	 */
	private String origTxnId;

	/**
	 * 是否需要输入密码
	 */
	private boolean needPin;

	/**
	 * 销售票据号
	 */
	private String receiptNo;

	/**
	 * 重发凭证标志
	 */
	private boolean rePostFlag;
	/**
	 * 购物车
	 */
	private ShoppingCart shoppingCart;

	/**
	 * 是否恢复标志
	 */
	public boolean recover;

	/**
	 * 扩展类型
	 */
	private String extType;

	/**
	 * 是否有新交易按钮
	 */
	private boolean hasNewTxnButton;

	private Long orderId;

	private TxnActionResponse txnActionResponse;
	/**
	 * 是否是云订单
	 */
	private boolean isCloudOrder = false;

	/**
	 * 云订单编号
	 */
	private String cloudOrderId;
	/**
	 * 判断此次交易是否取消
	 */
	private TxnCancelFlag txnCancelFlag = null;

	/**
	 * 操作跟踪编号
	 */
	private String opTraceNo;
	/**
	 * IC卡详细信息
	 */
	private AposICCardDataInfo aposICCardDataInfo;

	/**
	 * 是否是IC卡交易
	 */
	private boolean icCardTxn;
	
	/**
	 * mac数据
	 */
	private String mac;
	
	

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public TxnActionResponse getTxnActionResponse() {
		return txnActionResponse;
	}

	public void setTxnActionResponse(TxnActionResponse txnActionResponse) {
		this.txnActionResponse = txnActionResponse;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public boolean isNeedPin() {
		return needPin;
	}

	public void setNeedPin(boolean needPin) {
		this.needPin = needPin;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public CardInfo getCardInfo() {
		if (cardInfo == null) {
			cardInfo = new CardInfo();
		}
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public ParseBinResponse getParseBinResp() {
		if (parseBinResp == null) {
			parseBinResp = new ParseBinResponse();
		}
		return parseBinResp;
	}

	public void setParseBinResp(ParseBinResponse parseBinResp) {
		this.parseBinResp = parseBinResp;
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

	public BigDecimal getSalesAmt() {
		if (salesAmt != null) {
			return salesAmt;
		}

		if (StringUtil.isNotBlank(amtFomat)) {
			NumberFormat format = NumberFormat
					.getCurrencyInstance(Locale.CHINA);
			Number amt = null;
			try {
				amt = format.parse(amtFomat);
				return new BigDecimal(amt.toString());
			} catch (ParseException e) {

			}
		}
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getAmtFomat() {
		// if (StringUtil.isNotBlank(amtFomat)) {
		// return amtFomat;
		// }
		if (salesAmt != null) {
			NumberFormat format = NumberFormat
					.getCurrencyInstance(Locale.CHINA);
			return format.format(salesAmt.doubleValue());
		}
		return amtFomat;
	}

	public void setAmtFomat(String amtFomat) {
		this.amtFomat = amtFomat;
	}

	public int getPinErrorCount() {
		return pinErrorCount;
	}

	public void setPinErrorCount(int pinErrorCount) {
		this.pinErrorCount = pinErrorCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBackTagName() {
		return backTagName;
	}

	public void setBackTagName(String backTagName) {
		this.backTagName = backTagName;
	}

	public void setGoodsUpload(boolean goodsUpload) {
		this.goodsUpload = goodsUpload;
	}

	public boolean isRecover() {
		return recover;
	}

	public void setRecover(boolean recover) {
		this.recover = recover;
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

	public boolean isRePostFlag() {
		return rePostFlag;
	}

	public void setRePostFlag(boolean rePostFlag) {
		this.rePostFlag = rePostFlag;
	}

	public boolean isHasNewTxnButton() {
		return hasNewTxnButton;
	}

	public void setHasNewTxnButton(boolean hasNewTxnButton) {
		this.hasNewTxnButton = hasNewTxnButton;
	}

	public PayTxnInfo getOrigPayTxnInfo() {
		return origPayTxnInfo;
	}

	public void setOrigPayTxnInfo(PayTxnInfo origPayTxnInfo) {
		this.origPayTxnInfo = origPayTxnInfo;
		origTxnId = origPayTxnInfo.getTxnId();
	}

	public String getOrigTxnId() {
		return origTxnId;
	}

	public void setOrigTxnId(String origTxnId) {
		this.origTxnId = origTxnId;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public boolean isGoodsUpload() {
		return goodsUpload;
	}

	public boolean isSignUplaod() {
		return signUplaod;
	}

	public void setSignUplaod(boolean signUplaod) {
		this.signUplaod = signUplaod;
	}

	

	public void setHasTrack(boolean hasTrack) {
		this.hasTrack = hasTrack;
	}

	public String getCloudOrderId() {
		return cloudOrderId;
	}

	public void setCloudOrderId(String cloudOrderId) {
		this.cloudOrderId = cloudOrderId;
	}

	public boolean isCloudOrder() {
		return isCloudOrder;
	}

	public void setCloudOrder(boolean isCloudOrder) {
		this.isCloudOrder = isCloudOrder;
	}

	public TxnCancelFlag getTxnCancelFlag() {
		return txnCancelFlag;
	}

	public void setTxnCancelFlag(TxnCancelFlag txnCancelFlag) {
		this.txnCancelFlag = txnCancelFlag;
	}

	
	public String getOpTraceNo() {
		return opTraceNo;
	}

	public void setOpTraceNo(String opTraceNo) {
		this.opTraceNo = opTraceNo;
	}

	public AposICCardDataInfo getAposICCardDataInfo() {
		return aposICCardDataInfo;
	}

	public void setAposICCardDataInfo(AposICCardDataInfo aposICCardDataInfo) {
		this.aposICCardDataInfo = aposICCardDataInfo;
	}

	public boolean isIcCardTxn() {
		return icCardTxn;
	}

	public void setIcCardTxn(boolean icCardTxn) {
		this.icCardTxn = icCardTxn;
	}

	public boolean isHasTrack() {
		return hasTrack;
	}
	
	

}
