package com.example.chen.myapplication.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chen.myapplication.R;

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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fg_classone, container, false);
	}

	public void onClick(View view){

	}

}
