package com.example.wanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HomeAdapter;
import com.example.wanandroid.adapter.base.BaseRvAdapter;
import com.example.wanandroid.bean.BaseArticle;
import com.example.wanandroid.bean.HomeArticle;
import com.example.wanandroid.net.ApiLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 微信文章列表
 */
public class ArticleActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int id;//公众号id
    private List<HomeArticle> datas = new ArrayList<>();
    private HomeAdapter adapter;


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
        adapter = new HomeAdapter(this, datas, R.layout.home_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new BaseRvAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ArticleActivity.this, WebActivity.class);
                intent.putExtra("link", datas.get(position).getLink());
                intent.putExtra("title", datas.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    private void getArticleData() {
        if (id == -1) return;
        ApiLoader apiLoader = new ApiLoader();
        apiLoader.getArticleList(id, 0)
                .subscribe(new Action1<BaseArticle<List<HomeArticle>>>() {
                    @Override
                    public void call(BaseArticle<List<HomeArticle>> listBaseArticle) {
                        datas.clear();
                        datas.addAll(listBaseArticle.getDatas());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
