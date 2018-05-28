package com.example.chen.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/4/19.
 */
public class MyApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		// 吐司初始化
		ToastUtil.init(this);

		// 本地存储工具类初始化
		PreferenceUtil.init(this, new Gson());
	}

	public static Context getContext(){
		return context;
	}
}