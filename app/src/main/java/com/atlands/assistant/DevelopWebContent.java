package com.atlands.assistant;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;

import java.util.Objects;

public class DevelopWebContent extends AppCompatActivity {
    private AgentWeb mAgentWeb;
    private String current = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_web_content);
        TextView textView = findViewById(R.id.bar_title);
        textView.setVisibility(View.GONE);
        LinearLayout layout = findViewById(R.id.link_web);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(null);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black);
        }
        toolbar.setTitle(R.string.drawer1);
        Intent intent = getIntent();
        String urlLink = intent.getStringExtra("url");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(urlLink);
        //mWebViewClient.onPageStarted(mAgentWeb,urlLink,null);
        //获取网页的标题
        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                current = url;
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.develop_content_menu, menu);
        return true;
    }

    //标题栏菜单实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.copy:
                //获取剪贴版
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                //第一个参数只是一个标记，随便传入。
                //第二个参数是要复制到剪贴版的内容
                ClipData clip = ClipData.newPlainText("simple text ", current);
                //传入clipdata对象.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DevelopWebContent.this, getResources().getString(R.string.toast_succes_copy), Toast.LENGTH_SHORT).show();
                break;
            case R.id.skip_web:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri skip_uri = Uri.parse(current);
                intent.setData(skip_uri);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //返回键实现
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (mAgentWeb.handleKeyEvent(keyCode, keyEvent)) {
            return true;
        }
        return super.onKeyDown(keyCode, keyEvent);
    }
}
