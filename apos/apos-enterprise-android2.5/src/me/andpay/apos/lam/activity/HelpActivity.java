package me.andpay.apos.lam.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.lam.event.HelpStartPageChangeEventControl;
import me.andpay.apos.lam.event.ShowLoginControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@ContentView(R.layout.lam_help_start_layout)
public class HelpActivity extends TiActivity {

	@InjectView(R.id.help_viewpager)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnPageChangeListener.class, toEventController = HelpStartPageChangeEventControl.class)
	public ViewPager introViewpager;

	public LocalActivityManager localActivityManager;

	@InjectView(R.id.lam_toLogin_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowLoginControl.class)
	public Button toLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		LayoutInflater mLi = LayoutInflater.from(this);
		View help1 = mLi.inflate(R.layout.lam_help1_layout, null);
		View help2 = mLi.inflate(R.layout.lam_help2_layout, null);
		View help3 = mLi.inflate(R.layout.lam_help3_layout, null);
		View help4 = mLi.inflate(R.layout.lam_help4_layout, null);

		localActivityManager = new LocalActivityManager(this, true);
		localActivityManager.dispatchCreate(savedInstanceState);

		final ArrayList<View> helpList = new ArrayList<View>();
		helpList.add(help1);
		helpList.add(help2);
		helpList.add(help3);
		helpList.add(help4);

		// 填充ViewPager的数据适配器
		PagerAdapter helpPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return helpList.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(helpList.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(helpList.get(position));
				return helpList.get(position);
			}
		};

		introViewpager.setAdapter(helpPagerAdapter);

	}
}
