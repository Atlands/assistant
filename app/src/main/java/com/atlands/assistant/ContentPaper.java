package com.atlands.assistant;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlands.assistant.db.Contentlist;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;

public class ContentPaper extends AppCompatActivity {

    private LinearLayout view;
    private AgentWeb mAgentWeb;
    private Toolbar toolbar;
    private TextView textView;

    private List<Contentlist> contentlists;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_paper);

        view = findViewById(R.id.link_web);
        textView = findViewById(R.id.bar_title);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black);
        }
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView.setText(name);
        contentlists = LitePal.where("name = ?", name).find(Contentlist.class);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(contentlists.get(0).getLink());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (mAgentWeb.handleKeyEvent(keyCode, keyEvent)) {
            return true;
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                LitePal.delete(Contentlist.class, contentlists.get(0).getId());
                Toast.makeText(ContentPaper.this, getResources().getString(R.string.toast_succes_delete), Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences=getSharedPreferences("main_isRenovateViewPager",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("is_renovate_ViewPager",true);
                editor.apply();
                finish();
                break;
            case R.id.copy:
                //获取剪贴版
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                //第一个参数只是一个标记，随便传入。
                //第二个参数是要复制到剪贴版的内容
                ClipData clip = ClipData.newPlainText("simple text", contentlists.get(0).getLink());
                //传入clipdata对象.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ContentPaper.this, getResources().getString(R.string.toast_succes_copy), Toast.LENGTH_SHORT).show();
                break;
            case R.id.skip_web:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri skip_uri = Uri.parse(contentlists.get(0).getLink());
                intent.setData(skip_uri);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}