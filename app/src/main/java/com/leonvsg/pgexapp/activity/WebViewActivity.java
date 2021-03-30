package com.leonvsg.pgexapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        addContentView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Uri url = getIntent().getData();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                boolean overrideUrlLoading = super.shouldOverrideUrlLoading(view, request);
                if (request.getUrl().getHost().contains("localhost")) finishActivity();
                else if (!request.getUrl().getScheme().contains("http")) {
                    startActivity(request.getUrl());
                    overrideUrlLoading = true;
                }
                return overrideUrlLoading;
            }
        });
        if (url != null && url.getScheme() != null && !url.getScheme().contains("http")) {
            startActivity(url);
            finishActivity();
        }
        webView.loadUrl(url.toString());
    }

    private void finishActivity(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startActivity(Uri uri) {
        try {
            webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ошибка в URI", Toast.LENGTH_LONG).show();
        }
    }
}