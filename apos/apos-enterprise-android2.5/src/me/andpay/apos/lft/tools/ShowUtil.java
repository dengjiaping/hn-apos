package me.andpay.apos.lft.tools;

import me.andpay.apos.lft.view.CustomDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * 显示工具类
 * 
 * @author Administrator
 * 
 */
public class ShowUtil {
	/*************************************** 提示 *******************************************/
	public static void showShortToast(Context context, int pResId) {
		showShortToast(context, context.getString(pResId));
	}

	public static void showLongToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
	}

	/* 加载布局文件 */
	public static View LoadXmlView(Context context, int xmlId) {
		LayoutInflater flat = LayoutInflater.from(context);
		View view = flat.inflate(xmlId, null);
		return view;
	}
	/**
	 * 获得自定义的dialog
	 */
	public static CustomDialog getCustomDialog(Context context, int layoutId,
			int type) {
		View contentView = LoadXmlView(context, layoutId);
		CustomDialog customDialog = new CustomDialog(context, contentView);
		customDialog.setType(type);
		return customDialog;
	}



}
