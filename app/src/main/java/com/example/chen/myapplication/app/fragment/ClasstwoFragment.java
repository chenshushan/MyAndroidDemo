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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ClassShopActivity;

@SuppressLint("ValidFragment")
public class ClasstwoFragment extends Fragment  {


	@BindView(R.id.rl_class_two_liaoli)
	RelativeLayout rlClassTwoLiaoli;
	@BindView(R.id.rl_class_two_luwei)
	RelativeLayout rlClassTwoLuwei;
	@BindView(R.id.rl_class_two_mala)
	RelativeLayout rlClassTwoMala;
	Unbinder unbinder;
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
		unbinder = ButterKnife.bind(this, view);
		return view;
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@OnClick({R.id.rl_class_two_liaoli, R.id.rl_class_two_luwei, R.id.rl_class_two_mala})
	public void onViewClicked(View view) {
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

		if ("".equals(type)) {
			return;
		}

		Intent intent = new Intent(context, ClassShopActivity.class);
		intent.putExtra("type", type);
		startActivity(intent);

	}
}
