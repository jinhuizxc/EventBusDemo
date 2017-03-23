package com.example.jh.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 本demo测试EventBus的使用
 * <p>
 * 首先导入eventbus第三方库
 * compile 'de.greenrobot:eventbus:3.0.0-beta1'
 */
public class MainActivity extends AppCompatActivity {

    private TextView mMessageView;
    // 广播
    private MessageBroadcastReceiver mBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageView = (TextView) findViewById(R.id.message);
        // 2、在需要订阅事件的地方注册事件
        EventBus.getDefault().register(this);
        //通过点击按钮产生事件
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StickyModeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter intentFilter = new IntentFilter("message_broadcast");
        mBroadcastReceiver = new MessageBroadcastReceiver();
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    /**
     * 通过产生事件，发送消息后，
     * 下面这些方法依次被执行：
     * OnMessageEventPostThread
     * onMessageEventAsync
     * onMessageEventMainThread
     * onMessageEventBackgroundThread
     *
     * @param messageEvent
     */
    // 4、处理消息
    //在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void OnMessageEventPostThread(MessageEvent messageEvent){
        Log.e("PostThread", Thread.currentThread().getName());
    }
    //在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        Log.e("MainThread", Thread.currentThread().getName());
    }

    //如果产生事件的是UI线程，则在新的线程中执行。如果产生事件的是非UI线程，则在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("BackgroundThread", Thread.currentThread().getName());
    }

    //无论产生事件的是否是UI线程，都在新的线程中执行
    @Subscribe(threadMode = ThreadMode.Async)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.e("Async", Thread.currentThread().getName());
    }

    public class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive", "receive =" + intent.getStringExtra("message"));
            mMessageView.setText("Message from SecondActivity:" + intent.getStringExtra("message"));
        }
    }



    // 5、取消消息订阅
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
        //取消广播注册
        unregisterReceiver(mBroadcastReceiver);
    }
}
