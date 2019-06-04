package com.atlands.assistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.atlands.assistant.db.Contentlist;

import org.litepal.LitePal;

import java.util.List;

public class ContentPaper extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_paper);
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        List<Contentlist> contentlists= LitePal.where("name = ?",name).find(Contentlist.class);
        for (Contentlist contentlist:contentlists){
            webView.loadUrl(contentlist.getLink());
            break;
        }
        //webView.loadUrl(contentlists.get(1).getLink());
    }
}
