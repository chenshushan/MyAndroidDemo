package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.app.view.TitleView;

import static com.example.chen.myapplication.app.AppActivity.HOME_PAGE;
import static com.example.chen.myapplication.app.bean.User.USER_INFO;

// 个人中心
public class UserCenterActivity extends BaseActivity {


	TextView username;
	TextView phone;
	TextView sex;

	TitleView titleView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkLogin();

		setContentView(R.layout.activity_user_center);
		username = (TextView) findViewById(R.id.tv_username);
		phone = (TextView) findViewById(R.id.tv_phone);
		sex = (TextView) findViewById(R.id.tv_sex);
		titleView = (TitleView) findViewById(R.id.rl_accountdetail_top);
		titleView.setTitleText("个人中心");

		User user = PreferenceUtil.getObject(USER_INFO, User.class);
		username.setText(user.getUsername());
		phone.setText(user.getPhone());
		sex.setText(user.getSex());
	}

	public void logout(View view){
		PreferenceUtil.set(USER_INFO, "");
		ToastUtil.showToast("退出成功");
		Intent intent = new Intent(this, AppActivity.class);
		intent.putExtra(HOME_PAGE, 2);
		startActivity(intent);
	}
}
