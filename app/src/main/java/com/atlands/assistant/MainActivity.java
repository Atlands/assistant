package com.atlands.assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bNavigation;
    private TextView textView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //控件初始化
        initView();

        preferences = getSharedPreferences("main_isRenovateViewPager", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt("is_renovate_ViewPager", 10);
        editor.apply();

        initViewPager();
        //底部导航点击事件
        bNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_navi1:
                        whatFagment(1);
                        break;
                    case R.id.main_navi2:
                        whatFagment(2);
                        break;
                    case R.id.main_navi3:
                        whatFagment(3);
                        break;
                }
                return false;
            }
        });

        //侧滑菜单点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent drawerItem = null;
                switch (menuItem.getItemId()) {
                    case R.id.drawer1:
                        drawerItem = new Intent(MainActivity.this, DevelopWeb.class);
                        break;
                    case R.id.drawer2:
                        drawerItem = new Intent(MainActivity.this, AddPager.class);
                        break;
                    case R.id.drawer3:
                        drawerItem = new Intent(MainActivity.this, About.class);
                        break;
                    default:
                        break;
                }
                startActivity(drawerItem);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (preferences.getBoolean("is_renovate_ViewPager", false)) {
            editor.putBoolean("is_renovate_ViewPager", false);
            editor.commit();
            Log.d("hhh_main", "resume_renovate");
        }
        whatFagment(preferences.getInt("decide_what_fragment", 1));*/
    }

    //ViewPager初始化
    private void initViewPager() {
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
            public int getItemPosition(@NonNull Object object) {
//                switch (preferences.getInt("is_renovate_ViewPager",10)){
//                    case 1:
//                        if (viewPager.getCurrentItem()==1)
//                }
                return POSITION_NONE;
            }

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

    //private void reflashData(int type,L)
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
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    //根据i确定显示第几个fragment
    private void whatFagment(int i) {
        switch (i){
            case 1:
                viewPager.setCurrentItem(0);
                textView.setText(R.string.navigation1);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                textView.setText(R.string.navigation2);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                textView.setText(R.string.navigation3);
                break;
            default:
                break;
        }
    }
}
