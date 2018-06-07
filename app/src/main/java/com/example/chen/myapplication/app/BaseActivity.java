package com.example.chen.myapplication.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.vondear.rxtools.view.dialog.RxDialogSure;
import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import static com.example.chen.myapplication.app.bean.User.USER_INFO;


public abstract class BaseActivity extends AppCompatActivity {

    IntentFilter intentFilter;
    NetWorkBroadcastReceiver netWorkBroadcastReceiver;
    public static EventBus eventBus  = EventBus.getDefault();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkBroadcastReceiver = new NetWorkBroadcastReceiver(this);
        registerReceiver(netWorkBroadcastReceiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    static class NetWorkBroadcastReceiver extends BroadcastReceiver {
        WeakReference<Activity> reference;

        public NetWorkBroadcastReceiver(Activity activity) {
            reference = new WeakReference(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Activity activity = reference.get();
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            final RxDialogSure rxDialogSure = new RxDialogSure(activity);//提示弹窗
            rxDialogSure.setTitle("提示");
            rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rxDialogSure.cancel();
                }
            });
            if(networkInfo != null && networkInfo.isAvailable()) {
                rxDialogSure.dismiss();
            }else {
                rxDialogSure.setContent("网络不可用，请检查网络设置");
                rxDialogSure.show();
            }

        }
    }


    public void checkLogin(){
        User user = PreferenceUtil.getObject(USER_INFO, User.class);
        if(user != null) {
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkBroadcastReceiver);
    }
}
