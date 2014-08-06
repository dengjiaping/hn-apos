package me.andpay.timobileframework.lnk;

import java.util.Map;

/**
 * 客户端Rpc服务接口
 * 
 * @author tinyliu
 * 
 */
public interface TiRpcClient{
	/**
	 * 启动RpcClient
	 */
	public void start();

	/**
	 * Rpc SSL设置
	 * 
	 * @param keyStorePath
	 *            keystore存储位置
	 * @param password
	 *            密钥密码
	 * @param keyManagerPassword
	 *            管理密码
	 */
	public void configSSL(String keyStorePath, String password,
			String keyManagerPassword);

	/**
	 * 停止Rpc远程连接
	 */
	public void stop();

	
	/**
	 * 重启RPC连接
	 */
	public  void restart();

	/**
	 * 重启Rpc连接
	 */
	public void restartTransport();

	public Object getLnkService(Class serviceClass);

	public Long getSelectTimeout();

	public void setSelectTimeout(Long selectTimeout);

	/**
	 * 判断是否已经连接
	 * 
	 * @return
	 */
	public boolean isConn();

	/**
	 * 判断是否配置SSL
	 * 
	 * @return
	 */
	public boolean isConfigSSL();
	/**
	 * 设置cookies
	 * @param cookies
	 */
	public void setCookies(Map<String,String> cookies);
	
	
	/**
	 * 暂停长连接
	 */
	public void pause();

	/**
	 * 恢复长连接
	 */
	public void resume();
	
	
	public TiRemoteParamsReader getRemoteParamsReader();
		
	public void refreshLookupService();
	
	/**
	 * 获取文件上传地址
	 * @return
	 */
	public String getUploadUrl();

	/**
	 * 设置单个cookies
	 * @param key
	 * @param value
	 */
	public void setCookie(String key,String value);
	/**
	 * 获取cookies
	 * @return
	 */
	public Map<String,String> getCookies();
}
