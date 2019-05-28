package com.example.jh.eventbusdemo.event;

/**
 * 作者：jinhui on 2017/3/23
 * 邮箱：1004260403@qq.com
 *
 * EventBus是针一款对Android的发布/订阅事件总线。
 * 它可以让我们很轻松的实现在Android各个组件之间传递消息，并且代码的可读性更好，耦合度更低。
 */
// 1.首先需要定义一个消息类，该类可以不继承任何基类也不需要实现任何接口
public class MessageEvent {

    private String message;

    // 构造方法中声明属性实例
    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
