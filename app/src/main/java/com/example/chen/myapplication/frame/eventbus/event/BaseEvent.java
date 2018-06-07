package com.example.chen.myapplication.frame.eventbus.event;

/**
 * Created by kongweiwei on 2017/6/5.
 */

public class BaseEvent<T> {

    public int WHAT = 1;

    T t;

    private BaseEvent(){
    }

    public static BaseEvent build(int what) {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.WHAT = what;
        return baseEvent;
    }

    public BaseEvent setData(T t) {
        this.t = t;
        return this;
    }

    public T getData() {
        return t;
    }
}
