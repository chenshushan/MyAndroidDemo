package com.example.chen.myapplication.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.MyAddressActivity;

public class MineFragment extends Fragment implements View.OnClickListener {

	RelativeLayout myCenter;
	RelativeLayout myAddress;

	// 绑定UI
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mine_tab, container, false);
		myAddress = (RelativeLayout)view.findViewById(R.id.rl_my_address);
		myCenter = (RelativeLayout)view.findViewById(R.id.rl_my_center);
		myAddress.setOnClickListener(this);
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
				intent = new Intent(getActivity(), MyAddressActivity.class);
				break;
		}
		startActivity(intent);
	}
}
