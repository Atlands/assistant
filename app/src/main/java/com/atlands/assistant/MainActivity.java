package com.atlands.assistant;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.atlands.assistant.db.DBManager;
import com.atlands.assistant.db.Onelevel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public DBManager dbHelper;
    private ViewPager viewPager;
    private BottomNavigationView bNavigation;
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
        List<Onelevel> movies = LitePal.findAll(Onelevel.class);
        for (Onelevel m:movies){
            Log.d("hhh", m.getName());
        }
        viewPager=findViewById(R.id.viewpager);
        bNavigation=findViewById(R.id.navigation_bottom);
        bNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.main_navi1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_navi2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.main_navi3:
                        viewPager.setCurrentItem(2);
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fgLists=new ArrayList<>(3);
        fgLists.add(new FragmentHome1());
        fgLists.add(new FragmentHome2());
        fgLists.add(new FragmentHome3());

        //设置适配器用于装载Fragment
        FragmentPagerAdapter mPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
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
}
