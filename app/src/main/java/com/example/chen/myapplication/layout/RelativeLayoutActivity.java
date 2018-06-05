package com.example.chen.myapplication.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.frame.eventbus.IntentServiceResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/4/23.
 */
public class RelativeLayoutActivity extends AppCompatActivity {
	@BindView(R.id.textView2)
	TextView textButterknife;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_demo);
		//绑定初始化ButterKnife
		ButterKnife.bind(this);
		textButterknife.setText("textButterknife");
	}

	@OnClick({R.id.textView2, R.id.img1})
	public void onClick(View v) {
		textButterknife.setText("被点击了...");
	}

	@Override
	protected void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);  //注：为了后面分析的方便我们对进行注册的对象取名订阅者，如这里的MainActivity.this对象
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void doThis(IntentServiceResult intentServiceResult) {
		ToastUtil.showToast(intentServiceResult.getResultValue());
	}


	public void sendEvent(View view) {
		EventBus.getDefault().post(new IntentServiceResult(24, "sendEvent done!!"));
		// 这里还可以跳转到另一个Activity 在那里使用Subscribe注解的方法处理
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
