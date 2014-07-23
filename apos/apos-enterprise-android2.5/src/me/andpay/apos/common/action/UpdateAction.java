package me.andpay.apos.common.action;

import java.math.BigDecimal;
import java.util.Locale;

import me.andpay.ac.term.api.app.ApplicationInfo;
import me.andpay.ac.term.api.app.ApplicationUpgradeService;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.Action;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;

/**
 * 版本监测action
 * 
 * @author tinyliu
 * 
 */
@ActionMapping(domain = "/common/update.action")
public class UpdateAction implements Action {

	ApplicationUpgradeService upgradService;

	public ModelAndView handler(ActionRequest request) throws RuntimeException {
		Boolean isUpdate = false;
		ModelAndView mv = new ModelAndView();
		DeviceInfo dInfo = (DeviceInfo) request.getContext(
				TiContext.CONTEXT_SCOPE_APPLICATION).getAttribute(
				RuntimeAttrNames.DEVICE_INFO);
		ApplicationInfo info = null;
		try {
			info = upgradService.getLatestApplicationInfo(dInfo.getAppCode(),
					dInfo.getOsCode(), dInfo.getAppVersionCode(), Locale
							.getDefault().getLanguage());
		} catch (Exception e) {
			mv.setHappedEx(e);
			return mv.addModelValue("isUpdate", isUpdate);
		}
		Integer cvcode = (Integer) request
				.getParameterValue("currentVersionCode");
		if (info != null && !StringUtil.isEmpty(info.getAppVersionCode())
				&& Integer.parseInt(info.getAppVersionCode()) > cvcode) {
			isUpdate = true;

			mv.addModelValue("newVersion", info.getAppName());
			mv.addModelValue("updateInfo", info.getDescription());
			mv.addModelValue("updateDownloadUrl", info.getAppUpgradeURL());

			mv.addModelValue("time", info.getReleaseTime());
			// 换算成为M单位
			mv.addModelValue(
					"size",
					new BigDecimal(info.getAppSize()).divide(
							new BigDecimal(1024 * 1024)).setScale(2,
							BigDecimal.ROUND_HALF_UP)).toString();
		}
		return mv.addModelValue("isUpdate", isUpdate);
	}

}
