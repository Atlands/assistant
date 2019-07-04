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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_web);
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
        DevelopReViewAdapter adapter=new DevelopReViewAdapter(initData());
        recyclerView.setAdapter(adapter);
    }

    private List<Develop> initData() {
        List<Develop> develops=new ArrayList<>();
        Develop develop=new Develop();
        develop.setIcon(R.drawable.ic_develop_csdn);
        develop.setTitle(getResources().getString(R.string.csdn));
        develop.setUrl(getResources().getString(R.string.csdn_url));
        develop.setRecommend(getResources().getString(R.string.csdn_miaoshu));
        develops.add(develop);
        develop.setIcon(R.drawable.ic_develop_jianshu);
        develop.setTitle(getResources().getString(R.string.jianshu));
        develop.setUrl(getResources().getString(R.string.jianshu_url));
        develop.setRecommend(getResources().getString(R.string.jianshu_miaoshu));
        develops.add(develop);
        return develops;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
