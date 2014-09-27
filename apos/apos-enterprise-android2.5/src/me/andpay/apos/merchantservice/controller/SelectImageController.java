package me.andpay.apos.merchantservice.controller;

import java.io.File;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.base.tools.MathUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/*
 * 添加图片控制器
 */

public class SelectImageController extends BaseAdapterController<String> {
	public static final String TAG = "add";// 添加图片标志

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = ShowUtil.LoadXmlView(TiApplication.getContext(),
					R.layout.image_show);
		}
		ImageView show = (ImageView) arg1.findViewById(R.id.image_show_content);
		if (getAdpter().getList().get(arg0).equals(TAG)) {

			Picasso.with(TiApplication.getContext())
					.load(R.drawable.add_yes)
					.resize(MathUtil.diptoPx(getAdpter().getContext(), 90),
							MathUtil.diptoPx(getAdpter().getContext(), 80))

					.into(show);

		} else {

			Picasso.with(TiApplication.getContext())
					.load(new File(getAdpter().getList().get(arg0)))
					.resize(MathUtil.diptoPx(getAdpter().getContext(), 90),
							MathUtil.diptoPx(getAdpter().getContext(), 80))
					.into(show);
		}
		return arg1;
	}

	@Override
	public void getEvent(View view, int position) {
		// TODO Auto-generated method stub
		if (position == getAdpter().getList().size() - 1) {
			view.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getAdpter().getAdpterEventListener().onEventListener(
							new Object[] { 0 });
				}
			});
		} else {
			view.setOnClickListener(null);
		}
	}

}
