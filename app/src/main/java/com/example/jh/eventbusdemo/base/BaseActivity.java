package com.example.jh.eventbusdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jh.eventbusdemo.event.EmptyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 如何获取渠道信息
 * 在需要渠道等信息时可以通过下面代码进行获取
 * String channel = WalleChannelReader.getChannel(this.getApplicationContext());
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 2、在需要订阅事件的地方注册事件
        EventBus.getDefault().register(this);

        init();

    }

    protected void init() {
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmpty(EmptyEvent event){

    }
}
