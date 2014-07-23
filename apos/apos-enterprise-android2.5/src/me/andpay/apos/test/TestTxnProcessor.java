package me.andpay.apos.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.tam.action.TxnAction;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.BeanUtils;
import me.andpay.timobileframework.util.FileUtil;

public class TestTxnProcessor {
	/**
	 * 测试次数
	 */
	public static final int TEST_COUNT = 2;

	public static void processor(TiActivity activity) {
		for (int i = 0; i < TEST_COUNT; i++) {
			sendTxn(activity);
		}
	}

	private static void sendTxn(TiActivity activity) {
		TxnContext txnContext = new TxnContext();

		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setGoodsUpload(true);
		txnContext.setGoodsFileURL(createTempFile("test_sign.jpg", activity));
		txnContext.setSignUplaod(true);
		txnContext.setBackTagName(TabNames.TXN_PAGE);
		txnContext.setSalesAmt(new BigDecimal("0.88"));
		// txnContext.setExtTraceNo(String.valueOf(System.currentTimeMillis()));
		txnContext.setMemo("测试交易");
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setRePostFlag(false);
		txnContext.setHasNewTxnButton(true);

		txnContext.setPin("456789");
		CardInfo cardInfo = new CardInfo();
		cardInfo.setKsn("0100010000000015");
		cardInfo.setRandomNumber("69DEB33BFFECE46F");
		cardInfo.setEncTracks("F71A4AF203A1BDD77B620CD216C88861DB7B3716FFFCF1942EC4A72D77037DF2E31EEC9F073ABCE6CA77B2B642832BFF01020410213727BACBF74DC24028BD7D");
		txnContext.setCardInfo(cardInfo);

		ParseBinResponse parseBinResponse = new ParseBinResponse();
		parseBinResponse.setCardAssoc("CU");
		parseBinResponse.setCardIssuerId("03090010");
		parseBinResponse.setCardNo("6229015235959103");
		txnContext.setParseBinResp(parseBinResponse);

		EventRequest request = activity.generateSubmitRequest(activity);
		request.open(TxnAction.DOMAIN_NAME, TxnAction.TXN_ACTION, Pattern.async);
		Map submitData = new HashMap();
		TxnForm txnForm = BeanUtils.copyProperties(TxnForm.class, txnContext);
		submitData.put("txnForm", txnForm);
		request.callBack(new TestTxnCallback(activity));
		request.submit(submitData);
	}

	public static String createTempFile(String sourceFileName,
			TiActivity activity) {

		FileOutputStream output = null;
		InputStream input = null;
		try {
			input = activity.getResources().getAssets().open(sourceFileName);
			String tempFilePath = FileUtil.getFilePath(FileUtil.getMyUUID(),
					activity);
			File destFile = new File(tempFilePath);
			output = new FileOutputStream(destFile);

			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return tempFilePath;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException ioe) {
				// ignore
			}
		}
	}
}
