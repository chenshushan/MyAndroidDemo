package com.example.chen.myapplication.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.BaiduMapActivity;
import com.example.chen.myapplication.app.LoginActivity;
import com.example.chen.myapplication.app.MyAddressActivity;
import com.example.chen.myapplication.app.UserCenterActivity;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.view.TitleView;

import static com.example.chen.myapplication.app.bean.User.USER_INFO;

public class MineFragment extends Fragment implements View.OnClickListener {

	RelativeLayout myCenter;
	RelativeLayout myAddress;
	ImageView toLogin;

	TextView loginOrUserName;
	TextView priorityOrPhone;
	RelativeLayout location;

	TitleView titleView;
	// 绑定UI
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mine_tab, container, false);

		titleView = (TitleView) view.findViewById(R.id.mine_title);
		titleView.setTitleText("个人中心");

		myAddress = (RelativeLayout)view.findViewById(R.id.rl_my_address);
		myCenter = (RelativeLayout)view.findViewById(R.id.rl_my_center);
		location = (RelativeLayout)view.findViewById(R.id.rl_my_location);
		location.setOnClickListener(this);
		myAddress.setOnClickListener(this);
		myCenter.setOnClickListener(this);
		loginOrUserName = (TextView)view.findViewById(R.id.tv_minefragment_register_or_login);
		priorityOrPhone = (TextView)view.findViewById(R.id.tv_minefragment_enjoy_priority);

		toLogin = (ImageView)view.findViewById(R.id.iv_minefragment_back);
		toLogin.setOnClickListener(this);

//		判断是否登录
		User user = PreferenceUtil.getObject(USER_INFO, User.class);
		if(user != null) { // 已经登录 设置用户个人信息
			loginOrUserName.setText(user.getUsername() + user.getSex());
			priorityOrPhone.setText(user.getPhone());
			toLogin.setVisibility(View.GONE);
		}

		return view;
	}

	// 绑定UI数据
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		Intent intent = null;
		switch (id){
			case R.id.rl_my_address :
				FragmentActivity activity = getActivity();
				intent = new Intent(activity, MyAddressActivity.class);
				break;
			case R.id.rl_my_center :
				intent = new Intent(getActivity(), UserCenterActivity.class);
				break;
			case R.id.iv_minefragment_back :
				intent =new Intent(getActivity(), LoginActivity.class);
			case R.id.rl_my_location :
				intent =new Intent(getActivity(), BaiduMapActivity.class);
		}
		startActivity(intent);
	}
}
