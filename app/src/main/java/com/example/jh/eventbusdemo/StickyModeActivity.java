package com.example.jh.eventbusdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jh.eventbusdemo.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 作者：jinhui on 2017/3/23
 * 邮箱：1004260403@qq.com
 * <p>
 * 粘性事件
 */

public class StickyModeActivity extends AppCompatActivity {

    int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_mode);

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("test: " + index++));
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean registered = EventBus.getDefault().isRegistered(StickyModeActivity.this);
                if (registered) {
                    // 这里点击过后不能再次点击，会报异常，因为已经register过了，可以点击unregister继续操作。
                    Toast.makeText(StickyModeActivity.this, "已经register啦", Toast.LENGTH_SHORT).show();
                } else {
                    EventBus.getDefault().register(StickyModeActivity.this);
                }
            }
        });

        findViewById(R.id.unregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(StickyModeActivity.this);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void OnMessageEventPostThread(MessageEvent messageEvent) {
        Log.e("PostThread sticky", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void OnMessageEventMainThread(MessageEvent messageEvent) {
        Log.e("MainThread sticky", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void OnMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("BackgroundThread sticky", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void OnMessageEventAsync(MessageEvent messageEvent) {
        Log.e("Async sticky", messageEvent.getMessage());
    }
}
