package com.example.chen.myapplication.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.service.ShopService;

import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

	RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_tab,container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
		return view;
	}
	HomeAdapter homeAdapterTemp;
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		// 设置adapter
		final HomeAdapter homeAdapter = new HomeAdapter(getActivity(), getActivity().getSupportFragmentManager());
		homeAdapterTemp = homeAdapter;
		homeAdapter.setOnItemClickListener(this);
		recyclerView.setAdapter(homeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// 设置上拉加载更多
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView rv, int newState) {
				super.onScrollStateChanged(rv, newState);
				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
				//不滚动时
				if (newState == RecyclerView.SCROLL_STATE_IDLE){
					//最后一个item
					int lastItem = manager.findLastVisibleItemPosition();
					//所有的item
					int totalItemCount = manager.getItemCount();
					if (lastItem == totalItemCount -1){ // 滚动到最后一项时加载数据
						List<Shop> shops = new ShopService().getShops();
						homeAdapter.setShopData(shops, false);
					}

				}
			}
		});

	}

	@Override
	public void onItemClick(int position) {
		Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
		Shop shop = homeAdapterTemp.getList_shops().get(position);
		intent.putExtra("cartID",shop.getId());
		intent.putExtra("lowPrice",shop.getMinPrice());
		startActivity(intent);
	}
}
