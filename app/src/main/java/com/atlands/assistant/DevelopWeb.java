package com.atlands.assistant;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.atlands.assistant.adapter.Develop;
import com.atlands.assistant.adapter.DevelopReViewAdapter;
import com.atlands.assistant.adapter.MainReViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DevelopWeb extends AppCompatActivity {
    List<Develop> develops = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_web);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DevelopReViewAdapter adapter = new DevelopReViewAdapter(develops);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        Develop develop = new Develop(getResources().getString(R.string.csdn), getResources().getString(R.string.csdn_miaoshu),
                R.drawable.ic_develop_csdn, getResources().getString(R.string.csdn_url));
        develops.add(develop);
        Develop develop1 = new Develop(getResources().getString(R.string.jianshu), getResources().getString(R.string.jianshu_miaoshu),
                R.drawable.ic_develop_jianshu, getResources().getString(R.string.jianshu_url));
        develops.add(develop1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
