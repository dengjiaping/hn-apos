package ti.mobile.dexlib.api;

import java.io.File;


public interface HttpService {
	
	
	/**
	 * get请求
	 * @param postUrl
	 * @return
	 */
	public  String httpSimpleGet(String postUrl);
	
	/**
	 * 简单文件上传
	 * @param url
	 * @param files
	 * @return
	 */
	public int simplUpload(String url, File[] files);

}
