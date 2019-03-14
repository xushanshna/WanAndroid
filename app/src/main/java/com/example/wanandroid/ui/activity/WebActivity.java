package com.example.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webview显示文章详情
 */
public class WebActivity extends AppCompatActivity {
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
}
