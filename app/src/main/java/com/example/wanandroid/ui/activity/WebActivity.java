package com.example.wanandroid.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.bean.ArticleBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webview显示文章详情
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.web_view)
    WebView webView;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        link = getIntent().getStringExtra("link");
        title.setText(getIntent().getStringExtra("title"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getArticle(ArticleBean articleBean) {
        link = articleBean.getLink();
        title.setText(articleBean.getTitle());
        Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
    }
}
