package com.example.chen.myapplication.app.listener;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.myapplication.app.service.ShopService.initShop;

public class MyOnGetPoiSearchResultListener  implements OnGetPoiSearchResultListener {

	HomeAdapter homeAdapter;

	boolean isRefresh = false;

	public MyOnGetPoiSearchResultListener(HomeAdapter homeAdapter) {
		this.homeAdapter = homeAdapter;
	}

	public MyOnGetPoiSearchResultListener(HomeAdapter homeAdapter, boolean isRefresh) {
		this.homeAdapter = homeAdapter;
		this.isRefresh = isRefresh;
	}

	@Override
	public void onGetPoiResult(PoiResult poiResult) {
		List<PoiInfo> poiInfos = poiResult.getAllPoi();
		List<Shop> list = new ArrayList();
		for (PoiInfo poiInfo : poiInfos) {
			Shop shop = initShop(poiInfo.name);
			list.add(shop);
		}
		homeAdapter.setShopData(list, isRefresh);
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
	}

	@Override
	public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
	}

}
