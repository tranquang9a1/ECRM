package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.*;


import com.fu.group10.capstone.apps.teachermobileapp.R;

import java.util.Collection;

/**
 * Created by trungdq on 06/08/2014.
 *
 * How to use:
 * Nothing special, just remember to implement IInfinityAdapter to the adapter.
 *
 * InfinityListView listView = (InfinityListView) rootView.findViewById(R.id.listView);
 * List<LiveItem> items = new ArrayList<LiveItem>();
 * LiveAdapter adapter = new LiveAdapter(getAppContext(), items);
 * listView.setEmptyView(rootView.findViewById(R.id.empty_list));
 * listView.setAdapter(adapter);
 *
 * @author huynhquangthao@gmail.com, trungdq88@gmail.com
 */
public class InfinityListView extends ListView implements AbsListView.OnScrollListener {
    /**
     * Adapter for this ListView
     */
    BaseAdapter adapter;

    /**
     * Responsible to send message to UI thread after heavy item loading
     */
    LoadingHandler mHandler;

    /**
     * Footer view, where show the loading effect
     */
    View footer;

    /**
     * true: the list is loading more items
     */
    private boolean isLoading = false;


    /**
     * First item number to load
     */
    private int firstLoadingCount = 10;

    /**
     * Number of item to load at a time
     */
    private int numPerLoading = 10;

    /**
     * Item holder between loadings (because loading will be ran in another thread)
     * - Will be filled up when loading (in another thread)
     * - Will be cleared after append data to adapter
     */
    private Collection _loadedItems;


    // We need 3 constructor to be able to use this in layout xml file

    //region Constructors
    public InfinityListView(Context context) {
        super(context);
        setUpListView();
    }

    public InfinityListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpListView();
    }

    public InfinityListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUpListView();
    }
    //endregion

    /**
     * Set your custom firstLoadingCount outside
     * @param firstLoadingCount
     */
    public void setFirstLoadingCount(int firstLoadingCount) {
        this.firstLoadingCount = firstLoadingCount;
    }

    /**
     * Set your custom numPerLoading outside
     * @param numPerLoading
     */
    public void setNumPerLoading(int numPerLoading) {
        this.numPerLoading = numPerLoading;
    }

    /**
     * Init stuffs
     */
    private void setUpListView() {
        // To handle loadMore when scroll to bottom
        setOnScrollListener(this);
        // Create LoadingHandler instance
        mHandler = new LoadingHandler();
        // Add the loading footer view
        setUpFooter();
    }

    /**
     * Generate footer (if exists) and add to list.
     */
    private void setUpFooter() {
        if (footer == null) {
            footer = generateFooterView();
        }
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prevent default clicking event to footer view
            }
        });

        if (getFooterViewsCount() == 0) {
            addFooterView(footer);
        }
    }

    public void setFooterView(View v) {
        footer = v;
    }

    /**
     * Set adapter and load firstLoadingCount items
     * @param _adapter
     */
    @Override
    public void setAdapter(ListAdapter _adapter) {
        super.setAdapter(_adapter);
        if (_adapter == null) return;

        this.adapter = (BaseAdapter) _adapter;
        if (adapter.getCount() == 0) {
            loadItems(0, firstLoadingCount);
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * Load item at position `index`, takes `num` result(s)
     * and then add them to the list
     *
     * @param index position of the first item to load
     * @param num number of items to load
     */
    private void loadItems(final int index, final int num) {
        /**  waiting for ending session */
        if (isLoading) return;
        isLoading = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Just to make sure the list is not loading too fast
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                _loadedItems = ((IInfinityAdapter) adapter).loadMore(index, num);
                // Notify data set to UI
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        });
        t.start();
    }

    /**
     * Basic footer view is only a ProcessBar (the loading circle)
     * @return
     */
    private View generateFooterView() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        ProgressBar loading = new ProgressBar(getContext());
        loading.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        layout.addView(loading);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        layout.setPadding(0, 0, 0, dpAsPixels);
        return layout;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    /**
     * Handler load more (or finish load) when scrolling to the bottom
     * @param absListView
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null) return;
        if (!updateFooterView()) {
            return;
        }
        if (getAdapter().getCount() == 0) return;

        // get the first Item that currently hide and need to show
        final int firstItemHide = firstVisibleItem + visibleItemCount;
        // Log.d("demo", "FirstVisibleItem:" + firstVisibleItem + "  VisibleItemCount:"
        //        + visibleItemCount + "  TotalItemCount:" + totalItemCount);
        if (firstItemHide >= totalItemCount) {
            loadItems(totalItemCount - 1, numPerLoading); // Minus for 1 because of the footer is also counted
        }
    }

    /**
     * Return false if no more data, true if still have data
     * @return
     */
    private boolean updateFooterView() {
        if (!((IInfinityAdapter) adapter).hasMoreData()) {
            if (getFooterViewsCount() > 0) {
                removeFooterView(footer);
            }
            return false;
        }
        return true;
    }

    /**
     * This list view require IInfinityAdapter for its adapter
     */
    public interface IInfinityAdapter {
        public void addItems(Collection<Object> objs);

        public Collection loadMore(int offset, int count);

        public boolean hasMoreData();
    }

    /**
     * Responsible to send message to UI thread after heavy item loading
     */
    private class LoadingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            isLoading = false;
            ((IInfinityAdapter) adapter).addItems(_loadedItems);
            _loadedItems.clear();
            updateFooterView();
            adapter.notifyDataSetChanged();
            updateEmptyView();
        }
    }

    /**
     * If you want to apply empty view for InfinityListView, make sure it follow this structure
     * <View
     *  id="yourEmptyView">
     *      <View id="loadingImg"></View>
     *      <View id="emptyMessage"></View>
     *  </View>
     */
    private void updateEmptyView() {
        if (adapter.isEmpty() && getEmptyView() != null) {
            getEmptyView().findViewById(R.id.loadingImg).setVisibility(GONE);
            getEmptyView().findViewById(R.id.emptyMessage).setVisibility(VISIBLE);
        }
    }
}
