package me.andpay.apos.cdriver;

public interface InitCardReaderService {

	/**
	 * 初始化密钥
	 * @param identifier
	 * @return
	 */
	public InitMsrKeyResult initMsrKey(String identifier);
	
	
	public InitIcCardResult initIcCard(String identifier);
}
