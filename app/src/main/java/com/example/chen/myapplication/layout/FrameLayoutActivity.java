package com.example.chen.myapplication.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.example.chen.myapplication.MainActivity;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.layout.view.MyImageView;

/**
 * Created by Administrator on 2017/4/23.
 */
public class FrameLayoutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framelayout_demo);
		FrameLayout frame = (FrameLayout) findViewById(R.id.framelayout_demo);
		final MyImageView mezi = new MyImageView(FrameLayoutActivity.this);
		//为我们的萌妹子添加触摸事件监听器
		mezi.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				//设置妹子显示的位置
				mezi.bitmapX = event.getX() - 150;
				mezi.bitmapY = event.getY() - 150;
				//调用重绘方法
				mezi.invalidate();
				return true;
			}
		});
		frame.addView(mezi);

	}
}
