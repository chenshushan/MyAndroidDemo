package com.example.chen.myapplication.app.util.network;

import android.util.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
// 请求回调
public  class RequestCallback<T> implements Subscriber<T> {

    private static final String TAG = "RequestCallback";

    @Override
    public final void onNext(T t) {
        onResponse(t);
    }

    @Override
    public final void onError(Throwable throwable) {
        Log.e(TAG, "throwable isn't instance of ResponseError");
    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onComplete() {

    }

    public void onResponse(T response) {}

}