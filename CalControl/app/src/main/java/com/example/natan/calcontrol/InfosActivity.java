package com.example.natan.calcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfosActivity extends AppCompatActivity {

    private WebView mInfosWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        mInfosWebView = findViewById(R.id.wv_infos);
        mInfosWebView.setWebViewClient(new WebViewClient());
        mInfosWebView.loadUrl("https://dieta-alimentar.blogspot.com/2016/12/deficit-calorico.html");

    }
}
