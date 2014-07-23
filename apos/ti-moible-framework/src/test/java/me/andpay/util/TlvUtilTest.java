package me.andpay.util;

import java.math.BigDecimal;

import me.andpay.ti.lnk.transport.wsock.client.light.http.Base64;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.SwiperRequest;
import me.andpay.timobileframework.util.tlv.TlvUtil;

import org.junit.Test;



public class TlvUtilTest {

	@Test
	public void parseTest() throws Exception {


		
		TlvObject ap = new TlvObject();
		
		String encodeStr = TlvUtil.encodeTvl(ap);
		System.out.println(encodeStr);

		System.out.println(Base64.encode(HexUtils.hexString2Bytes(encodeStr)));
//		AposICCardDataInfo reselt = TlvUtil.decodeTlv(encodeStr, AposICCardDataInfo.class);
		
//		System.out.println(reselt.getAmtOther());
//		System.out.println(reselt.getTermVerificationResult());

	}
	
	@Test
	public void decodeTest() {
		
		SwiperRequest swiperRequest = new SwiperRequest();
		swiperRequest.setAmt(new BigDecimal("0.11").multiply(new BigDecimal(100)));
		swiperRequest.setOpModel(1);
		swiperRequest.setTerminalTraceNo("123456");
		swiperRequest.setPinTimeout(60);
		swiperRequest.setSwiperTimeout(60);
		
		System.out.println(TlvUtil.encodeTvl(swiperRequest));

	}
	
	@Test

	public void decodeTest1() { 
		
		char ss = '1';
		String b = Integer.toHexString(ss);
		System.out.println(b);
	}
	
	
}


