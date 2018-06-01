package com.example.chen.myapplication.app;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.LocationAdapter;
import com.example.chen.myapplication.app.util.BDMapUtil;
import com.example.chen.myapplication.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.MyApplication.mLocationClient;
// 百度地图页面
public class BaiduMapActivity extends BaseActivity {

	MapView mMapView;
	List<PoiInfo> dataList;

	RecyclerView nearRv;
	private BaiduMap mBaiduMap;

	TextView back;

	LocationAdapter locationAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_baidu_map);

		back = (TextView)findViewById(R.id.chat_publish_complete_cancle);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		dataList = new ArrayList();
		nearRv = (RecyclerView) findViewById(R.id.lv_location_nearby);

		nearRv.setLayoutManager(new LinearLayoutManager(this));
		locationAdapter = new LocationAdapter(this, dataList);
		nearRv.setAdapter(locationAdapter);
		mMapView = (MapView) findViewById(R.id.bmapView);

		mBaiduMap = mMapView.getMap();
		mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
			@Override
			public void onReceiveLocation(final BDLocation location) {
				BitmapDescriptor mCurrentMarker = bdMapUtil.initBaiDuMap(mBaiduMap, location);// 调起地图
				bdMapUtil.locationAndSetCursor(mBaiduMap, location, mCurrentMarker);// 将地图设置到当前位置并设置光标
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				bdMapUtil.showAround(latLng, locationAdapter);
			}
		});

		mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {

			}

			@Override
			public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

			}

			@Override
			public void onMapStatusChangeFinish(MapStatus mapStatus) {
				LatLng latLng = mapStatus.target;
				bdMapUtil.showAround(latLng, locationAdapter);

			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {

			}
		});


		mLocationClient.start();
	}

	private BDMapUtil bdMapUtil = BDMapUtil.getInstance();



	@Override
	protected void onDestroy() {
		mBaiduMap.setMyLocationEnabled(false);
		if(mLocationClient.isStarted()) {
			mLocationClient.stop(); //停止定位
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}


}
