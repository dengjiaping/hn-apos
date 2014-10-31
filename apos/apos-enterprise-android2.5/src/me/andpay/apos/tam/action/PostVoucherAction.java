package me.andpay.apos.tam.action;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.term.api.base.DeliverDest;
import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.tam.callback.PostVoucherCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.PostVoucherForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain = PostVoucherAction.DOMAIN_NAME)
public class PostVoucherAction extends SessionKeepAction{

	public final static String DOMAIN_NAME = "/tam/postvc.action";
	public final static String POSTVC_ACTION = "postVoucher";

	private TxnService txnService;

	@Inject
	private TxnControl txnControl;

	public void postVoucher(ActionRequest request) {

		List<DeliverDest> dests = new ArrayList<DeliverDest>();

		PostVoucherForm postForm = (PostVoucherForm) request
				.getParameterValue("postVoucherForm");

		if (StringUtil.isNotBlank(postForm.getEmail())) {
			DeliverDest dest = new DeliverDest();
			dest.setDeliverType(DeliverDest.TYPE_EMAIL);
			dest.setDestInfo(postForm.getEmail());
			dests.add(dest);
		}

		if (StringUtil.isNotBlank(postForm.getPhone())) {
			DeliverDest dest = new DeliverDest();
			dest.setDeliverType(DeliverDest.TYPE_SMS);
			dest.setDestInfo(postForm.getPhone());
			dests.add(dest);

		}
		//
		// if (StringUtil.isNotBlank(postForm.getMicroblog())) {
		// DeliverDest dest = new DeliverDest();
		// dest.setDeliverType(DeliverDest.TYPE_WEIBO_SINA);
		// dest.setDestInfo(postForm.getMicroblog());
		// dests.add(dest);
		// }

		DeliverDest dest = new DeliverDest();
		dest.setDeliverType(DeliverDest.TYPE_EMAIL);
		PostVoucherCallback callBack = (PostVoucherCallback) request
				.getHandler();

		try {
			txnService.sendReceipt(dests,postForm.getTxnId());
			callBack.dealResponse();
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "postvoicher error", ex);
			callBack.netWorkerror();

		}
	}
}
