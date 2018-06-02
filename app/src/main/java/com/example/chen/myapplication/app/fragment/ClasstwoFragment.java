package com.example.chen.myapplication.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ClassShopActivity;

@SuppressLint("ValidFragment")
public class ClasstwoFragment extends Fragment implements View.OnClickListener {



	private Context context;
	private LayoutInflater inflater;

	public ClasstwoFragment(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fg_classtwo, container, false);
		RelativeLayout one = (RelativeLayout) view.findViewById(R.id.rl_class_two_liaoli);
		one.setOnClickListener(this);
		RelativeLayout two = (RelativeLayout) view.findViewById(R.id.rl_class_two_luwei);
		two.setOnClickListener(this);
		RelativeLayout three = (RelativeLayout) view.findViewById(R.id.rl_class_two_mala);
		three.setOnClickListener(this);


		return view;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		String type = "";
		switch (id) {
			case R.id.rl_class_two_liaoli:
				type = "日韩料理";
				break;
			case R.id.rl_class_two_luwei:
				type = "卤味鸭脖";
				break;
			case R.id.rl_class_two_mala:
				type = "麻辣烫";
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
