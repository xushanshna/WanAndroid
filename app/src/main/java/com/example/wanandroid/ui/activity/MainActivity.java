package com.example.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ViewPagerAdapter;
import com.example.wanandroid.ui.fragment.HomeFragment;
import com.example.wanandroid.ui.fragment.NavFragment;
import com.example.wanandroid.ui.fragment.ProjectFragment;
import com.example.wanandroid.ui.fragment.SystemFragment;
import com.example.wanandroid.ui.fragment.WeChatFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_bnv)
    BottomNavigationView bnv;
    @BindView(R.id.main_vp)
    ViewPager viewPager;
    private MenuItem mMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        List<Fragment> list = new ArrayList<>();
        list.add(HomeFragment.newInstance(getString(R.string.home)));
        list.add(SystemFragment.newInstance(getString(R.string.system)));
        list.add(WeChatFragment.newInstance(getString(R.string.wechat)));
        list.add(NavFragment.newInstance(getString(R.string.nav)));
        list.add(ProjectFragment.newInstance());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
        initListener();
    }

    private void initListener() {
        //viewpager监听，联动bnv
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    bnv.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = bnv.getMenu().getItem(i);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        //bnv监听,联动vp
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mMenuItem = menuItem;
                switch (menuItem.getItemId()) {
                    case R.id.bnv_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bnv_system:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.bnv_wechat:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bnv_nav:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.bnv_project:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this,
                        "再按一次退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
