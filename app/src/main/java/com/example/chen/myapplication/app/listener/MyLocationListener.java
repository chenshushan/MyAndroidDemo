package com.example.chen.myapplication.app.listener;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.*;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.fragment.HomeFragment;
import com.example.chen.myapplication.app.service.ShopService;
import com.example.chen.myapplication.app.util.BDMapUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.MyApplication.mLocationClient;
import static com.example.chen.myapplication.app.service.ShopService.initShop;

public class MyLocationListener extends BDAbstractLocationListener {

	HomeFragment homeFragment;

	public MyLocationListener(HomeFragment homeFragment) {
		this.homeFragment = homeFragment;
	}

	BDLocation bdLocation;

	public BDLocation getBdLocation() {
		return bdLocation;
	}

	@Override
	public void onReceiveLocation(BDLocation location){
		//此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
		//以下只列举部分获取经纬度相关（常用）的结果信息
		//更多结果信息获取说明，请参照类参考中BDLocation类中的说明
		bdLocation = location;
		HomeFragment.setBdLocation(location);
		double latitude = location.getLatitude();    //获取纬度信息
		double longitude = location.getLongitude();    //获取经度信息
		float radius = location.getRadius();    //获取定位精度，默认值为0.0f

		String coorType = location.getCoorType();
		//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

		int errorCode = location.getLocType();
		//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
		System.out.println("radius:"+radius+",coorType:"+coorType+",errorCode:"+errorCode);
		System.out.println("latitude:" + latitude + ",longitude:" + longitude);
		mLocationClient.stop();


		String addr = location.getAddrStr();    //获取详细地址信息
		String country = location.getCountry();    //获取国家
		String province = location.getProvince();    //获取省份
		String city = location.getCity();    //获取城市
		String district = location.getDistrict();    //获取区县
		String street = location.getStreet();    //获取街道信息

		homeFragment.getMyLocation().setText(street);

		String format = String.format("%s %s %s %s %s %s", country, province, city, district, street, addr);
		System.out.println(format);

		System.out.println("desc:" + location.getLocationDescribe());
//		List<Poi> poiList = location.getPoiList();

		poiSearch = BDMapUtil.getInstance().searchNeayBy(location, new OnGetPoiSearchResultListener() {
			@Override
			public void onGetPoiResult(PoiResult poiResult) {
				List<PoiInfo> poiInfos = poiResult.getAllPoi();
				HomeAdapter homeAdapterTemp = homeFragment.getHomeAdapterTemp();
				List<Shop> list = new ArrayList();
				if(poiInfos == null){
					return;
				}
				for (PoiInfo poiInfo : poiInfos) {
					Shop shop = initShop(poiInfo.name);
					list.add(shop);
				}
				homeAdapterTemp.setShopData(list, true);
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
			}

			@Override
			public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
			}
		}, "美食");


	}
	PoiSearch poiSearch;

	public PoiSearch getPoiSearch() {
		return poiSearch;
	}
}