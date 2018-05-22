package com.example.chen.myapplication.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ClassShopActivity;

import java.util.List;

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
		ImageView one = (ImageView) view.findViewById(R.id.iv_class_one_fastfood);
		one.setOnClickListener(new ClassClickListener());
		ImageView two = (ImageView) view.findViewById(R.id.iv_class_one_chicken);
		two.setOnClickListener(new ClassClickListener());
		ImageView three = (ImageView) view.findViewById(R.id.iv_class_one_noodle);
		three.setOnClickListener(new ClassClickListener());
		ImageView four = (ImageView) view.findViewById(R.id.iv_class_one_place);
		four.setOnClickListener(new ClassClickListener());
		ImageView five = (ImageView) view.findViewById(R.id.iv_class_one_seafood);
		five.setOnClickListener(new ClassClickListener());
		ImageView six = (ImageView) view.findViewById(R.id.iv_class_one_fruit);
		six.setOnClickListener(new ClassClickListener());
		ImageView seven = (ImageView) view.findViewById(R.id.iv_class_one_drink);
		seven.setOnClickListener(new ClassClickListener());
		ImageView eight = (ImageView) view.findViewById(R.id.iv_class_one_fastfood);
		eight.setOnClickListener(new ClassClickListener());
		return view;
	}

	class ClassClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			String type = "";
			switch (id){
				case R.id.iv_class_one_chinese:
					type = "2";
					break;
				case R.id.iv_class_one_chicken:
					type = "3";
					break;
				case R.id.iv_class_one_noodle:
					type = "4";
					break;
				case R.id.iv_class_one_place:
					type = "5";
					break;
				case R.id.iv_class_one_seafood:
					type = "6";
					break;
				case R.id.iv_class_one_fruit:
					type = "7";
					break;
				case R.id.iv_class_one_drink:
					type = "8";
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
