package me.andpay.timobileframework.lnk;

import me.andpay.ti.lnk.transport.wsock.client.NetworkStatusChecker;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.app.Application;

import com.google.inject.Inject;

/**
 * tiLnk网络检测回调
 * @author cpz
 *
 */
public class TiNetworkStatusChecker implements NetworkStatusChecker {

	@Inject
	private Application application;

	public boolean available() {

		return NetWorkUtil.isHighNetWork(application);
	}

}
