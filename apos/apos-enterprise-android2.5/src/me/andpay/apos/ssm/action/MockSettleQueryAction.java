package me.andpay.apos.ssm.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.ac.term.api.txn.TxnBatchItem;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

@ActionMapping(domain = "/ssm/query.action")
public class MockSettleQueryAction extends MultiAction {
	/**
	 * 加载用户未结帐汇总信息
	 * 
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView loadUnSettleInfo(ActionRequest request)
			throws RuntimeException {
		ModelAndView mv = new ModelAndView();
		mv.addModelValue("hasUnSettleInfo", true);
		mv.addModelValue("beginDate", new Date());
		mv.addModelValue("endDate", new Date());
		mv.addModelValue("txnCount", "" + 10);
		mv.addModelValue("txnAmount", "" + 100.10);
		mv.addModelValue("cancelCount", "" + 10);
		mv.addModelValue("cancelAmount", "" + 100.10);
		mv.addModelValue("amount", "" + "" + 200.20);
		mv.addModelValue("count", "" + "" + 20);
		return mv;
	}

	/**
	 * 查询用户结帐汇总信息
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySettleInfo(ActionRequest request) {
		List<TxnBatch> batchs = new LinkedList<TxnBatch>();
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int count = new Random().nextInt(5) + 1;
		for (int i = 0; i < count; i++) {
			TxnBatch batch = new TxnBatch();
			batch.setBatchTime(new Date());
			batch.setId(System.currentTimeMillis());
			TxnBatchItem item = new TxnBatchItem();
			item.setCount(100);
			item.setTotal(new BigDecimal(10000.12));
			batch.setSummary(item);
			Map<String, TxnBatchItem> items = new HashMap<String, TxnBatchItem>();
			if (i % 2 == 0) {
				items.put(TxnTypes.PURCHASE, item);
			} else {
				TxnBatchItem item1 = new TxnBatchItem();
				item1.setCount(50);
				item1.setTotal(new BigDecimal(5000.06));
				items.put(TxnTypes.PURCHASE, item1);
				items.put(TxnTypes.REFUND, item1);
			}
			batch.setItems(items);
			batchs.add(batch);
		}
		return new ModelAndView().addModelValue("infoList", batchs);
	}
}
