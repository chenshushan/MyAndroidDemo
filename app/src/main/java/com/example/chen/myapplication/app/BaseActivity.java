package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;

import static com.example.chen.myapplication.app.bean.User.USER_INFO;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finish();
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
}
