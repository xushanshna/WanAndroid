package com.example.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.TabBean;
import com.example.wanandroid.net.ApiLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

public class ProjectFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unBinder;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        unBinder = ButterKnife.bind(this, view);
        getTabData();
        return view;
    }

    /**
     * 关联tablayout和viewpager
     */
    private void linkTabAndVp() {
//        limit的值代表着还要缓存当前Fragment左右各limit个Fragment，
//        一共会创建2*limit+1个Fragment。超出这个limit范围的Fragment就会被销毁
        viewPager.setOffscreenPageLimit(2);
//        FragmentStatePagerAdapter销毁时回调ondestory,需要手动保存状态
//        FragmentPagerAdapter不回调ondestory,
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getTabData() {
        ApiLoader apiLoader = new ApiLoader();
        apiLoader.getTab().subscribe(new Action1<List<TabBean>>() {
            @Override
            public void call(List<TabBean> tabBeans) {
                titles.clear();
                fragments.clear();
                for (TabBean tabBean : tabBeans) {
                    titles.add(tabBean.getName());
                    fragments.add(TabFragment.newInstance());
                }
                linkTabAndVp();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }
}
