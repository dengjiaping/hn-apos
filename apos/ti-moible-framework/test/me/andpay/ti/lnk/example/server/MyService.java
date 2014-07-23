package me.andpay.ti.lnk.example.server;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.lnk.annotaion.LnkAbstractService;
import me.andpay.ti.lnk.annotaion.LnkMethod;
import me.andpay.ti.lnk.annotaion.Sla;

/**
 * 测试服务接口定义类。
 * 
 * @author sea.bao
 */
@LnkAbstractService
public interface MyService {
	/**
	 * 应答处理
	 * 
	 * @param str
	 * @return
	 */
	String echo(@NotNull String str);

	/**
	 * 存储Session数据
	 * 
	 * @param value
	 * @return
	 */
	void storeSession(String value);

	/**
	 * 获得Session数据
	 * 
	 * @return
	 */
	String getSession();

	/**
	 * 获得汇率集
	 * 
	 * @param curPairs
	 * @return
	 */
	Map<String, BigDecimal> getRates(Set<String> curPairs);

	/**
	 * 异步处理
	 * 
	 * @param request
	 * @return
	 */
	@LnkMethod(async = true)
	String asyncProcess(String request);

	/**
	 * 过载处理
	 */
	@LnkMethod(async = true)
	@Sla(timeout = 100)
	void overloadProcess();
	
	/**
	 * 低于Sla要求的处理
	 */
	@Sla(avgTime = 100)
	String lowSlaProcess();
	/**
	 * 链式处理交易
	 * 
	 * @param request
	 * @param respHandler
	 */
	@LnkMethod(async = true)
	void chainProcessTxn(TxnRequest request, @Callback TxnResponseHandler respHandler);
}