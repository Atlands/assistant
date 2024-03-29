package com.atlands.assistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.atlands.assistant.db.DBManager;

import org.litepal.LitePal;

import java.lang.ref.WeakReference;
import java.text.BreakIterator;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class AdStart extends AppCompatActivity {
    private TextView textView;
    private int count = 1;
   // private final MyHandler mHandler = new MyHandler(this);

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //导入数据库
                DBManager dbHelper = new DBManager(AdStart.this);
                dbHelper.openDatabase();
                dbHelper.closeDatabase();
                //LitePal初始化
                LitePal.initialize(AdStart.this);
            }
        }).start();


        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_ad_start);
        // 初始化控件对象
        textView = findViewById(R.id.ad_skip);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                getCount();
            }
        });
        textView.setVisibility(View.GONE);
        /*new Thread(new Runnable() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                for (; count <= 0; count--) {
                    mHandler.handleMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
       // handler.sendEmptyMessageDelayed(0, 1000);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mHandler.sendEmptyMessageDelayed(0, 1000);
//            }
//        }).start();
        getCount();
    }

    private int getCount() {
        count--;
        if (count == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return count;
    }

    /*private static class MyHandler extends Handler {
        private final WeakReference<AdStart> mActivity;

        MyHandler(AdStart activity) {
            mActivity = new WeakReference<AdStart>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AdStart activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 0) {
                    activity.textView.setText(activity.getCount() + R.string.ad_text_skip);
                    activity.mHandler.sendEmptyMessageDelayed(0, 1000);
                    // activity.mHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        }
    }*/


   /* Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                textView.setText(getCount() + R.string.ad_text_skip);
                handler.sendEmptyMessageDelayed(0, 1000);
            }

        }

        ;
    };*/

}