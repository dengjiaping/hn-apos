package me.andpay.apos.scm.action;

import me.andpay.ac.term.api.ga.FeedbackService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

@ActionMapping(domain = "/scm/feedback.action")
public class ScmFeedBackAction extends SessionKeepAction {

	FeedbackService feedBackService;

	public ModelAndView feedBack(ActionRequest request) throws RuntimeException {
		String feedback = (String) request.getParameterValue("feedback");
		ModelAndView mv = new ModelAndView();
		try {
			feedBackService.submitFeedback(feedback, 0);
			mv.addModelValue("sendSucc", true);
		} catch (Exception ex) {
			mv.addModelValue("sendSucc", false);
		}
		return mv;
	}

}
