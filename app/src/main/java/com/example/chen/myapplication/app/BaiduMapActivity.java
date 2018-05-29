package com.example.chen.myapplication.app;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.MyApplication.mLocationClient;

public class BaiduMapActivity extends AppCompatActivity {

	MapView mMapView;
	List dataList;
	Button mCompleteButton;

//	RecyclerView nearRv;
	private BaiduMap mBaiduMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());

		setContentView(R.layout.activity_baidu_map);

		dataList = new ArrayList();
		mCompleteButton = (Button) findViewById(R.id.chat_publish_complete_publish);
//		nearRv = (RecyclerView) findViewById(R.id.lv_location_nearby);
		mMapView = (MapView) findViewById(R.id.bmapView);

		mBaiduMap = mMapView.getMap();
		mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
			@Override
			public void onReceiveLocation(final BDLocation location) {
				BitmapDescriptor mCurrentMarker = initBaiDuMap(location);// 调起地图
				locationAndSetCursor(location, mCurrentMarker);// 将地图设置到当前位置并设置光标


				//poi 搜索周边
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Looper.prepare();
						searchNeayBy(location);
						Looper.loop();
					}
				}).start();

			}
		});


		mLocationClient.start();
	}

	private void searchNeayBy(BDLocation location){
		// POI初始化搜索模块，注册搜索事件监听
		PoiSearch mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener());
		PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();

		poiNearbySearchOption.keyword("美食");
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		poiNearbySearchOption.location(new LatLng(latitude, longitude));
		poiNearbySearchOption.radius(2000);  // 检索半径，单位是米
		poiNearbySearchOption.pageCapacity(20);  // 默认每页10条
		mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
	}


	class MyOnGetPoiSearchResultListener implements    OnGetPoiSearchResultListener {
		@Override
		public void onGetPoiResult(PoiResult result) {
			// 获取POI检索结果
			if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
				ToastUtil.showToast("未找到结果");
				return;
			}

			if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
//          mBaiduMap.clear();
				if(result != null){
					List<PoiInfo> poiInfos = result.getAllPoi();
					if(poiInfos != null && poiInfos.size()>0){
						for (PoiInfo poiInfo : poiInfos) {
							System.out.println(poiInfo.name);
						}


						dataList.addAll(poiInfos);
//                  adapter.notifyDataSetChanged();
//							Message msg = new Message();
//							msg.what = 0;
//							handler.sendMessage(msg);
					}
				}
			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

		}

		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

		}
	};



	public BitmapDescriptor initBaiDuMap(BDLocation location){
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
// 构造定位数据
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(latitude)
				.longitude(longitude).build();
// 设置定位数据
		mBaiduMap.setMyLocationData(locData);

// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）

		BitmapDescriptor  mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.mipmap.icon_openmap_mark2);
		MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
		mBaiduMap.setMyLocationConfiguration(config);
		return mCurrentMarker;
	}

	public void locationAndSetCursor(BDLocation location, BitmapDescriptor  mCurrentMarker) {
// 当不需要定位图层时关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng point  = new LatLng(latitude, longitude);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory. newLatLng(point));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));


//构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions()
				.position(point)
				.icon(mCurrentMarker);
//在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
	}

	@Override
	protected void onDestroy() {
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop(); //停止定位

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
