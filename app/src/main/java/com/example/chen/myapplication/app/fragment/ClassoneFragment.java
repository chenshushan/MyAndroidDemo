package com.example.chen.myapplication.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ClassShopActivity;

@SuppressLint("ValidFragment")
public class ClassoneFragment extends Fragment {


	private Context context;
	private LayoutInflater inflater;

	public ClassoneFragment(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Fragment直接在xml中使用onClick失效 使用这种方法注册点击事件
		View view = inflater.inflate(R.layout.fg_classone, container, false);
		RelativeLayout one = (RelativeLayout) view.findViewById(R.id.rl_class_one_fastfood);
		one.setOnClickListener(new ClassClickListener());
		RelativeLayout two = (RelativeLayout) view.findViewById(R.id.rl_class_one_chinese);
		two.setOnClickListener(new ClassClickListener());
		RelativeLayout three = (RelativeLayout) view.findViewById(R.id.rl_class_one_chicken);
		three.setOnClickListener(new ClassClickListener());
		RelativeLayout four = (RelativeLayout) view.findViewById(R.id.rl_class_one_noodle);
		four.setOnClickListener(new ClassClickListener());
		RelativeLayout five = (RelativeLayout) view.findViewById(R.id.rl_class_one_place );
		five.setOnClickListener(new ClassClickListener());
		RelativeLayout six = (RelativeLayout) view.findViewById(R.id.rl_class_one_seafood );
		six.setOnClickListener(new ClassClickListener());
		RelativeLayout seven = (RelativeLayout) view.findViewById(R.id.rl_class_one_fruit );
		seven.setOnClickListener(new ClassClickListener());
		RelativeLayout eight = (RelativeLayout) view.findViewById(R.id.rl_class_one_drink);
		eight.setOnClickListener(new ClassClickListener());
		return view;
	}

	class ClassClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			String type = "";
			switch (id){
				case R.id.rl_class_one_fastfood:
					type = "小吃快餐";
					break;
				case R.id.rl_class_one_chinese:
					type = "中式简餐";
					break;
				case R.id.rl_class_one_chicken:
					type = "炸鸡炸串";
					break;
				case R.id.rl_class_one_noodle:
					type = "面条米线";
					break;
				case R.id.rl_class_one_place:
					type = "地方小吃";
					break;
				case R.id.rl_class_one_seafood:
					type = "海鲜烧烤";
					break;
				case R.id.rl_class_one_fruit:
					type = "水果蔬菜";
					break;
				case R.id.rl_class_one_drink:
					type = "甜点饮品";
					break;
			}

			if("".equals(type)) {
				return;
			}

			Intent intent = new Intent(context, ClassShopActivity.class);
			intent.putExtra("type", type);
			startActivity(intent);
		}
	}

}
