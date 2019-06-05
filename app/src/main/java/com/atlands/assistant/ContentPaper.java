package com.atlands.assistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.atlands.assistant.db.Contentlist;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebView;

import org.litepal.LitePal;

import java.util.List;

public class ContentPaper extends AppCompatActivity {

    private LinearLayout view;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_paper);
        view = findViewById(R.id.link_web);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        List<Contentlist> contentlists = LitePal.where("name = ?", name).find(Contentlist.class);
        for (Contentlist contentlist : contentlists) {
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(contentlist.getLink());
            break;
        }
    }
}
