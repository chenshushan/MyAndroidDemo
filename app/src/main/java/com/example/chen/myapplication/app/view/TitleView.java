package com.example.chen.myapplication.app.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;

public class TitleView extends RelativeLayout {

	private TextView titleView;
	private ImageView back;

	public TitleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.app_title, this);
		titleView = (TextView) findViewById(R.id.tv_title_label);
		back = (ImageView) findViewById(R.id.iv_title_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();// 返回上级
			}
		});
	}

	/**
	 * 设置标题
	 * @param text
	 */
	public void setTitleText(String text) {
		titleView.setText(text);
	}
	/**
	 * 隐藏返回按钮
	 */
	public void hideBackImage(){
		back.setVisibility(View.GONE);
	}
}
