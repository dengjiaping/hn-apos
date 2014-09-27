package me.andpay.apos.common.action;

import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

import com.google.inject.Inject;

@ActionMapping(domain = "/common/upload.action")
public class UploadAction extends MultiAction {

	@Inject
	private UpLoadFileServce upLoadFileServce;

	public ModelAndView synchroUpload(ActionRequest request)
			throws RuntimeException {

		return null;
	}
}
