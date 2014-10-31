package me.andpay.apos.lft.flow.transfer;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.ti.util.AttachmentItem;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

/**
 * 充值数据转化器
 * 
 * @author xiaoping.shan
 *
 */
public class TopupTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		// TODO Auto-generated method stub

		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		CommonTermTxnResponse cm = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CommonTermTxnResponse.class);
		SignContext signContext = new SignContext();
		
		AttachmentItem item = null;
		for(int i=0;i<cm.getAttachmentItems().size();i++){
			item = cm.getAttachmentItems().get(i);
			if(item.getAttachmentType().equals(AttachmentTypes.SIGNATURE_PICTURE)){
				break;
			}
		}
		signContext.setSignType(txnContext.getTxnType());
		signContext.setIdUnderType(item.getIdUnderType());
		
		signContext.setShowAmt(txnContext.getAmtFomat());
		signContext.setAmtTextColor(activity.getResources().getColor(
				R.color.tqm_list_item_amount_col));
		TiFlowControlImpl.instanceControl().setFlowContextData(signContext);
		
		

//		signContext.setTermTraceNo(txnContext.getTxnActionResponse()
//				.getTermTraceNo());
		
		// String txnTime = TimeUtil.getInstance().formatDate(new Date(),
		// TimeUtil.DATE_PATTERN_4);
		// signContext.setTermTxnTime(txnTime);
		// .getTermTxnTime());

		return null;
	}

}
