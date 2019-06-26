package com.atlands.assistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.atlands.assistant.db.DBManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DBManager dbHelper;
    private ViewPager viewPager;
    private BottomNavigationView bNavigation;
    private TextView textView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    BottomNavigationItemView bitem;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //导入数据库
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        //LitePal初始化
        LitePal.initialize(this);

        //控件初始化
        initView();

        //默认首页
        textView.setText(R.string.navigation1);

        //底部导航点击事件
        bNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_navi1:
                        viewPager.setCurrentItem(0);
                        textView.setText(R.string.navigation1);
                        break;
                    case R.id.main_navi2:
                        viewPager.setCurrentItem(1);
                        textView.setText(R.string.navigation2);
                        break;
                    case R.id.main_navi3:
                        viewPager.setCurrentItem(2);
                        textView.setText(R.string.navigation3);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bNavigation.getMenu().getItem(i).setChecked(true);
                if (i == 0) textView.setText(R.string.navigation1);
                if (i == 1) textView.setText(R.string.navigation2);
                if (i == 2) textView.setText(R.string.navigation3);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fgLists = new ArrayList<>(3);
        fgLists.add(new FragmentHome1());
        fgLists.add(new FragmentHome2());
        fgLists.add(new FragmentHome3());

        //设置适配器用于装载Fragment
        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);  //得到Fragment
            }

            @Override
            public int getCount() {
                return fgLists.size();  //得到数量
            }
        };
        viewPager.setAdapter(mPagerAdapter);   //设置适配器
        viewPager.setOffscreenPageLimit(2); //预加载剩下两页

        //以上就将Fragment装入了ViewPager
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        bNavigation = findViewById(R.id.navigation_bottom);
        textView = findViewById(R.id.bar_title);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawer_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.drawer1:
                break;
            case R.id.drawer2:
                Intent intentAddPager = new Intent(MainActivity.this, AddPager.class);
                startActivity(intentAddPager);
            case R.id.drawer3:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
