package me.andpay.apos.lam.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.ac.term.api.auth.Party;
import me.andpay.ac.term.api.auth.TermAuthService;
import me.andpay.ac.term.api.sec.TermCert;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.apos.common.service.ProductSynchroner;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.lam.callback.ActivateCodeCallback;
import me.andpay.apos.lam.form.ActivateCodeForm;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.s3.client.GenKeyAndCsrRequest;
import me.andpay.ti.s3.client.KeyAndCert;
import me.andpay.ti.s3.client.KeyAndCsr;
import me.andpay.ti.s3.client.PkcsClient;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;
import me.andpay.timobileframework.mvc.context.TiContext;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;

@ActionMapping(domain = ActivateCodeAction.DOMAIN_NAME)
public class ActivateCodeAction extends MultiAction {

	public final static String DOMAIN_NAME = "/lam/activte.action";

	public final static String SEDN_ACIVATE_CODE = "sendActivateCode";

	@Inject
	private Application application;

	@Inject
	private ProductSynchroner productSynchroner;

	private TermAuthService termAuthService;

	private TiContext tiContext = null;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private DynamicFieldHelper dynamicFieldHelper;

	@Inject
	private ClientInitHelper clientInitHelper;

	public ModelAndView sendActivateCode(ActionRequest request) {
		tiContext = request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);

		LoginResponse loginResponse =(LoginResponse) tiContext.getAttribute(RuntimeAttrNames.LOGIN_RESPONSE);
		Party party = loginResponse.getParties().get(0);
		try {
			termAuthService.sendTermActCode(party.getPartyId());
		} catch (AppBizException e) {
			return null;
		}

		return null;
	}

	/**
	 * 签证书
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView signCertWithActivateCode(ActionRequest request) {
		tiContext = request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);

		ActivateCodeCallback activateCodeCallback = (ActivateCodeCallback) request
				.getHandler();
		String keystorePath = null;
		try {
			ActivateCodeForm activateCodeForm = (ActivateCodeForm) request
					.getParameterValue("activateCodeForm");
			DeviceInfo deviceInfo = getDeviceInfo();
			LoginResponse loginResponse =(LoginResponse) tiContext.getAttribute(RuntimeAttrNames.LOGIN_RESPONSE);
			Party party = loginResponse.getParties().get(0);
			
			GenKeyAndCsrRequest genKeyAndCsrRequest = createKeyAndCsrRequest(
					party.getPartyId(), deviceInfo.getDeviceId());
			KeyAndCsr keyAndCsr = PkcsClient.genKeyAndCsr(genKeyAndCsrRequest);
			TermCert signCert = termAuthService.signTermCertAsJKS(
					keyAndCsr.getCsrData(), party.getPartyId(),
					activateCodeForm.getActivateCode());
			KeyAndCert keyAndCert = new KeyAndCert();
			keyAndCert.setKeyPair(keyAndCsr.getKeyPair());
			keyAndCert.setCertType(signCert.getCertType());
			keyAndCert.setCertData(signCert.getCertData());

			keystorePath = getKeyPath(party.getPartyId());
			createFile(getFileName(party.getPartyId()));
			PkcsClient
					.storeKeyAndCert(keyAndCert, keystorePath,
							CommonProvider.CERT_ALIAS,
							deviceInfo.getKeyStorePassword());
			clientInitHelper.configClient(deviceInfo.getKeyStorePassword(),
					party.getPartyId());
			productSynchroner.sync(application, true);
			activateCodeCallback.activateSuccess();
		} catch (AppBizException e) {
			activateCodeCallback.activateFaild(e.getLocalizedMessage());
			Log.e(this.getClass().getName(), "activate error", e);
		} catch (Exception e) {

			if (keystorePath != null) {
				File file = new File(keystorePath);
				if (file.exists()) {
					file.delete();
				}
			}
			if (!ErrorMsgUtil.isNetworkError(e)) {
				Crashlytics.log("activate Code error");
				Crashlytics.logException(e);
			}

			activateCodeCallback.activateFaild(ErrorMsgUtil.parseError(
					application, e));
			Log.e(this.getClass().getName(), "activate error", e);
		}

		return null;
	}

	/**
	 * 系统路径创建文件
	 * 
	 * @param fileName
	 */
	private void createFile(String fileName) {
		FileOutputStream fileOut = null;
		try {
			// 创建新文件
			fileOut = application
					.openFileOutput(fileName, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// ignore
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private DeviceInfo getDeviceInfo() {

		DeviceInfo deviceInfo = (DeviceInfo) tiContext
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		return deviceInfo;
	}

	private String getKeyPath(String partyId) {

		return application.getFilesDir().getAbsolutePath() + "/"
				+ getFileName(partyId);
	}

	private String getFileName(String partyId) {
		DeviceInfo deviceInfo = (DeviceInfo) tiContext
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		String fileName = deviceInfo.getAppCode() + "_" + partyId + "_"
				+ CommonProvider.BKS_NAME;
		return fileName;
	}

	private GenKeyAndCsrRequest createKeyAndCsrRequest(String partyId,
			String deviceId) {
		GenKeyAndCsrRequest genKeyAndCsrRequest = new GenKeyAndCsrRequest();
		genKeyAndCsrRequest.setCommonName(partyId + "." + deviceId);
		genKeyAndCsrRequest.setCountryCode("CN");
		genKeyAndCsrRequest.setCityName("SH");
		genKeyAndCsrRequest.setKeySize(2048);
		genKeyAndCsrRequest.setOrganizationalName("ANDPAY");
		genKeyAndCsrRequest.setOrganizationCode("ANDPAY");
		genKeyAndCsrRequest.setProvinceName("SH");
		return genKeyAndCsrRequest;
	}

}
