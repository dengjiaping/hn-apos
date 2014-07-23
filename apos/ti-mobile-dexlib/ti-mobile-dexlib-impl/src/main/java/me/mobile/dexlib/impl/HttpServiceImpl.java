package me.mobile.dexlib.impl;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

import me.andpay.ti.util.IOUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import ti.mobile.dexlib.api.HttpService;

public class HttpServiceImpl implements HttpService {

	public String httpSimpleGet(String postUrl) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(postUrl);
		HttpParams httpParams = httpGet.getParams();
		httpParams.setParameter(AllClientPNames.CONNECTION_TIMEOUT, 3000);
		httpParams.setParameter(AllClientPNames.SO_TIMEOUT, 3000);
		httpParams.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				"UTF-8");
		try {
			HttpResponse httpResponse = httpclient.execute(httpGet);
			int statue = httpResponse.getStatusLine().getStatusCode();
			if (statue == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				return IOUtil.toString(inputStream, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public int simplUpload(String url, File[] files) {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpParams httpParams = client.getParams();
		httpParams.setParameter(AllClientPNames.CONNECTION_TIMEOUT, 60000);
		httpParams.setParameter(AllClientPNames.SO_TIMEOUT, 30000);

		HttpPost post = new HttpPost(url);
		int i = 0;
		MultipartEntity entity = new MultipartEntity();
		for (File file : files) {
			try {
				entity.addPart(
						"file-" + (i++),
						new FileBody(file, URLEncoder.encode(file.getName(),
								"UTF-8"), "application/octet-stream", null));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		post.setEntity(entity);

		try {
			return client.execute(post).getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
