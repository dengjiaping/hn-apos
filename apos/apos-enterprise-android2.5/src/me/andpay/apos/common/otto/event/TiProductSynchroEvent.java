package me.andpay.apos.common.otto.event;

import java.util.Date;

public class TiProductSynchroEvent {

	public static final String LASTSYNCTIMEKEY = "product_last_sync";

	private Date lastSyncTime;

	public TiProductSynchroEvent(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public Date getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

}
