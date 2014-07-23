package me.andpay.apos.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.term.api.ic.ICAppPara;
import me.andpay.ac.term.api.ic.ICAppParaTagNames;
import me.andpay.ac.term.api.ic.ICCaPubKey;
import me.andpay.ac.term.api.ic.ICCaPubKeyTagNames;
import me.andpay.ac.term.api.ic.ICDataService;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.dao.ICCardParamsInfoDao;
import me.andpay.apos.dao.ICCardPublicKeyInfoDao;
import me.andpay.apos.dao.model.ICCardParamsInfo;
import me.andpay.apos.dao.model.ICCardPublicKeyInfo;
import me.andpay.apos.dao.model.QueryICCardParamsInfoCond;
import me.andpay.apos.dao.model.QueryICCardPublicKeyInfoCond;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.BeanUtils;
import android.util.Log;

import com.google.inject.Inject;

public class DownloadICCardParamsService {

	private ICDataService icDataService;

	@Inject
	private ICCardPublicKeyInfoDao icCardPublicKeyInfoDao;
	@Inject
	private ICCardParamsInfoDao icCardParamsInfoDao;

	@Inject
	private AposContext aposContext;

	public void downloadICparams() {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					downloadImpl();
				} catch (Exception ex) {
					Log.e(this.getClass().getName(), "downloadICparams error",
							ex);
				}
			}
		});
		thread.start();
	}

	public synchronized Map<String, Object> downloadImpl() {

		Map<String, Object> returnData = new HashMap<String, Object>();

		List<AposIcPublicKey> aposIcPublicKeys = queryAposPublicKey();
		returnData.put(RuntimeAttrNames.IC_CARD_PUBLIC_KEY, aposIcPublicKeys);

		List<AposICAppParam> aposICAppParams = queryAposICAppParams();
		returnData.put(RuntimeAttrNames.IC_CARD_PARAMS, aposICAppParams);

		return returnData;
	}

	private List<AposICAppParam> queryAposICAppParams() {

		Object icCardParamsObject = aposContext.getAppContext().getAttribute(
				RuntimeAttrNames.IC_CARD_PARAMS);
		
		List<AposICAppParam> aposICAppParams = new ArrayList<AposICAppParam>();
		if (icCardParamsObject == null) {
			
			String hasSaved = (String) aposContext.getAppConfig().getAttribute(
					ConfigAttrNames.IC_CARD_PARAMS_SAVED);
			List<ICCardParamsInfo> icCardParamsInfos = icCardParamsInfoDao
					.query(new QueryICCardParamsInfoCond(), 0, 100);
			
			if (icCardParamsInfos.size() == 0 || StringUtil.isBlank(hasSaved)) {
				
				//清空不完整的数据
				if (icCardParamsInfos.size() > 0) {
					for (ICCardParamsInfo icCardParamsInfo : icCardParamsInfos) {
						icCardParamsInfoDao.delete(icCardParamsInfo
								.getIdICCardParamsInfo());
					}
				}

				icCardParamsInfos = new ArrayList<ICCardParamsInfo>();
				List<ICAppPara> icAppParas = icDataService.getAllICAppParas();

				for (ICAppPara icAppPara : icAppParas) {

					ICCardParamsInfo icInfo = new ICCardParamsInfo();
					Map<String, String> paraData = icAppPara.getParaData();

					icInfo.setAid(icAppPara.getAid());
					icInfo.setAis(paraData.get(ICAppParaTagNames.ASI));
					icInfo.setAppViersionId(paraData
							.get(ICAppParaTagNames.APP_VER_NO));
					icInfo.setContactlessReaderOfflineMinQuoata(paraData
							.get(ICAppParaTagNames.CONTACTLESS_READER_OFFLINE_MIN_QUOTA));
					icInfo.setConteactLessReaderTxnQuota(paraData
							.get(ICAppParaTagNames.CONTACTLESS_READER_TXN_QUOTA));
					icInfo.setDefaultDool(paraData
							.get(ICAppParaTagNames.DEFAULT_DDOL));
					icInfo.setMaxTargetPercentageBias(paraData
							.get(ICAppParaTagNames.MAX_TARGET_PER_FOR_BRS));
					icInfo.setReaderCvmLimit(paraData
							.get(ICAppParaTagNames.READER_CVM_LIMIT));
					icInfo.setTacDefault(paraData
							.get(ICAppParaTagNames.TAC_DEFAULT));
					icInfo.setTacOnline(paraData
							.get(ICAppParaTagNames.TAC_ONLINE));
					icInfo.setTacReject(paraData
							.get(ICAppParaTagNames.TAC_REJECT));
					icInfo.setTargetPercentage(paraData
							.get(ICAppParaTagNames.TARGET_PER_FOR_RS));
					icInfo.setTerminalEcashTxnQuota(paraData
							.get(ICAppParaTagNames.TERM_ECASH_TXN_QUOTA));
					icInfo.setTerminalFloorLimit(paraData
							.get(ICAppParaTagNames.TERM_FLOOR_LIMIT));
					icInfo.setTerminalOnlinePinBlance(paraData
							.get(ICAppParaTagNames.TERM_ONLINE_PIN_ENABLED));
					icInfo.setThresholdBias(paraData
							.get(ICAppParaTagNames.THRE_VAL_FOR_BRS));
					icCardParamsInfoDao.insert(icInfo);
					icCardParamsInfos.add(icInfo);
				}
			}

			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.IC_CARD_PARAMS_SAVED,
					ConfigAttrNames.IC_CARD_PARAMS_SAVED);

			for (ICCardParamsInfo icCardParamsInfo : icCardParamsInfos) {
				aposICAppParams.add(BeanUtils.copyProperties(
						AposICAppParam.class, icCardParamsInfo));
			}

			aposContext.getAppContext().setAttribute(
					RuntimeAttrNames.IC_CARD_PARAMS, aposICAppParams);

		} else {
			aposICAppParams = (List<AposICAppParam>) icCardParamsObject;
		}
		return aposICAppParams;
	}

	private List<AposIcPublicKey> queryAposPublicKey() {

		List<AposIcPublicKey> aposIcPublicKeys = new ArrayList<AposIcPublicKey>();
		Object icCardPublicKeyObject = aposContext.getAppContext()
				.getAttribute(RuntimeAttrNames.IC_CARD_PUBLIC_KEY);
		if (icCardPublicKeyObject == null) {

			String hasSaved = (String) aposContext.getAppConfig().getAttribute(
					ConfigAttrNames.IC_CARD_PUBLIC_KEY_SAVED);

			List<ICCardPublicKeyInfo> icPublicKeyInfos = icCardPublicKeyInfoDao
					.query(new QueryICCardPublicKeyInfoCond(), 0, 100);
			if (icPublicKeyInfos.size() == 0 || StringUtil.isBlank(hasSaved)) {
				
				
				//清空不完整的数据
				if (icPublicKeyInfos.size() > 0) {
					for (ICCardPublicKeyInfo icCardPublicKeyInfo : icPublicKeyInfos) {
						icCardPublicKeyInfoDao.delete(icCardPublicKeyInfo
								.getIdICCardPublicKeyInfo());
					}
				}

				icPublicKeyInfos = new ArrayList<ICCardPublicKeyInfo>();
				List<ICCaPubKey> pubKeys = icDataService.getAllICCaPubKeys();
				for (ICCaPubKey icCaPubKey : pubKeys) {
					Map<String, String> pubkeyData = icCaPubKey.getPubKeyData();
					ICCardPublicKeyInfo icCardPublicKeyInfo = new ICCardPublicKeyInfo();
					icCardPublicKeyInfo.setExpirationDate(icCaPubKey
							.getExpirationDate());
					icCardPublicKeyInfo.setIndexText(icCaPubKey.getIdx());
					icCardPublicKeyInfo.setRid(pubkeyData
							.get(ICCaPubKeyTagNames.RID));

					icCardPublicKeyInfo.setExponent(pubkeyData
							.get(ICCaPubKeyTagNames.CPK_EXPONENT));
					icCardPublicKeyInfo.setHashAlgorithmIndicator(pubkeyData
							.get(ICCaPubKeyTagNames.CPK_HASH_ALG));
					icCardPublicKeyInfo.setModulus(pubkeyData
							.get(ICCaPubKeyTagNames.CPK_MODULE));
					icCardPublicKeyInfo.setSha1CheckSum(pubkeyData
							.get(ICCaPubKeyTagNames.CPK_CHECK_VALUE));
					icCardPublicKeyInfo
							.setSignatureAlgorithmIndicator(pubkeyData
									.get(ICCaPubKeyTagNames.CPK_SIGN_ALG));
					icCardPublicKeyInfoDao.insert(icCardPublicKeyInfo);
					icPublicKeyInfos.add(icCardPublicKeyInfo);
				}
			}

			aposContext.getAppConfig().setAttribute(
					ConfigAttrNames.IC_CARD_PUBLIC_KEY_SAVED,
					ConfigAttrNames.IC_CARD_PUBLIC_KEY_SAVED);

			for (ICCardPublicKeyInfo icCardPublicKeyInfo : icPublicKeyInfos) {
				AposIcPublicKey aposIcPublicKey = BeanUtils.copyProperties(
						AposIcPublicKey.class, icCardPublicKeyInfo);
				aposIcPublicKey.setIndex(icCardPublicKeyInfo.getIndexText());
				aposIcPublicKeys.add(aposIcPublicKey);
			}

			aposContext.getAppContext().setAttribute(
					RuntimeAttrNames.IC_CARD_PUBLIC_KEY, aposIcPublicKeys);
		} else {
			aposIcPublicKeys = (List<AposIcPublicKey>) icCardPublicKeyObject;
		}

		return aposIcPublicKeys;

	}
}
