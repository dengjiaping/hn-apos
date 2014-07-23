package me.andpay.apos.common.util;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build;

@SuppressLint("NewApi")
public class APOSGifUtil {

	public static void startGif(GifDrawable gifDrawable,
			GifImageView gifImageView, Resources resources, int drawableId) {

		try {
			if(gifDrawable != null) {
				gifDrawable.stop();
				gifDrawable.recycle();
			}
			
			gifDrawable = new GifDrawable(resources, drawableId);
			if (Build.VERSION.SDK_INT >= 16)
				gifImageView.setBackground(gifDrawable);
			else
				gifImageView.setBackgroundDrawable(gifDrawable);
			gifDrawable.start();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
