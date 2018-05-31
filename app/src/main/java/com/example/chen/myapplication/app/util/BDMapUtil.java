package com.example.chen.myapplication.app.util;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.*;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.LocationAdapter;

import java.util.List;

public class BDMapUtil {

	private static BDMapUtil bdMapUtil = new BDMapUtil();
	private BDMapUtil() {
	}

	public static BDMapUtil getInstance(){
		return bdMapUtil;
	}

	public BitmapDescriptor initBaiDuMap(BaiduMap mBaiduMap, BDLocation location){
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

		BitmapDescriptor  mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_openmap_mark2);
		MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
		mBaiduMap.setMyLocationConfiguration(config);
		return mCurrentMarker;
	}



	public void locationAndSetCursor(final BaiduMap mBaiduMap, BDLocation location, final BitmapDescriptor  mCurrentMarker) {
// 当不需要定位图层时关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		final LatLng point  = new LatLng(latitude, longitude);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory. newLatLng(point));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));

		setCursor(mBaiduMap, point, mCurrentMarker);

		// 按住一会才能拖动
		//调用BaiduMap对象的setOnMarkerDragListener方法设置Marker拖拽的监听
		mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
			public void onMarkerDrag(Marker marker) {
				//拖拽中
			}
			public void onMarkerDragEnd(Marker marker) {
				setCursor(mBaiduMap,marker.getPosition(), mCurrentMarker);
			}
			public void onMarkerDragStart(Marker marker) {
				//开始拖拽
				mBaiduMap.clear();
			}
		});
	}

	public void setCursor(final BaiduMap mBaiduMap, LatLng point, BitmapDescriptor  mCurrentMarker){
//构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().zIndex(12)
				.position(point).draggable(true)  //设置手势拖拽
				.icon(mCurrentMarker);
//在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
	}

	public PoiSearch searchNeayBy(BDLocation location,OnGetPoiSearchResultListener onGetPoiSearchResultListener, String searchWord,int page, int pageNums){
		// POI初始化搜索模块，注册搜索事件监听
		PoiSearch mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		final LatLng point  = new LatLng(latitude, longitude);
		PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
		poiNearbySearchOption.keyword(searchWord).location(point);
		poiNearbySearchOption.radius(2000);  // 检索半径，单位是米
		poiNearbySearchOption.pageNum(page).pageCapacity(pageNums);  // 默认每页10条
		mPoiSearch.searchNearby(poiNearbySearchOption);
		return mPoiSearch;
	}

	public PoiSearch searchNeayBy(BDLocation location,OnGetPoiSearchResultListener onGetPoiSearchResultListener, String searchWord, int page){
		return searchNeayBy(location,onGetPoiSearchResultListener,searchWord,page,10);
	}

	public PoiSearch searchNeayBy(BDLocation location,OnGetPoiSearchResultListener onGetPoiSearchResultListener,int pageNums, String searchWord){
		return searchNeayBy(location,onGetPoiSearchResultListener,searchWord,1,pageNums);
	}

	// 检索周边poi
	public PoiSearch searchNeayBy(BDLocation location,OnGetPoiSearchResultListener onGetPoiSearchResultListener, String searchWord){
		return searchNeayBy(location,onGetPoiSearchResultListener,searchWord,1,10);
	}

	// 检索周边的位置
	public void showAround(LatLng latLng, final LocationAdapter locationAdapter){
		GeoCoder geoCoder = GeoCoder.newInstance();
		//
		OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
			// 反地理编码查询结果回调函数
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					// 没有检测到结果
					ToastUtil.showToast("抱歉，未能找到结果");
					return;
				}
				List<PoiInfo> poiList = result.getPoiList();
				locationAdapter.setPois(poiList);
				locationAdapter.notifyDataSetChanged();
			}

			// 地理编码查询结果回调函数
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					// 没有检测到结果
					ToastUtil.showToast("抱歉，未能找到结果");
				}
			}
		};
		// 设置地理编码检索监听者
		geoCoder.setOnGetGeoCodeResultListener(listener);
		//
		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
	}

}
