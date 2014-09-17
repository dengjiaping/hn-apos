/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package me.andpay.apos.cmview;

import me.andpay.apos.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PullListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView loadMoreBtn;

	public PullListViewFooter(Context context) {
		super(context);
		mContext = context;
	}

	public PullListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void setState(int state) {
		loadMoreBtn.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		if (state == STATE_READY) {
			loadMoreBtn.setVisibility(View.VISIBLE);
			loadMoreBtn.setText(R.string.com_xlistview_footer_hint_ready_str);
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			loadMoreBtn.setVisibility(View.VISIBLE);
			loadMoreBtn.setText(R.string.com_xlistview_footer_hint_normal_str);
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		loadMoreBtn.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		loadMoreBtn.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	
	public boolean isLoading() {
		return loadMoreBtn.getVisibility() == View.GONE;
	}

	public void initView(int footerViewId, OnClickListener onclickListener) {

		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(footerViewId, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		loadMoreBtn = (TextView) moreView
				.findViewById(R.id.xlistview_footer_loadmore_tv);
		moreView.setOnClickListener(onclickListener);
	}

}
