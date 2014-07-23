package me.andpay.apos.lam.action;

import java.util.List;

import me.andpay.ac.term.api.auth.Party;
import me.andpay.ac.term.api.auth.PartyAuthService;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.ac.term.api.cif.PartyInfoService;
import me.andpay.ac.term.api.sec.PublicKey;
import me.andpay.ac.term.api.sec.TermSecurityService;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.log.AposDebugLog;
import me.andpay.apos.common.log.AposOperationLog;
import me.andpay.apos.common.service.CleanDataService;
import me.andpay.apos.common.service.ProductSynchroner;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.lnk.TiRpcClient;
import android.app.Application;

import com.google.inject.Inject;

public class ClientInitHelper {

	private PartyAuthService partyAuthService;

	private PartyInfoService partyInfoService;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	Application application;

	@Inject
	private DynamicFieldHelper dynamicFieldHelper;

	@Inject
	private ProductSynchroner productSynchroner;

	@Inject
	private CleanDataService clearCleanPayTxnInfoService;

	@Inject
	private AposDebugLog aposDebugLog;

	private TermSecurityService termSecurityService;

	@Inject
	private AposContext aposContext;

	public void configClient(String password, String partyId)
			throws AppBizException {

		// 初始化日志
		aposDebugLog.init();
		AposOperationLog.init(application.getApplicationContext());
		openSSL(partyId, password);

		bindParty(partyId);

		// 动态字段
		List<FlexFieldDefine> defineList = partyInfoService
				.getFlexFieldDefines();
		aposContext.getAppContext().setAttribute(RuntimeAttrNames.FIELD_DEFINE,
				defineList);
		dynamicFieldHelper.setFieldDefines(defineList);
		// party 设置公共密码
		setpublicKey(termSecurityService.getTxnPinPublicKey());
	}

	private void setpublicKey(PublicKey txnPinKey) {
		aposContext.getAppContext().setAttribute(
				RuntimeAttrNames.PIN_PUBLIC_KEY, txnPinKey);

	}

	/**
	 * 选择商户 以后有用户选择
	 * 
	 * @param response
	 * @throws AppBizException
	 */
	private PartyInfo bindParty(String partyId) throws AppBizException {

		Party party = partyAuthService.bindTxnParty(partyId);
		return partyInfoInit(party);

	}

	private PartyInfo partyInfoInit(Party party) {

		// 设置刷卡器配合
		// List<MsrQuota> mrsList = partyInfoService.getMsrQuotas();
		PartyInfo partyInfo = new PartyInfo();

		partyInfo.setPartyId(party.getPartyId());
		partyInfo.setPartyName(party.getPartyName());
		partyInfo.setRoles(party.getRoles());
		partyInfo.setPrivileges(party.getPrivileges());
		partyInfo.setExtFuncConfigs(party.getExtFuncConfigs());
		// if(mrsList==null ||mrsList.isEmpty()) {
		// Set<String> msrTypes = new HashSet<String>();
		// msrTypes.add("1");
		// msrTypes.add("2");
		// msrTypes.add("3");
		// msrTypes.add("4");
		// msrTypes.add("5");
		// msrTypes.add("6");
		// partyInfo.setMsrTypes(msrTypes);
		// }else {
		// partyInfo.setMsrTypes(party.getMsrTypes());
		// }
		aposContext.getAppContext().setAttribute(RuntimeAttrNames.PARTY_INFO,
				partyInfo);
		return partyInfo;
	}

	private void openSSL(String partyId, String password) {
		tiRpcClient.configSSL(getKeyPath(partyId), password, password);
	}

	private String getKeyPath(String partyId) {
		return application.getFilesDir().getAbsolutePath() + "/"
				+ getFileName(partyId);
	}

	private String getFileName(String partyId) {
		DeviceInfo deviceInfo = (DeviceInfo) aposContext.getAppContext()
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		String fileName = deviceInfo.getAppCode() + "_" + partyId + "_"
				+ CommonProvider.BKS_NAME;
		return fileName;
	}
}
