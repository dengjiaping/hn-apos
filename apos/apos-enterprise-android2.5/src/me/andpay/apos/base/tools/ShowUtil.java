package me.andpay.apos.base.tools;

import java.io.File;

import me.andpay.apos.R;
import me.andpay.apos.base.inteface.PhotoResponse;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
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

	private static LayoutInflater inflater = null;

	/* 加载视图 */
	public static View getLayoutView(int layoutR) {
		if (inflater == null) {
			inflater = LayoutInflater.from(TiApplication.getContext());
		}
		return inflater.inflate(layoutR, null);
	}

	/**
	 * 拍照
	 * 
	 * @param context
	 *            上下文
	 * @param titleStr
	 *            标题
	 * @param response
	 *            响应
	 * @return
	 */
	public static CustomDialog getPhotoView(Context context, String titleStr,
			final PhotoResponse response) {

		final CustomDialog dialog = getCustomDialog(context,
				R.layout.select_image, 1);
		View view = dialog.getcView();
		TextView title = (TextView) view.findViewById(R.id.select_image_title);
		TextView album = (TextView) view.findViewById(R.id.select_image_album);
		TextView takingPictures = (TextView) view
				.findViewById(R.id.select_image_taking_pictures);
		TextView cancle = (TextView) view
				.findViewById(R.id.select_image_cancle);

		title.setText(titleStr);
		album.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				response.selectAlbum();
			}
		});

		takingPictures.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				response.selectTakingPictures();
			}
		});

		cancle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		return dialog;
	}

	/**
	 * 选相册
	 * 
	 * @param activity
	 *            类
	 * @param flat
	 *            标志
	 */
	public static void selectAlbum(Activity activity, int flat) {

		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		activity.startActivityForResult(intent, flat);

	}

	/**
	 * 获得选择照片的路径
	 * 
	 * @param activity
	 *            上下文
	 * @param data
	 *            返回的intent
	 * @return 路径
	 */
	public static String getAlbumPath(Activity activity, Intent data) {
		try {
			// 获得图片的uri
			Uri originalUri = data.getData();
			// 这里开始的第二部分，获取图片的路径：
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = activity.managedQuery(originalUri, proj, null,
					null, null);
			// 按我个人理解 这个是获得用户选择的图片的索引值
			int columnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			// 最后根据索引值获取图片路径
			String path = cursor.getString(columnIndex);
			return path;

		} catch (Exception e) {

			return "";
		}

	}

	/**
	 * 拍照
	 * 
	 * @param activity
	 * @param file
	 *            拍照后存储文件
	 * @param flat
	 *            标志值
	 */
	public static void selectTakingPictures(Activity activity, File file,
			int flat) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivityForResult(intent, flat);

	}

	/**
	 * 显示popwindow
	 * 
	 * @param context
	 *            上下文
	 * @param layoutR
	 *            显示view
	 * @param isOutSide
	 *            是否点击外面消失
	 * @return 返回
	 */
	public static Object[] getCustomPopupWindow(Context context,
			int layoutR, boolean isOutSide) {
		View view = ShowUtil.LoadXmlView(context, layoutR);
		PopupWindow pw = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setOutsideTouchable(isOutSide);
		pw.setFocusable(true);
		return new Object[]{pw,view};
	}

}
