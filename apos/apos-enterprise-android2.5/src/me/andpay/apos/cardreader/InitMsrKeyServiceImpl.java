package me.andpay.apos.cardreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.andpay.ac.term.api.sec.MsrKey;
import me.andpay.ac.term.api.sec.MsrKeyTypes;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.InitCardReaderService;
import me.andpay.apos.cdriver.InitIcCardResult;
import me.andpay.apos.cdriver.InitMsrKeyResult;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.service.DownloadICCardParamsService;
import me.andpay.apos.lam.action.GenMsrKeysAction;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AndroidEventRequest;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import me.andpay.timobileframework.util.HexUtils;
import android.util.Log;

import com.google.inject.Inject;

public class InitMsrKeyServiceImpl implements InitCardReaderService {

	private static final String TAG = InitCardReaderService.class.getName();

	@Inject
	private AposContext aposContext;

	@Inject
	private TiApplication tiApplication;

	@Inject
	private DispatchCenter center;

	@Inject
	private DownloadICCardParamsService downloadICCardParamsService;

	private boolean hasMacKey(String version) {
		if (StringUtil.isBlank(version)) {
			return false;
		}

		if (CardReaderTypes.NEW_LAND_BL == CardReaderManager
				.getCardReaderType() && "1.41".compareTo(version) < 0) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param tiActivity
	 * @return true 初始化成功 false 初始化失败
	 */
	public InitMsrKeyResult initMsrKey(String identifier) {
		InitMsrKeyResult initMsrKeyResult = new InitMsrKeyResult();
		// if
		// (CardReaderInitManager.checkAllKeyIsInit(aposContext.getAppConfig(),
		// identifier)) {
		// initMsrKeyResult.setSuccess(true);
		// return initMsrKeyResult;
		// }

		DeviceInfo deviceInfo = CardReaderManager.getDeviceInfo();
		if (deviceInfo == null
				|| me.andpay.ti.util.StringUtil.isBlank(deviceInfo.getKsn())) {
			initMsrKeyResult.setErrorMsg("无法获取设备信息,请确认设备正常后重试。");
			return initMsrKeyResult;
		}

		if (!hasMacKey(deviceInfo.getAppVersion())) {
			deviceInfo.setInitMacKey(true);
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.MAC_KEY);
		}

		Set<String> keyTypes = createKeyTypes(deviceInfo);

		if (keyTypes.size() == 0) {
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.MASTER_KEY);
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.PIN_KEY);
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.DATA_KEY);
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.MAC_KEY);
			initMsrKeyResult.setSuccess(true);
			return initMsrKeyResult;
		}
		if (!keyTypes.contains(MsrKeyTypes.MASTER_KEY)) {
			CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
					identifier, MsrKeyTypes.MASTER_KEY);
		}

		EventRequest request = new AndroidEventRequest(center,
				tiApplication.getContextProvider());
		request.open(GenMsrKeysAction.DOMAIN_NAME,
				GenMsrKeysAction.GEN_MSRKEYS,
				me.andpay.timobileframework.mvc.EventRequest.Pattern.sync);
		Map<String, Object> submitData = new HashMap<String, Object>();
		submitData.put("ksn", deviceInfo.getKsn());
		submitData.put("keyTypes", keyTypes);

		ModelAndView modelAndView = request.submit(submitData);

		Object keyObj = modelAndView.getValue("mrsKeys");
		if (keyObj != null) {
			List<MsrKey> mrsKeys = (ArrayList<MsrKey>) keyObj;

			if (!loadMastKey(keyTypes, mrsKeys, aposContext.getAppConfig(),
					identifier)) {
				initMsrKeyResult.setErrorMsg("初始化主密钥失败,请联系和付。");
				return initMsrKeyResult;
			}
			if (!loadOtherKeys(keyTypes, mrsKeys, aposContext.getAppConfig(),
					identifier)) {
				initMsrKeyResult.setErrorMsg("初始化工作密钥失败,请联系和付。");
				return initMsrKeyResult;
			}
		} else {
			initMsrKeyResult.setErrorMsg((String) modelAndView
					.getValue("errorMsg"));
			return initMsrKeyResult;
		}

		initMsrKeyResult.setSuccess(true);
		return initMsrKeyResult;
	}

	private boolean loadOtherKeys(Set<String> keyTypes, List<MsrKey> mrsKeys,
			TiContext ticonfig, String identifier) {
		for (MsrKey msrKey : mrsKeys) {

			if (msrKey.getMsrKeyType().equals(MsrKeyTypes.DATA_KEY)) {

				Log.e(TAG,
						"磁道密钥：key="
								+ HexUtils.bytesToHexString(msrKey.getKeyData())
								+ "cv="
								+ HexUtils.bytesToHexString(msrKey.getCv()));
				if (CardReaderManager.loadKey(MsrKeyTypes.DATA_KEY,
						msrKey.getKeyData(), msrKey.getCv())) {
					CardReaderInitManager.setParamInit(ticonfig, identifier,
							MsrKeyTypes.DATA_KEY);
				} else {
					return false;
				}
			}
			if (msrKey.getMsrKeyType().equals(MsrKeyTypes.PIN_KEY)) {
				if (CardReaderManager.loadKey(MsrKeyTypes.PIN_KEY,
						msrKey.getKeyData(), msrKey.getCv())) {
					CardReaderInitManager.setParamInit(ticonfig, identifier,
							MsrKeyTypes.PIN_KEY);
					// Log.e(TAG,
					// "密码密钥：key="
					// + HexUtils.bytesToHexString(msrKey
					// .getKeyData()) + "cv="
					// + HexUtils.bytesToHexString(msrKey.getCv()));

				} else {
					return false;
				}
			}

			if (msrKey.getMsrKeyType().equals(MsrKeyTypes.MAC_KEY)) {
				if (CardReaderManager.loadKey(MsrKeyTypes.MAC_KEY,
						msrKey.getKeyData(), msrKey.getCv())) {
					CardReaderInitManager.setParamInit(ticonfig, identifier,
							MsrKeyTypes.MAC_KEY);
					// Log.e(TAG,
					// "密码密钥：key="
					// + HexUtils.bytesToHexString(msrKey
					// .getKeyData()) + "cv="
					// + HexUtils.bytesToHexString(msrKey.getCv()));

				} else {
					return false;
				}
			}
		}

		return true;
	}

	private boolean loadMastKey(Set<String> keyTypes, List<MsrKey> mrsKeys,
			TiContext ticonfig, String identifier) {
		if (keyTypes.contains(MsrKeyTypes.MASTER_KEY)) {

			for (MsrKey msrKey : mrsKeys) {
				if (msrKey.getMsrKeyType().equals(MsrKeyTypes.MASTER_KEY)) {

					Log.e(TAG,
							"主密钥：key="
									+ HexUtils.bytesToHexString(msrKey
											.getKeyData()) + "cv="
									+ HexUtils.bytesToHexString(msrKey.getCv()));

					if (CardReaderManager.loadKey(MsrKeyTypes.MASTER_KEY,
							msrKey.getKeyData(), msrKey.getCv())) {
						CardReaderInitManager.setParamInit(ticonfig,
								identifier, MsrKeyTypes.MASTER_KEY);

					} else {
						return false;
					}
				}
			}
		}

		return true;

	}

	private Set<String> createKeyTypes(DeviceInfo deviceInfo) {
		Set<String> keyTypes = new HashSet<String>();

		if (!deviceInfo.isInitMasterKey()) {
			keyTypes.add(MsrKeyTypes.MASTER_KEY);
			keyTypes.add(MsrKeyTypes.DATA_KEY);
			keyTypes.add(MsrKeyTypes.PIN_KEY);
			if (!hasMacKey(deviceInfo.getAppVersion())) {
				keyTypes.add(MsrKeyTypes.MAC_KEY);
			}
		}

		if (!deviceInfo.isInitDataKey()) {
			keyTypes.add(MsrKeyTypes.DATA_KEY);
		} else if (!deviceInfo.isInitPinKey()) {
			keyTypes.add(MsrKeyTypes.PIN_KEY);
		} else if (!deviceInfo.isInitMacKey()) {
			keyTypes.add(MsrKeyTypes.MAC_KEY);
		}

		return keyTypes;
	}

	public InitIcCardResult initIcCard(String identifier) {

		InitIcCardResult initIcCardResult = new InitIcCardResult();
		initIcCardResult.setSuccess(true);

		boolean isInitParam = CardReaderInitManager.isCardReaderInit(
				aposContext.getAppConfig(), identifier,
				CardReaderInitManager.ININ_KEY_IC_PARAMS);

		boolean isInitPublicKey = CardReaderInitManager.isCardReaderInit(
				aposContext.getAppConfig(), identifier,
				CardReaderInitManager.ININ_KEY_IC_PUBLIC_KEY);

		if (isInitParam && isInitPublicKey) {
			return initIcCardResult;
		}

		try {
			Map<String, Object> data = downloadICCardParamsService
					.downloadImpl();
			List<AposICAppParam> aposICAppParams = (ArrayList<AposICAppParam>) data
					.get(RuntimeAttrNames.IC_CARD_PARAMS);
			List<AposIcPublicKey> aposIcPublicKeys = (List<AposIcPublicKey>) data
					.get(RuntimeAttrNames.IC_CARD_PUBLIC_KEY);

			if (!isInitParam) {
				for (AposICAppParam aposICAppParam : aposICAppParams) {
					AposResultData aposResultData = CardReaderManager
							.addICAppParam(aposICAppParam);

					// 重试3次
					if (!aposResultData.isSuccess()) {
						for (int i = 0; i < 3; i++) {
							aposResultData = CardReaderManager
									.addICAppParam(aposICAppParam);
							if (aposResultData.isSuccess()) {
								break;
							}
						}
					}

					if (!aposResultData.isSuccess()) {
						initIcCardResult.setErrorMsg("初始化参数失败。");
						initIcCardResult.setSuccess(false);
						return initIcCardResult;

					}
				}

				CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
						identifier, CardReaderInitManager.ININ_KEY_IC_PARAMS);

			}

			if (!isInitPublicKey) {

				for (AposIcPublicKey aposIcPublicKey : aposIcPublicKeys) {
					AposResultData aposResultData = CardReaderManager
							.addICPublicKey(aposIcPublicKey);

					// 重试3次
					if (!aposResultData.isSuccess()) {
						for (int i = 0; i < 3; i++) {
							aposResultData = CardReaderManager
									.addICPublicKey(aposIcPublicKey);
							if (aposResultData.isSuccess()) {
								break;
							}
						}
					}

					if (!aposResultData.isSuccess()) {
						initIcCardResult.setErrorMsg("初始化公钥失败。");
						initIcCardResult.setSuccess(false);
						return initIcCardResult;
					}
				}

				CardReaderInitManager.setParamInit(aposContext.getAppConfig(),
						identifier,
						CardReaderInitManager.ININ_KEY_IC_PUBLIC_KEY);

			}

		} catch (Exception exception) {
			Log.e(this.getClass().getName(), "initIC para exception", exception);
			initIcCardResult.setErrorMsg("初始化参数异常。");
			initIcCardResult.setSuccess(false);
		}

		return initIcCardResult;
	}

}
