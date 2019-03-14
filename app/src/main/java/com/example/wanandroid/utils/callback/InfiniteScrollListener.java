package com.example.wanandroid.utils.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Administrator on 2019/3/14 0014.
 * 参考giffun，监听RecycleView滑动到底部时加载更多
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    // The minimum number of items remaining before we should loading more.
    private final int VISIBLE_THRESHOLD = 1;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy < 0 || isDataLoading() || isNoMoreData()) return;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = 0;

        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            firstVisibleItem = findMin(((StaggeredGridLayoutManager) layoutManager)
                    .findFirstVisibleItemPositions(null));
        }
        if (totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            onLoadMore();
        }
    }

    protected abstract void onLoadMore();

    protected abstract boolean isNoMoreData();

    protected abstract boolean isDataLoading();

    private int findMin(int[] array) {
        int min = array[0];
        for (int i : array) {
            if (i < min) min = i;
        }
        return min;
    }
}
