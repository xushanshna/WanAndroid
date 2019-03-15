package com.example.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.base.BaseArticle;
import com.example.wanandroid.base.BaseRvAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.net.ApiLoader;
import com.example.wanandroid.net.http.BaseObserver;
import com.example.wanandroid.utils.callback.InfiniteScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 微信文章列表
 */
public class ArticleActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private int id;//公众号id
    private List<ArticleBean> datas = new ArrayList<>();
    private ArticleAdapter adapter;
    private int currPage = 0;
    private boolean isNoMoreData = false;
    private boolean isDataLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        title.setText(getIntent().getStringExtra("name"));//公众号名称
        init();
        getArticleData();
    }

    private void init() {
        adapter = new ArticleAdapter(this, datas, R.layout.home_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListner(new BaseRvAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ArticleActivity.this, WebActivity.class);
                intent.putExtra("link", datas.get(position).getLink());
                intent.putExtra("title", datas.get(position).getTitle());
                startActivity(intent);

//                EventBus.getDefault().post(datas.get(position));

            }
        });
        recyclerView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            protected void onLoadMore() {
                onLoad();
            }

            @Override
            protected boolean isNoMoreData() {
                return isNoMoreData;
            }

            @Override
            protected boolean isDataLoading() {
                return isDataLoading;
            }
        });

        //下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                currPage = 0;
                getArticleData();
                isDataLoading = true;
            }
        });
    }

    private void onLoad() {
        if (!isDataLoading) {
            isDataLoading = true;
            currPage++;
            getArticleData();
        }
    }

    /**
     * 请求数据
     */
    private void getArticleData() {
        if (id == -1) return;
        new ApiLoader().getArticleList(id, currPage).subscribe(new BaseObserver<BaseArticle<List<ArticleBean>>>() {
            @Override
            public void onCompleted() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                isDataLoading = false;
            }

            @Override
            public void onNext(BaseArticle<List<ArticleBean>> listBaseArticle) {
                datas.addAll(listBaseArticle.getDatas());
                if (listBaseArticle.getCurPage() >= listBaseArticle.getTotal()) {
                    isNoMoreData = true;
                }
            }
        });
    }
}
