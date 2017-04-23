package com.example.chen.myapplication.layout.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chen.myapplication.R;

/**
 * Created by Administrator on 2017/4/23.
 */
public class Fragmentone extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
//		在需要加载Fragment的Activity对应的布局文件中添加fragment的标签
//		加载Fragment的布局文件,接着返回加载的view对象
		View view = inflater.inflate(R.layout.fragment_one, container,false);
		return view;
	}
}