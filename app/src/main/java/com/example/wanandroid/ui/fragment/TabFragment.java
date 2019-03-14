package com.example.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabFragment extends Fragment {

    private Unbinder unBinder;
    boolean mIsPrepare = false;        //视图还没准备好
    boolean mIsVisible = false;        //不可见
    boolean mIsFirstLoad = true;    //第一次加载
    private View rootView;

    public static TabFragment newInstance() {
        return new TabFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        }
        unBinder = ButterKnife.bind(this, rootView);
        mIsPrepare = true;//视图加载完成
        lazyLoad();
        return rootView;
    }

    //这里进行网络请求和数据装载
    private void loadData() {

    }


    /**
     * fragment可见状态发生变化时回调
     * 该方法游离在Fragment生命周期之外的，有可能早于onCreate和onCreateView执行
     *
     * @param isVisibleToUser 当前fragment是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible) {
            lazyLoad();
        }
    }

    /**
     * 懒加载，满足以下条件
     * 1.View视图加载完毕，即onCreateView（）执行完成
     * 2.当前Fragment可见，即setUserVisibleHint（）的参数为true
     * 3.初次加载，即防止多次滑动重复加载
     */
    private void lazyLoad() {
        if (!mIsPrepare || !isVisible() || !mIsFirstLoad) return;
        loadData();
        //数据加载完毕,恢复标记,防止重复加载
        mIsFirstLoad = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
        //恢复标志位
        mIsFirstLoad = true;
        mIsPrepare = false;
        mIsVisible = false;
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("销毁Tabfragment");
    }
}
