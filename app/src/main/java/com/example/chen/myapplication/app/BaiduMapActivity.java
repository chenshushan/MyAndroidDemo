package com.example.chen.myapplication.app;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.*;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.LocationAdapter;
import com.example.chen.myapplication.app.util.BDMapUtil;
import com.example.chen.myapplication.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.MyApplication.mLocationClient;

public class BaiduMapActivity extends AppCompatActivity {

	MapView mMapView;
	List<PoiInfo> dataList;
	Button mCompleteButton;

	RecyclerView nearRv;
	private BaiduMap mBaiduMap;
	LocationAdapter locationAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());

		setContentView(R.layout.activity_baidu_map);

		dataList = new ArrayList();
		mCompleteButton = (Button) findViewById(R.id.chat_publish_complete_publish);
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

	PoiSearch poiSearch;
	private BDMapUtil bdMapUtil = BDMapUtil.getInstance();


	class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {
		@Override
		public void onGetPoiResult(PoiResult result) {
			// 获取POI检索结果
			if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
				ToastUtil.showToast("未找到结果");
				return;
			}

			if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
				if (result != null) {
					List<PoiInfo> poiInfos = result.getAllPoi();
					if (poiInfos != null && poiInfos.size() > 0) {
						for (PoiInfo poiInfo : poiInfos) {
							System.out.println(poiInfo.name);
						}

						dataList.addAll(poiInfos);
						locationAdapter.notifyDataSetChanged();
//							Message msg = new Message();
//							msg.what = 0;
//							handler.sendMessage(msg);
					}
				}
			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
			System.out.println(poiDetailResult.getAddress());
		}

		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

		}
	}

	;


	@Override
	protected void onDestroy() {
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop(); //停止定位
		poiSearch.destroy();
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
