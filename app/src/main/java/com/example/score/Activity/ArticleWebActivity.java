package com.example.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.score.R;

public class ArticleWebActivity extends FragmentActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_web);

        webView = (WebView)findViewById(R.id.article_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra("article_bundle");
        String url = bundleExtra.getString("articleUrl");

        webView.loadUrl(url);

    }
}
