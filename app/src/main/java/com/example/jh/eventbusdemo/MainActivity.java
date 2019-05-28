package com.example.jh.eventbusdemo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jh.eventbusdemo.base.BaseActivity;
import com.example.jh.eventbusdemo.event.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 本demo测试EventBus的使用
 * <p>
 * 首先导入eventbus第三方库
 * compile 'de.greenrobot:eventbus:3.0.0-beta1'
 *
 * 混淆报错:
 *  java.lang.RuntimeException:
 *  Unable to start activity ComponentInfo{com.example.jh.eventbusdemo/com.example.jh.eventbusdemo.MainActivity}:
 *  a.a.a.e: Subscriber class com.example.jh.eventbusdemo.MainActivity and its super classes have no public methods with the @Subscribe annotation
 *
 *  Caused by: a.a.a.e: Subscriber class com.example.jh.eventbusdemo.MainActivity and its super classes have no public methods with the @Subscribe annotation
 *
 *  美团打包方案;
 *
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "EventBus";

    private TextView mMessageView;
    // 广播
    private MessageBroadcastReceiver mBroadcastReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();

        mMessageView = (TextView) findViewById(R.id.message);

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
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnMessageEventPostThread(MessageEvent messageEvent) {
        Log.e(TAG, "OnMessageEventPostThread: " + messageEvent.getMessage());
        Log.e("PostThread", Thread.currentThread().getName());
    }

    //在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        Log.e(TAG, "OnMessageEventPostThread: " + messageEvent.getMessage());
        Log.e("MainThread", Thread.currentThread().getName());
    }

    //如果产生事件的是UI线程，则在新的线程中执行。如果产生事件的是非UI线程，则在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e(TAG, "OnMessageEventPostThread: " + messageEvent.getMessage());
        Log.e("BackgroundThread", Thread.currentThread().getName());
    }

    //无论产生事件的是否是UI线程，都在新的线程中执行
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.e(TAG, "OnMessageEventPostThread: " + messageEvent.getMessage());
        Log.e("Async", Thread.currentThread().getName());
    }

    public class MessageBroadcastReceiver extends BroadcastReceiver {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive", "receive =" +
                    intent.getStringExtra("message"));
            mMessageView.setText("Message from SecondActivity:"
                    + intent.getStringExtra("message"));
        }
    }


    // 5、取消消息订阅
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播注册
        unregisterReceiver(mBroadcastReceiver);
    }
}
