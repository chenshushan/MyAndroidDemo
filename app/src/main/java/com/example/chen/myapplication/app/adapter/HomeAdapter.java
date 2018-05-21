package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chen.myapplication.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


	private Context context;



	private LayoutInflater mInflater;

	private List<Fragment> fragments;

	private FragmentManager manager;

	private RecyclerView mRv;


	public HomeAdapter(Context context, FragmentManager manager) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.manager = manager;
	}
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mRv = recyclerView;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case 0 :
				return new ADHolder(mInflater.inflate(R.layout.fg_ad, parent, false));
			case 1 :
				return new ClassHolder(mInflater.inflate(R.layout.fg_class, parent, false));
			case 2 :
				return new ShopHolder(mInflater.inflate(R.layout.fg_shop, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		Log.i("position", "position:" + position);
		int itemViewType = getItemViewType(position);
		switch (itemViewType) {
			case 0 :
				((ADHolder)holder).textView.setText("hello1");
				break;
			case 1 :
				ClassHolder classHolder = (ClassHolder) holder;

				break;
			case 2 :
				((ShopHolder)holder).textView.setText("ShopHolder");
				break;
		}
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return 3;
	}

	class ADHolder extends RecyclerView.ViewHolder{

		TextView textView;
		public ADHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.tx_ad);
		}
	}

	class ClassHolder extends RecyclerView.ViewHolder{

		ViewPager viewPager;
		ImageView ivClassOne;
		ImageView ivClassTwo;

		public ClassHolder(View itemView) {
			super(itemView);
			viewPager = (ViewPager) itemView.findViewById(R.id.vp_class_show);
			ivClassOne = (ImageView) itemView.findViewById(R.id.iv_class_one);
			ivClassTwo = (ImageView) itemView.findViewById(R.id.iv_class_one);
		}
	}
	class ShopHolder extends RecyclerView.ViewHolder{
		TextView textView;
		public ShopHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.tx_shop);

		}
	}


	public void initClassFragment(ClassHolder holder){

	}

}
