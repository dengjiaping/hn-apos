package me.andpay.apos.cmview;

import java.util.LinkedList;
import java.util.List;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

public abstract class TiSectionListAdapter<T> extends BaseAdapter implements
		SectionIndexer, OnScrollListener {

	public static final String TAG = TiSectionListAdapter.class.getSimpleName();

	protected LinkedList<Pair<String, LinkedList<T>>> all = new LinkedList<Pair<String, LinkedList<T>>>();

	public interface HasMorePagesListener {
		void noMorePages();

		void mayHaveMorePages();
	}

	/**
	 * The <em>current</em> page, not the page that is going to be loaded.
	 */
	int page = 1;
	int initialPage = 1;
	boolean automaticNextPageLoading = false;
	HasMorePagesListener hasMorePagesListener;

	void setHasMorePagesListener(HasMorePagesListener hasMorePagesListener) {
		this.hasMorePagesListener = hasMorePagesListener;
	}

	/**
	 * Pinned header state: don't show the header.
	 */
	public static final int PINNED_HEADER_GONE = 0;

	/**
	 * Pinned header state: show the header at the top of the list.
	 */
	public static final int PINNED_HEADER_VISIBLE = 1;

	/**
	 * Pinned header state: show the header. If the header extends beyond the
	 * bottom of the first shown element, push it up and clip.
	 */
	public static final int PINNED_HEADER_PUSHED_UP = 2;

	/**
	 * Computes the desired state of the pinned header for the given position of
	 * the first visible list item. Allowed return values are
	 * {@link #PINNED_HEADER_GONE}, {@link #PINNED_HEADER_VISIBLE} or
	 * {@link #PINNED_HEADER_PUSHED_UP}.
	 */
	public int getPinnedHeaderState(int position) {
		if (position < 0 || getCount() == 0) {
			return PINNED_HEADER_GONE;
		}

		// The header should get pushed up if the top item shown
		// is the last item in a section for a particular letter.
		int section = getSectionForPosition(position);
		int nextSectionPosition = getPositionForSection(section + 1);
		if (nextSectionPosition != -1 && position == nextSectionPosition - 1) {
			return PINNED_HEADER_PUSHED_UP;
		}

		return PINNED_HEADER_VISIBLE;
	}

	/**
	 * Sets the initial page when {@link #resetPage()} is called. Default is 1
	 * (for APIs with 1-based page number).
	 */
	public void setInitialPage(int initialPage) {
		this.initialPage = initialPage;
	}

	/**
	 * Resets the current page to the page specified in
	 * {@link #setInitialPage(int)}.
	 */
	public void resetPage() {
		this.page = this.initialPage;
	}

	/**
	 * Increases the current page number.
	 */
	public void nextPage() {
		this.page++;
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (view instanceof TiSectionListView) {
			((TiSectionListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nop
	}

	public int getCount() {
		int res = 0;
		for (int i = 0; i < getSectionCount(); i++) {
			res += getSectionItemCount(i);
		}
		return res;
	}

	public int getSectionItemIndex(int position) {
		int c = 0;
		for (int i = 0; i < getSectionCount(); i++) {
			if (position >= c && position < c + getSectionItemCount(i)) {
				break;
			}
			c += getSectionItemCount(i);
		}
		return position - c;
	}

	public int getPositionForSection(int section) {
		if (section < 0)
			section = 0;
		if (section >= getSectionCount())
			section = getSectionCount() - 1;
		int c = 0;
		for (int i = 0; i < getSectionCount(); i++) {
			if (section == i) {
				return c;
			}
			c += getSectionItemCount(i);
		}
		return 0;
	}

	public int getSectionForPosition(int position) {
		int c = 0;
		for (int i = 0; i < getSectionCount(); i++) {
			if (position >= c && position < c + getSectionItemCount(i)) {
				return i;
			}
			c += getSectionItemCount(i);
		}
		return -1;
	}

	public final View getView(int position, View convertView, ViewGroup parent) {
		View res = getSectionItemView(getSectionForPosition(position),
				this.getSectionItemIndex(position), convertView, parent);

		if (position == getCount() - 1 && automaticNextPageLoading) {
			onNextPageRequested(page + 1);
		}

		final int section = getSectionForPosition(position);
		boolean displaySectionHeaders = (getPositionForSection(section) == position);

		bindSectionHeader(res, getSectionForPosition(position),
				displaySectionHeaders);

		return res;
	}

	public Object getItem(int position) {
		return this.getSectionItem(getSectionForPosition(position),
				getSectionItemIndex(position));
	}

	public long getItemId(int position) {
		return getItemId(getSectionForPosition(position),
				getSectionItemIndex(position));
	}

	public void notifyNoMorePages() {
		automaticNextPageLoading = false;
		if (hasMorePagesListener != null)
			hasMorePagesListener.noMorePages();
	}

	public void notifyMayHaveMorePages() {
		automaticNextPageLoading = true;
		if (hasMorePagesListener != null)
			hasMorePagesListener.mayHaveMorePages();
	}

	/**
	 * Configures the pinned header view to match the first visible list item.
	 * 
	 * @param header
	 *            pinned header view.
	 * @param position
	 *            position of the first visible list item.
	 * @param alpha
	 *            fading of the header view, between 0 and 255.
	 */
	public void configurePinnedHeader(View header, int position, int alpha) {
		this.configureSectionView(header, getSectionForPosition(position),
				alpha);
	}

	/**
	 * The last item on the list is requested to be seen, so do the request and
	 * call {@link AmazingListView#tellNoMoreData()} if there is no more pages.
	 * 
	 * @param page
	 *            the page number to load.
	 */
	protected void onNextPageRequested(int page) {

	}

	public int getSectionCount() {
		return all.size();
	}

	public int getSectionItemCount(int section) {
		return all.get(section).second.size();
	}

	public T getSectionItem(int sectionIndex, int itemIndex) {
		return all.get(sectionIndex).second.get(itemIndex);
	}

	public void configureSectionView(View header, int section, int alpha) {

	}

	public void addValues(List<T> infos) {
		for (T info : infos) {
			this.addValue(info, false);
		}
	}

	/**
	 * 按照先后顺序添加对象
	 * 
	 * @param info
	 * @param isAdd2First
	 */
	public void addValue(T info, boolean isAdd2First) {
		String sectionStr = getSectionDesc(info);
		for (Pair<String, LinkedList<T>> infos : all) {
			if (!infos.first.equalsIgnoreCase(sectionStr)) {
				continue;
			}
			if (isAdd2First) {
				infos.second.addFirst(info);
			} else {
				infos.second.addLast(info);
			}
			return;
		}
		LinkedList<T> infos = new LinkedList<T>();
		infos.add(info);
		Pair<String, LinkedList<T>> pair = new Pair<String, LinkedList<T>>(
				sectionStr, infos);
		if (isAdd2First) {
			all.addFirst(pair);
		} else {
			all.addLast(pair);
		}
	}

	public void destory() {
		for (Pair<String, LinkedList<T>> infos : all) {
			infos.second.clear();
		}
		all.clear();
	}

	public Object[] getSections() {
		String[] sectionInfos = new String[all.size()];
		for (int i = 0; i < all.size(); i++) {
			sectionInfos[i] = all.get(i).first;
		}
		return sectionInfos;
	}

	/**
	 * Configure the view (a listview item) to display headers or not based on
	 * displaySectionHeader (e.g. if displaySectionHeader
	 * header.setVisibility(VISIBLE) else header.setVisibility(GONE)).
	 */
	protected abstract void bindSectionHeader(View view, int sectionIdex,
			boolean displaySectionHeader);

	public abstract long getItemId(int section, int itemIndex);

	public abstract String getSectionDesc(T info);

	public abstract View getSectionItemView(int sectionIndex, int itemIndex,
			View convertView, ViewGroup parent);

}
