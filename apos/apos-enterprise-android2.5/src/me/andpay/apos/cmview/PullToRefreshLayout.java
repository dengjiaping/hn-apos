package me.andpay.apos.cmview;

import java.util.Date;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullActivateLayout.OnPullListener;
import me.andpay.apos.cmview.PullActivateLayout.OnPullStateListener;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PullToRefreshLayout extends PullActivateLayout implements
		OnPullListener, OnPullStateListener {

	/* Handler message id */
	private final static int MSG_LOADING = 1;
	private final static int MSG_LOADED = 2;

	/* Variable */
	private boolean mInLoading = false;

	/* Views, widgets, animations & drawables */
	private PullActivateLayout mPullLayout;
	private TextView mActionText;
	private TextView mTimeText;
	private View mProgress;
	private View mActionImage;
	private View mActionView;
	private PullListViewFooter footerView;
	private ListView listView;
	private boolean isRefreashEnable = true;
	private boolean isLoadMoreEnable = true;
	private boolean isFootViewRemoved = false;

	private Animation mRotateUpAnimation;
	private Animation mRotateDownAnimation;

	private IOperationListener listener;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_LOADING:
				try {
					if (listener != null) {
						listener.onRefresh();
					}
				} catch (Exception ex) {
					Log.e(this.getClass().getName(), ex.getMessage());
				}
				break;
			case MSG_LOADED:
				dataLoaded();
				break;
			}
		}
	};
	private boolean mPullLoading;

	public PullToRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setAdapter(ListAdapter adapter) {
		if (adapter != null) {
			listView.addFooterView(footerView);
			isFootViewRemoved = false;
		}
		listView.setAdapter(adapter);

	}

	public void initView() {
		mRotateUpAnimation = AnimationUtils.loadAnimation(this.getContext(),
				R.anim.rotate_up);
		mRotateDownAnimation = AnimationUtils.loadAnimation(this.getContext(),
				R.anim.rotate_down);

		mPullLayout = (PullActivateLayout) findViewById(R.id.pull_container);
		listView = (ListView) findViewById(R.id.list_view);
		// listView.setOnScrollListener(new OnScollListener());
		// listView.setDivider(this.getResources().getDrawable(
		// R.drawable.com_solid_line_img));
		listView.setDrawingCacheEnabled(false);
		mActionView = findViewById(R.id.action_view);
		footerView = new PullListViewFooter(this.getContext());
		footerView.initView(R.layout.com_xlistview_footer,
				new OnClickListener() {
					public void onClick(View v) {
						onBottomShow();
					}
				});
		mProgress = findViewById(android.R.id.progress);
		mActionImage = findViewById(android.R.id.icon);
		mActionText = (TextView) findViewById(R.id.pull_note);
		mTimeText = (TextView) findViewById(R.id.refresh_time);

		mTimeText.setText(R.string.note_not_update);
		mActionText.setText(R.string.note_pull_down);
		mPullLayout.setEnableStopInActionView(false);
	}

	public void onPullOut() {
		if (!mInLoading && isRefreashEnable) {
			mActionText.setText(R.string.note_pull_refresh);
			mActionImage.clearAnimation();
			mActionImage.startAnimation(mRotateUpAnimation);
		}
	}

	public void onPullIn() {
		if (!mInLoading && isRefreashEnable) {
			mActionText.setText(R.string.note_pull_down);
			mActionImage.clearAnimation();
			mActionImage.startAnimation(mRotateDownAnimation);
		}
	}

	private void startLoading() {
		if (!mInLoading && isRefreashEnable) {
			mInLoading = true;
			mPullLayout.setEnableStopInActionView(true);
			mActionImage.clearAnimation();
			mActionImage.setVisibility(View.GONE);
			mProgress.setVisibility(View.VISIBLE);
			mActionText.setText(this.getContext().getResources()
					.getString(R.string.note_pull_loading));
			mHandler.sendEmptyMessage(MSG_LOADING);
		}
	}

	private void dataLoaded() {
		if (mInLoading) {
			mInLoading = false;
			mPullLayout.setEnableStopInActionView(false);
			mPullLayout.hideActionView();
			mActionImage.setVisibility(View.VISIBLE);
			mProgress.setVisibility(View.GONE);

			if (mPullLayout.isPullOut()) {
				mActionText.setText(R.string.note_pull_refresh);
				mActionImage.clearAnimation();
				mActionImage.startAnimation(mRotateUpAnimation);
			} else {
				mActionText.setText(R.string.note_pull_down);
			}

			mTimeText.setText(String.format(
					this.getResources().getString(
							R.string.com_xlistview_header_last_time_str),
					new Date()));
		}
	}

	public void setPullLoadEnable(boolean isEnable) {
		this.isLoadMoreEnable = isEnable;
		if (!isLoadMoreEnable) {
			footerView.hide();
			footerView.setOnClickListener(null);
			this.listView.removeFooterView(footerView);
			isFootViewRemoved = true;
		} else {
			mPullLoading = false;
			if (isFootViewRemoved) {
				this.listView.addFooterView(footerView);
				isFootViewRemoved = false;
			}
			footerView.show();
			footerView.setState(PullListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			footerView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (!footerView.isLoading()) {
						onBottomShow();
					}
				}
			});
		}
	}

	@Override
	public void setPullRefreshEnable(boolean isEnable) {
		super.setPullRefreshEnable(isEnable);
		this.isRefreashEnable = isEnable;
		if (!isEnable) {
			mActionView.setVisibility(View.GONE);
		} else {
			mActionView.setVisibility(View.VISIBLE);
		}
	}

	public void setIOperationListener(IOperationListener listener) {
		this.listener = listener;
		setOnPullStateChangeListener(this);
		setOnActionPullListener(this);
	}

	public void onHide() {
		// TODO Auto-generated method stub

	}

	public void onShow() {
	}

	public void onSnapToTop() {
		startLoading();
	}

	public void stopRefresh() {
		mHandler.sendEmptyMessage(MSG_LOADED);
	}

	public void stopLoadMore() {
		onBottomFinished();
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IOperationListener {
		public void onRefresh();

		public void onLoadMore();
	}

	/*
	 * class OnScollListener implements OnScrollListener {
	 * 
	 * public void onScrollStateChanged(AbsListView view, int scrollState) { //
	 * 当不滚动时 if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { //
	 * 判断是否滚动到底部 if (view.getLastVisiblePosition() == view.getCount() - 1 &&
	 * isLoadMoreEnable == true && !mPullLoading) { onBottomShow(); } } }
	 * 
	 * public void onScroll(AbsListView view, int firstVisibleItem, int
	 * visibleItemCount, int totalItemCount) { // TODO Auto-generated method
	 * stub
	 * 
	 * } }
	 */

	public void onBottomShow() {
		mPullLoading = true;
		footerView.setState(PullListViewFooter.STATE_LOADING);
		if (listener != null) {
			listener.onLoadMore();
		}
	}

	public void onBottomFinished() {
		if (mPullLoading == true) {
			mPullLoading = false;
			footerView.setState(PullListViewFooter.STATE_NORMAL);
		}

	}

}
