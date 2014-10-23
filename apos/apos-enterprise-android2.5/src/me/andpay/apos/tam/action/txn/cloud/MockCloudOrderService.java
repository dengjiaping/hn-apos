package me.andpay.apos.tam.action.txn.cloud;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.CardAssoces;
import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.consts.TxnRespCodes;
import me.andpay.ac.term.api.txn.PurchaseResponse;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.ac.term.api.txn.order.CloudOrderApply;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.transport.websock.common.NetworkOpPhase;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;

public class MockCloudOrderService {

	public Map<String, CloudOrderApply> caches = new HashMap<String, CloudOrderApply>();

	public String processCloudOrderApply(CloudOrderApply copApply)
			throws AppBizException {
		String orderid = null;

		try {
			orderid = String.valueOf(System.currentTimeMillis());
			Thread.sleep(5 * 1000);
			if (copApply.getSalesAmt().doubleValue() == 0.01) {
				throw new RuntimeException("订单上送失败");
			}
			caches.put(orderid, copApply);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return orderid;
	}

	public void cancelCloudOrder(String cloudOrderNo) throws AppBizException {

	}

	public TxnResponse getCloudOrderPaymentResult(String cloudOrderNo)
			throws AppBizException {

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		CloudOrderApply apply = caches.get(cloudOrderNo);
		if (apply.getSalesAmt().doubleValue() == 0.02) {
			throw new WebSockTimeoutException(NetworkOpPhase.READ_WRITE,
					"订单上送失败", 5);
		}

		PurchaseResponse response = new PurchaseResponse();
		response.setTxnFlag(TxnFlags.SUCCESS);
		response.setAuthAmt(apply.getSalesAmt());
		response.setSignTxnFlag(false);
		response.setEncCardNo("6225********2092");
		response.setCardAssoc(CardAssoces.VISA);
		response.setMerchantName("湖南银联测试信息");
		response.setCardName("招商银行信用卡");
		response.setShortCardNo("6225********2092");
		response.setRespMessage("交易成功");
		response.setRespCode(TxnRespCodes.SUCCESS);
		response.setTxnFlagMessage("交易成功");
		response.setTxnTime(new Date());
		response.setIssuerName("招商银行");
		response.setTxnId("000000000000001");
		return response;
	}

}
