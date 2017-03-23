package com.example.jh.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

/**
 * 作者：jinhui on 2017/3/23
 * 邮箱：1004260403@qq.com
 */

public class SecondActivity extends AppCompatActivity {

    private EditText et_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        et_message = (EditText) findViewById(R.id.et_message);
        //发送消息
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("postEvent", Thread.currentThread().getName());  // main
                String message = et_message.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    message = "defaule message";
                    Log.e("send", "message =" + message);
                } else {
                    // 3、产生事件，即发送消息
                    EventBus.getDefault().post(new MessageEvent(message));
                    Log.e("send_notNull", "message =" + message);
                }
            }
        });

        // 发送广播消息
        findViewById(R.id.send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et_message.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    message = "defaule message";
                    Log.e("send_broadcast", "message =" + message);
                }else{
                    Intent intent = new Intent();
                    intent.setAction("message_broadcast");
                    intent.putExtra("message", message);
                    sendBroadcast(intent);
                }
            }

        });
    }

}
