package com.example.chen.myapplication.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;

import static com.example.chen.myapplication.app.bean.User.USER_INFO;

public class LoginActivity extends AppCompatActivity {

	EditText usernameEt;
	EditText passwordEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		usernameEt = (EditText) findViewById(R.id.et_login_username);
		passwordEt = (EditText) findViewById(R.id.et_login_password);

	}

	public void login(View view){
		String username = usernameEt.getText().toString().trim();
		String password = passwordEt.getText().toString().trim();

		if(("小明".equals(username) || "18482117193".equals(username)) && "123".equals(password)){
			ToastUtil.showToast("登录成功");
			User user = new User("小明", password, "先生", "18482117193");
			user.setUserId(1);
			PreferenceUtil.set(USER_INFO, user);

			Intent intent = new Intent(this, AppActivity.class);
			intent.putExtra("index",2);
			startActivity(intent);
			finish();//销毁当前页面
		}else {
			ToastUtil.showToast("用户名或者密码不正确，请重新输入");
		}

	}

}
