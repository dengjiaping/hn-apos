package me.andpay.apos.tqm.action;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.DateUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

@ActionMapping(domain = "/tqm/query.action")
public class QueryTxnMockAction extends MultiAction{

	private Integer idTxn = 100000;

	/**
	 * 查询交易 先从本地查询，如何查询记录不够到服务端查询
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryTxnList(ActionRequest request) {
		LinkedList<PayTxnInfo> results = new LinkedList<PayTxnInfo>();
		ModelAndView mv = new ModelAndView();
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = new Random().nextInt(5) + 1;
		for (int i = 0; i < count; i++) {
			PayTxnInfo info = new PayTxnInfo();
			if (i % 2 == 0) {
				info.setCardOrg("招商银行");
				info.setShortPan("622588******2092");
				//info.setSalesAmt(new Random().nextDouble());
				info.setMerchantName("星巴克咖啡");
				info.setTxnTime(DateUtil.format("yyyy/MM/dd HH:mm:ss",
						new Date()));
				info.setTxnTypeDesc("消费");
				info.setTxnType(TxnTypes.PURCHASE);
				info.setExTraceNO("CZZ099" + idTxn);
				info.setTranPic("http://www.test");
				//info.setRefundFlag(true);
				info.setLatitude(121.2943);
				info.setLongitude(31.1430);
				info.setPositionDesc("上海市浦东新区东方明珠塔32号601");
			} else {
				info.setCardOrg("浦发银行");
				info.setShortPan("622118******2092");
				//info.setSalesAmt(new Random().nextDouble());
				info.setMerchantName("上海和付信息技术有限公司");
				info.setTxnTime(DateUtil.format("yyyy/MM/dd HH:mm:ss",
						new Date()));
				info.setTxnTypeDesc("消费");
				info.setTxnType(TxnTypes.PURCHASE);
				info.setExTraceNO("CZZ099" + idTxn);
				info.setLatitude(121.2943);
				info.setLongitude(31.1430);
				info.setPositionDesc("上海市浦东新区东方明珠塔32号601");
			}
			info.setIdTxn(idTxn);
			idTxn++;
			results.add(info);
		}
		return mv.addModelValue("txnList", results).addModelValue("queryConditionForm", new QueryConditionForm());
	}
}
