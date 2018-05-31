package com.example.chen.myapplication.app.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.ShopDetailActivity;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.listener.MyLocationListener;
import com.example.chen.myapplication.app.listener.MyOnGetPoiSearchResultListener;
import com.example.chen.myapplication.app.service.ShopService;
import com.example.chen.myapplication.app.util.BDMapUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.MyApplication.mLocationClient;
import static com.example.chen.myapplication.app.service.ShopService.initShop;

// 首页
public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

	RecyclerView recyclerView;

	TextView myLocation;

	public TextView getMyLocation() {
		return myLocation;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_tab, container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);

		myLocation = (TextView)view.findViewById(R.id.home_location);
		getPersimmions();// 请求定位权限
		return view;
	}

	HomeAdapter homeAdapterTemp;

	public HomeAdapter getHomeAdapterTemp() {
		return homeAdapterTemp;
	}
	private static BDLocation bdLocation;

	public static void setBdLocation(BDLocation bdLocation) {
		HomeFragment.bdLocation = bdLocation;
	}

	public static BDLocation getBdLocation() {
		return bdLocation;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// 设置adapter
		final HomeAdapter homeAdapter = new HomeAdapter(getActivity(), getActivity().getSupportFragmentManager());
		homeAdapterTemp = homeAdapter;
		homeAdapter.setOnItemClickListener(this);
		recyclerView.setAdapter(homeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// 注册监听函数
		myLocationListener = new MyLocationListener(this);
		mLocationClient.registerLocationListener(myLocationListener);
		mLocationClient.start();


		// 设置上拉加载更多
		RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
			int page = 2;
			@Override
			public void onScrollStateChanged(RecyclerView rv, int newState) {
				super.onScrollStateChanged(rv, newState);
				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
				//不滚动时
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					//最后一个item
					int lastItem = manager.findLastVisibleItemPosition();
					//所有的item
					int totalItemCount = manager.getItemCount();
					if (lastItem == totalItemCount - 1) { // 滚动到最后一项时加载数据
//						bdLocation = myLocationListener.getBdLocation();
						BDMapUtil.getInstance().searchNeayBy(bdLocation, new MyOnGetPoiSearchResultListener(homeAdapter),"美食", page++);
					}

				}
			}
		};
		recyclerView.addOnScrollListener(listener);

	}
	MyLocationListener myLocationListener;
	@Override
	public void onItemClick(int position) {
		Intent intent = new Intent(getActivity(), ShopDetailActivity.class);

		Shop shop = homeAdapterTemp.getList_shops().get(position);
		intent.putExtra("shop",shop);
		startActivity(intent);
	}
	private String permissionInfo;

	@TargetApi(23)
	private void getPersimmions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			ArrayList<String> permissions = new ArrayList<String>();
			/***
			 * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
			 */
			// 定位精确位置
			if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
			// 读写权限
			if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
			}
			// 读取电话状态权限
			if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
				permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
			}

			if (permissions.size() > 0) {
				requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
			}
		}
	}
	private final int SDK_PERMISSION_REQUEST = 127;

	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
		if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)){
				return true;
			}else{
				permissionsList.add(permission);
				return false;
			}

		}else{
			return true;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		PoiSearch poiSearch = myLocationListener.getPoiSearch();
		if(poiSearch != null) {
			poiSearch.destroy();
		}
	}
}
