package com.example.wanandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wanandroid.R;

/**
 * 微信文章列表
 */
public class ArticleActivity extends AppCompatActivity {
    private String id;//公众号id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        id = getIntent().getStringExtra("id");
    }
}
