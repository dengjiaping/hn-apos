package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;

public class QueryICCardInfoCond extends Sorts {
	
	@Expression
	private Integer idTxn;

	public Integer getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(Integer idTxn) {
		this.idTxn = idTxn;
	}

	
	
}
