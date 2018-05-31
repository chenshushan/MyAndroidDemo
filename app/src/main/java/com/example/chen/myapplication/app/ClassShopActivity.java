package com.example.chen.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.fragment.HomeFragment;
import com.example.chen.myapplication.app.listener.MyOnGetPoiSearchResultListener;
import com.example.chen.myapplication.app.service.ShopService;
import com.example.chen.myapplication.app.util.BDMapUtil;
import com.example.chen.myapplication.app.view.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.chen.myapplication.app.adapter.HomeAdapter.CLASS_TYPE;
import static com.example.chen.myapplication.app.service.ShopService.initShop;

// 店铺分类的列表页
public class ClassShopActivity extends BaseActivity implements HomeAdapter.OnItemClickListener {

	SearchView searchView;

	RecyclerView recyclerView;

	TitleView titleView;

	HomeAdapter classTypeAdatperTemp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_shops);

		searchView = (SearchView) findViewById(R.id.sv_class_type);
		recyclerView = (RecyclerView) findViewById(R.id.rv_class_type_list);
		titleView = (TitleView) findViewById(R.id.tv_type_title);

		// 得到类型 设置title
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		titleView.setTitleText(type);
		// 初始化recyclerView
		final HomeAdapter classTypeAdatper = new HomeAdapter(this, getSupportFragmentManager(), CLASS_TYPE);
		classTypeAdatperTemp = classTypeAdatper;
		classTypeAdatper.setOnItemClickListener(this);
		final ShopService shopService = new ShopService();
		classTypeAdatper.setShopData(shopService.getShops(),false);
		recyclerView.setAdapter(classTypeAdatper);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		BDMapUtil.getInstance().searchNeayBy(HomeFragment.getBdLocation(), new MyOnGetPoiSearchResultListener(classTypeAdatper, true),"美食", 1);

		// 进入时隐藏键盘
		searchView.setIconifiedByDefault(false);
		searchView.setFocusable(false);
		searchView.setIconified(true);


		// 设置搜索框文本监听
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				// 得到输入管理对象
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					// 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
					imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
				}
				int pageNums = new Random().nextInt(9) + 1;
				BDMapUtil.getInstance().searchNeayBy(HomeFragment.getBdLocation(), new MyOnGetPoiSearchResultListener(classTypeAdatper, true),pageNums,"美食");

//				List<Shop> shops = shopService.getShops();
//				List<Shop> shopList = shops.subList(0, pageNums);
//				classTypeAdatper.setShopData(shopList, true);
				searchView.clearFocus(); // 不获取焦点
				return true;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				if(!"".equals(s)) {
					return false;
				}
				int pageNums = new Random().nextInt(9) + 1;
				BDMapUtil.getInstance().searchNeayBy(HomeFragment.getBdLocation(), new MyOnGetPoiSearchResultListener(classTypeAdatper, true),pageNums,"美食");
				searchView.clearFocus(); // 不获取焦点
				return true;

			}
		});
		// 下拉加载更多
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			int page = 2;
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
					String s = searchView.getQuery().toString();
					if (lastItem == totalItemCount -1 && s.equals("")){ // 滚动到最后一项时加载数据 并且搜索框没有数据
//						List<Shop> shops = new ShopService().getShops();
//						classTypeAdatper.setShopData(shops, false);
						BDMapUtil.getInstance().searchNeayBy(HomeFragment.getBdLocation(), new MyOnGetPoiSearchResultListener(classTypeAdatper, false),"美食", page++);

					}

				}
			}
		});


	}

	@Override
	public void onItemClick(int position) {
		Intent intent = new Intent(this, ShopDetailActivity.class);

		Shop shop = classTypeAdatperTemp.getList_shops().get(position);
		intent.putExtra("shop",shop);
		startActivity(intent);
	}

}
