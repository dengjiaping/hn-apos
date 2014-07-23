package me.andpay.apos.vas.flow.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 开卡流程上下文
 * @author cpz
 *
 */
public class OpenCardContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406551852780573432L;
	
	
	private Long orderId;
	
	private String productType;
	
	private BigDecimal cardSalesAmt;
	
	/**
	 * 持卡人手机号
	 */
	private String phoneNo;
	
	/**
	 * 开卡数量
	 */
	private int cardQuantity;
	
	
	private String startBlankPartCardNo;

	/**
	 * 截至空白卡部分卡号
	 */
	private String endBlankPartCardNo;
	
	/**
	 * 主键编号
	 */
	private String pid;
	/**
	 * 是否包含返回按钮
	 */
	private boolean isBack;
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getCardSalesAmt() {
		return cardSalesAmt;
	}

	public void setCardSalesAmt(BigDecimal cardSalesAmt) {
		this.cardSalesAmt = cardSalesAmt;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(int cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

	public String getStartBlankPartCardNo() {
		return startBlankPartCardNo;
	}

	public void setStartBlankPartCardNo(String startBlankPartCardNo) {
		this.startBlankPartCardNo = startBlankPartCardNo;
	}

	public String getEndBlankPartCardNo() {
		return endBlankPartCardNo;
	}

	public void setEndBlankPartCardNo(String endBlankPartCardNo) {
		this.endBlankPartCardNo = endBlankPartCardNo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isBack() {
		return isBack;
	}

	public void setBack(boolean isBack) {
		this.isBack = isBack;
	}
	

}
