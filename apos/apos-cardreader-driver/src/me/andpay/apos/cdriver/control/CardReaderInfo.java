package me.andpay.apos.cdriver.control;

import java.io.Serializable;

/**
 * 读卡器信息
 * @author cpz
 *
 */
public class CardReaderInfo implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mName;
	  
	private String mIdentifier;
	  
	private int boundState;

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmIdentifier() {
		return mIdentifier;
	}

	public void setmIdentifier(String mIdentifier) {
		this.mIdentifier = mIdentifier;
	}

	public int getBoundState() {
		return boundState;
	}

	public void setBoundState(int boundState) {
		this.boundState = boundState;
	}
	  
	  
	  
}
