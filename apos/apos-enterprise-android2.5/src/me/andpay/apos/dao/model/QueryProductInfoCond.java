package me.andpay.apos.dao.model;

import java.util.Date;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;
import me.andpay.timobileframework.sqlite.convert.DateConverter;

public class QueryProductInfoCond extends Sorts {
	/**
	 * 产品唯一编号
	 */
	@Expression
	private Long productId;

	/**
	 * 商户参与者编号
	 */
	@Expression
	private String merchPartyId;
	

//	/**
//	 * 名称
//	 */
//	@Expression(operater = "like", logicSymbol = "or")
//	private String name;
//
//
//	@Expression(operater = "like", logicSymbol = "or")
//	private String namePinyin;
	
	
    @Expression(paraName = "namePinyin", sqlformat="( namePinyin like '%${value}%' or name  like '%${value}%')")
	private String nameLike;

	@Expression
	private String status;

	@Expression(dataConverter = DateConverter.class)
	private Date updataTime;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}



	public Date getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(Date updataTime) {
		this.updataTime = updataTime;
	}

	public String getMerchPartyId() {
		return merchPartyId;
	}

	public void setMerchPartyId(String merchPartyId) {
		this.merchPartyId = merchPartyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	
	
}
