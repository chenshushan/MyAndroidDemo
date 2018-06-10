package com.example.chen.myapplication.app.service;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {
	public static <T> ObservableTransformer<T, T> compose() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				return observable
						.subscribeOn(Schedulers.io())
//							.doOnSubscribe(new Consumer<Disposable>() {
//								@Override
//								public void accept(Disposable disposable) throws Exception {
//									if (!Utils.isNetworkConnected()) {
//										ToastUtil.showToast("网络连接错误！！");
//									}
//								}
//							})
						.observeOn(AndroidSchedulers.mainThread());
			}
		};
	}
}
