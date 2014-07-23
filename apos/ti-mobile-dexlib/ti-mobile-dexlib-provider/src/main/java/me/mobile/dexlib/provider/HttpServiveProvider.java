package me.mobile.dexlib.provider;

import java.io.File;

import me.mobile.dexlib.provider.util.DexFileUtils;
import ti.mobile.dexlib.api.HttpService;
import android.content.Context;
import dalvik.system.DexClassLoader;

public class HttpServiveProvider {

	private static HttpService httpService;

	public static HttpService get(Context context) {

		if (httpService != null) {
			return httpService;
		}
		DexFileUtils.prepareDex("dexlib-impl.dex", context);
		DexFileUtils.prepareDex("httpclient-4.2.1.dex", context);
		DexFileUtils.prepareDex("httpcore-4.2.1.dex", context);
		DexFileUtils.prepareDex("httpmime-4.2.1.dex", context);

		final File optimizedDexOutputPath = context.getDir("outdex",
				Context.MODE_PRIVATE);

		DexClassLoader cl = new DexClassLoader(DexFileUtils.getDexFilePath(
				context, "httpclient-4.2.1.dex")
				+ File.pathSeparator
				+ DexFileUtils.getDexFilePath(context, "httpcore-4.2.1.dex")
				+ File.pathSeparator
				+ DexFileUtils.getDexFilePath(context, "httpmime-4.2.1.dex")
				+ File.pathSeparator
				+ DexFileUtils.getDexFilePath(context, "dexlib-impl.dex"),
				optimizedDexOutputPath.getAbsolutePath(), null,
				context.getClassLoader());

		Class<?> clazz = null;
		try {
			clazz = cl.loadClass("me.mobile.dexlib.impl.HttpServiceImpl");
			httpService = (HttpService) clazz.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return httpService;

	}
}
