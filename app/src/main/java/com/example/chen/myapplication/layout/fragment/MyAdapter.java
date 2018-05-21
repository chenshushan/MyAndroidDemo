package com.example.chen.myapplication.layout.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

	private Context context;


	private final int TYPE_AD = 0;
	private final int TYPE_CLASS = 1;
	private final int TYPE_SHOP = 2;

	private LayoutInflater mInflater;

	private List<Fragment> fragments;

	private FragmentManager manager;

//	private OnItemClickListener mOnItemClickListener;
	private RecyclerView mRv;

	public MyAdapter(Context context, FragmentManager manager) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.manager = manager;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 3;
	}

	@Override
	public void onClick(View view) {

	}
}
