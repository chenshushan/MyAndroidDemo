package com.example.chen.myapplication.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.app.view.TitleView;

import static com.example.chen.myapplication.app.bean.User.USER_INFO;

// 个人中心
public class UserCenterActivity extends AppCompatActivity {


	TextView username;
	TextView phone;
	TextView sex;

	TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center);
		username = (TextView) findViewById(R.id.tv_username);
		phone = (TextView) findViewById(R.id.tv_phone);
		sex = (TextView) findViewById(R.id.tv_sex);
		titleView = (TitleView) findViewById(R.id.rl_accountdetail_top);
		titleView.setTitleText("个人中心");

		User user = PreferenceUtil.getObject(USER_INFO, User.class);
		if(user == null) {
			finish();
			ToastUtil.showToast("请登录");
			return;
		}
		username.setText(user.getUsername());
		phone.setText(user.getPhone());
		sex.setText(user.getSex());
	}

	public void logout(View view){
		PreferenceUtil.set(USER_INFO, "");
		ToastUtil.showToast("退出成功");
		finish();
	}
}
